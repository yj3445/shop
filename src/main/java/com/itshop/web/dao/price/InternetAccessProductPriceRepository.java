package com.itshop.web.dao.price;

import com.alibaba.fastjson.JSONArray;
import com.itshop.web.bo.UserInfoVO;
import com.itshop.web.constants.CacheKeyConstants;
import com.itshop.web.dao.mysql.*;
import com.itshop.web.dao.po.*;
import com.itshop.web.dto.price.InternetAccessProductPriceConfig;
import com.itshop.web.enums.OrgTypeEnum;
import com.itshop.web.enums.ProductItemType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Slf4j
@Repository(value = "internetAccessProductPriceRepository")
public class InternetAccessProductPriceRepository {

    @Autowired
    TProductItemDAO tProductItemDAO;

    @Autowired
    TProductOemDAO productOemDAO;

    @Autowired
    TProductOemDiscountRulesDAO tProductOemDiscountRulesDAO;

    @Autowired
    TProductOemItemPriceDAO tProductOemItemPriceDAO;

    @Autowired
    TProductAgentRelationDAO tProductAgentRelationDAO;


    /**
     * 得到产品价格配置信息
     * @param productId 产品id
     * @param userInfoVO 当前用户
     * @param agentUseEndUserPrice 代理公司使用终端用户价格
     * @return
     */
    public InternetAccessProductPriceConfig getInternetAccessProductPrice(Integer productId, UserInfoVO userInfoVO,boolean agentUseEndUserPrice) {
        InternetAccessProductPriceConfig result = null;
        if (agentUseEndUserPrice && Objects.equals(OrgTypeEnum.AGENT_COMPANY.getCode(), userInfoVO.getOrgType())) {
            result = getEndPrice(productId, userInfoVO);
        } else {
            if (Objects.equals(OrgTypeEnum.SERVICE_PROVIDER.getCode(), userInfoVO.getOrgType())) {
                result = getServiceProviderProductPrice(productId);
            } else if (Objects.equals(OrgTypeEnum.CUSTOMER_COMPANY.getCode(), userInfoVO.getOrgType())) {
                result = getEndPrice(productId, userInfoVO);
            } else {
                result = getParentOrgSettingPrice(productId, userInfoVO);
            }
        }
        // log.info("[getInternetAccessProductPrice]" +
        //                 " productId:[{}],orgType:[{}],orgId:[{}],parentOrgId:[{}],agentUseEndUserPrice:[{}]" +
        //                 " InternetAccessProductPriceConfig:[{}]",
        //         productId,
        //         OrgTypeEnum.getName(userInfoVO.getOrgType()),
        //         userInfoVO.getOrgId(),
        //         userInfoVO.getParentOrgId(),
        //         agentUseEndUserPrice,
        //         result);
        return result;
    }

    /**
     * 得到终端价格
     *
     * @param productId 产品ID
     * @param userInfoVO 当前登录用户信息
     * @return
     */
    private InternetAccessProductPriceConfig getEndPrice(Integer productId, UserInfoVO userInfoVO) {
        InternetAccessProductPriceConfig price = new InternetAccessProductPriceConfig();
        price.setProductId(productId);
        Integer agentOrgId = OrgTypeEnum.CUSTOMER_COMPANY.getCode().equals(userInfoVO.getOrgType()) ? userInfoVO.getParentOrgId() : userInfoVO.getOrgId();
        TProductAgentRelation persistence = tProductAgentRelationDAO.selectByOrgIdAndProductId(agentOrgId, productId);
        if (persistence != null && BooleanUtils.isTrue(persistence.getIsAgent())) {
            TProductOem productOem = productOemDAO.selectByProductAndOrgId(productId, agentOrgId);
            List<TProductOemItemPrice> oemItemPrices = tProductOemItemPriceDAO.selectEndPrice(agentOrgId, productId);
            List<TProductOemDiscountRules> oemDiscountRules = null;
            if (productOem != null) {
                price.setProductOemId(productOem.getProductOemId());
                oemDiscountRules = tProductOemDiscountRulesDAO.selectByProductOemId(productOem.getProductOemId());
            }
            List<TProductItem> productItemList = tProductItemDAO.selectByProductId(productId);
            if (CollectionUtils.isNotEmpty(productItemList)) {
                Map<String, List<TProductItem>> stringListMap = productItemList.stream().collect(Collectors.groupingBy(TProductItem::getItemType));
                List<TProductOemItemPrice> finalOemItemPrices = oemItemPrices;
                List<TProductOemDiscountRules> finalOemDiscountRules = oemDiscountRules;
                stringListMap.forEach((key, value) -> {
                    value.stream().sorted(Comparator.comparing(TProductItem::getOrderNum)).forEach(p -> {
                        AtomicReference<InternetAccessProductPriceConfig.BroadBandPriceConfig> broadBandPriceConfig = new AtomicReference<>();
                        broadBandPriceConfig.set(new InternetAccessProductPriceConfig.BroadBandPriceConfig());
                        broadBandPriceConfig.get().setMbps(p.getItemValue());
                        broadBandPriceConfig.get().setPriceUnit(p.getDefaultPriceUnit());
                        //默认价格: 0
                        broadBandPriceConfig.get().setPrice(0d);
                        if (CollectionUtils.isNotEmpty(finalOemItemPrices)) {
                            finalOemItemPrices.stream().filter(t -> t.getProductItemId().equals(p.getProductItemId()))
                                    .findFirst()
                                    .ifPresent(t -> {
                                        broadBandPriceConfig.get().setPrice(t.getPrice().doubleValue());
                                        if (StringUtils.isNotEmpty(t.getPriceUnit())) {
                                            broadBandPriceConfig.get().setProductOemItemPriceId(t.getProductOemItemPriceId());
                                            broadBandPriceConfig.get().setPriceUnit(t.getPriceUnit());
                                        }
                                    });
                        }
                        AtomicReference<InternetAccessProductPriceConfig.DiscountRateConfig> discountRateConfig = new AtomicReference<>();
                        discountRateConfig.set(new InternetAccessProductPriceConfig.DiscountRateConfig());
                        discountRateConfig.get().setValue(p.getItemValue());
                        //默认折扣:100
                        discountRateConfig.get().setDiscountRate(100);
                        if (CollectionUtils.isNotEmpty(finalOemDiscountRules)) {
                            finalOemDiscountRules.stream().filter(t -> t.getProductItemId().equals(p.getProductItemId()))
                                    .findFirst()
                                    .ifPresent(t -> {
                                        discountRateConfig.get().setProductOemDiscountRulesId(t.getProductOemDiscountRulesId());
                                        discountRateConfig.get().setDiscountRate(t.getDiscountRate());
                                    });
                        }
                        if (key.equalsIgnoreCase(ProductItemType.BROAD_BAND.getCode()) && broadBandPriceConfig.get() != null) {
                            price.getBroadBand().add(broadBandPriceConfig.get());
                        }
                        if (key.equalsIgnoreCase(ProductItemType.EXPORT.getCode()) && discountRateConfig.get() != null) {
                            price.getExport().add(discountRateConfig.get());
                        }
                        if (key.equalsIgnoreCase(ProductItemType.CONTRACT_PERIOD.getCode()) && discountRateConfig.get() != null) {
                            price.getContractPeriod().add(discountRateConfig.get());
                        }
                        if (key.equalsIgnoreCase(ProductItemType.PAYMENT_CYCLE.getCode()) && discountRateConfig.get() != null) {
                            price.getPaymentCycle().add(discountRateConfig.get());
                        }
                        if (key.equalsIgnoreCase(ProductItemType.PAYMENT_METHOD.getCode()) && discountRateConfig.get() != null) {
                            price.getPaymentMethod().add(discountRateConfig.get());
                        }
                    });
                });
            }
        }
        return price;
    }

    /**
     * 根据产品id得到服务提供商的价格
     *
     * @param productId
     * @return
     */
    private InternetAccessProductPriceConfig getServiceProviderProductPrice(Integer productId){
        InternetAccessProductPriceConfig price = new InternetAccessProductPriceConfig();
        price.setProductId(productId);
        List<TProductItem> productItemList = tProductItemDAO.selectByProductId(productId);
        if (CollectionUtils.isNotEmpty(productItemList)) {
            Map<String, List<TProductItem>> stringListMap = productItemList.stream().collect(Collectors.groupingBy(TProductItem::getItemType));
            stringListMap.forEach((key, value) -> {
                value.stream().sorted(Comparator.comparing(TProductItem::getOrderNum)).forEach(p -> {
                    InternetAccessProductPriceConfig.BroadBandPriceConfig broadBandPriceConfig = new InternetAccessProductPriceConfig.BroadBandPriceConfig();
                    broadBandPriceConfig.setPrice(p.getDefaultPrice().doubleValue());
                    broadBandPriceConfig.setPriceUnit(p.getDefaultPriceUnit());
                    broadBandPriceConfig.setMbps(p.getItemValue());
                    InternetAccessProductPriceConfig.DiscountRateConfig discountRateConfig = new InternetAccessProductPriceConfig.DiscountRateConfig();
                    discountRateConfig.setDiscountRate(p.getDefaultDiscount().intValue());
                    discountRateConfig.setValue(p.getItemValue());
                    if (key.equalsIgnoreCase(ProductItemType.BROAD_BAND.getCode())) {
                        price.getBroadBand().add(broadBandPriceConfig);
                    }
                    if (key.equalsIgnoreCase(ProductItemType.EXPORT.getCode())) {
                        price.getExport().add(discountRateConfig);
                    }
                    if (key.equalsIgnoreCase(ProductItemType.CONTRACT_PERIOD.getCode())) {
                        price.getContractPeriod().add(discountRateConfig);
                    }
                    if (key.equalsIgnoreCase(ProductItemType.PAYMENT_CYCLE.getCode())) {
                        price.getPaymentCycle().add(discountRateConfig);
                    }
                    if (key.equalsIgnoreCase(ProductItemType.PAYMENT_METHOD.getCode())) {
                        price.getPaymentCycle().add(discountRateConfig);
                    }
                });
            });
        }
        return price;
    }

    /**
     * 根据产品id及当期登录的用户得到对应的价格
     * @param productId
     * @param userInfoVO
     * @return
     */
    private InternetAccessProductPriceConfig getParentOrgSettingPrice(Integer productId, UserInfoVO userInfoVO) {
        InternetAccessProductPriceConfig price = new InternetAccessProductPriceConfig();
        price.setProductId(productId);
        Integer agentOrgId = OrgTypeEnum.CUSTOMER_COMPANY.getCode().equals(userInfoVO.getOrgType()) ? userInfoVO.getParentOrgId() : userInfoVO.getOrgId();
        TProductAgentRelation persistence = tProductAgentRelationDAO.selectByOrgIdAndProductId(agentOrgId, productId);
        if (persistence != null && BooleanUtils.isTrue(persistence.getIsAgent())) {
            TProductOem productOem = productOemDAO.selectByProductAndOrgId(productId, agentOrgId);
            List<TProductOemItemPrice> oemItemPrices = tProductOemItemPriceDAO.selectByTargetOrgIdAndProductId(userInfoVO.getOrgId(), userInfoVO.getParentOrgId(), productId);
            List<TProductOemDiscountRules> oemDiscountRules = null;
            if (productOem != null) {
                price.setProductOemId(productOem.getProductOemId());
                oemDiscountRules = tProductOemDiscountRulesDAO.selectByProductOemId(productOem.getProductOemId());
            }
            List<TProductItem> productItemList = tProductItemDAO.selectByProductId(productId);
            if (CollectionUtils.isNotEmpty(productItemList)) {
                Map<String, List<TProductItem>> stringListMap = productItemList.stream().collect(Collectors.groupingBy(TProductItem::getItemType));
                List<TProductOemItemPrice> finalOemItemPrices = oemItemPrices;
                List<TProductOemDiscountRules> finalOemDiscountRules = oemDiscountRules;
                stringListMap.forEach((key, value) -> {
                    value.stream().sorted(Comparator.comparing(TProductItem::getOrderNum)).forEach(p -> {
                        AtomicReference<InternetAccessProductPriceConfig.BroadBandPriceConfig> broadBandPriceConfig = new AtomicReference<>();
                        broadBandPriceConfig.set(new InternetAccessProductPriceConfig.BroadBandPriceConfig());
                        broadBandPriceConfig.get().setMbps(p.getItemValue());
                        broadBandPriceConfig.get().setPriceUnit(p.getDefaultPriceUnit());
                        //默认价格: 0
                        broadBandPriceConfig.get().setPrice(0d);
                        if (CollectionUtils.isNotEmpty(finalOemItemPrices)) {
                            finalOemItemPrices.stream().filter(t -> t.getProductItemId().equals(p.getProductItemId()))
                                    .findFirst()
                                    .ifPresent(t -> {
                                        broadBandPriceConfig.get().setPrice(t.getPrice().doubleValue());
                                        if (StringUtils.isNotEmpty(t.getPriceUnit())) {
                                            broadBandPriceConfig.get().setProductOemItemPriceId(t.getProductOemItemPriceId());
                                            broadBandPriceConfig.get().setPriceUnit(t.getPriceUnit());
                                        }
                                    });
                        }
                        AtomicReference<InternetAccessProductPriceConfig.DiscountRateConfig> discountRateConfig = new AtomicReference<>();
                        discountRateConfig.set(new InternetAccessProductPriceConfig.DiscountRateConfig());
                        discountRateConfig.get().setValue(p.getItemValue());
                        //默认折扣:100
                        discountRateConfig.get().setDiscountRate(100);
                        if (CollectionUtils.isNotEmpty(finalOemDiscountRules)) {
                            finalOemDiscountRules.stream().filter(t -> t.getProductItemId().equals(p.getProductItemId()))
                                    .findFirst()
                                    .ifPresent(t -> {
                                        discountRateConfig.get().setProductOemDiscountRulesId(t.getProductOemDiscountRulesId());
                                        discountRateConfig.get().setDiscountRate(t.getDiscountRate());
                                    });
                        }
                        if (key.equalsIgnoreCase(ProductItemType.BROAD_BAND.getCode()) && broadBandPriceConfig.get() != null) {
                            price.getBroadBand().add(broadBandPriceConfig.get());
                        }
                        if (key.equalsIgnoreCase(ProductItemType.EXPORT.getCode()) && discountRateConfig.get() != null) {
                            price.getExport().add(discountRateConfig.get());
                        }
                        if (key.equalsIgnoreCase(ProductItemType.CONTRACT_PERIOD.getCode()) && discountRateConfig.get() != null) {
                            price.getContractPeriod().add(discountRateConfig.get());
                        }
                        if (key.equalsIgnoreCase(ProductItemType.PAYMENT_CYCLE.getCode()) && discountRateConfig.get() != null) {
                            price.getPaymentCycle().add(discountRateConfig.get());
                        }
                        if (key.equalsIgnoreCase(ProductItemType.PAYMENT_METHOD.getCode()) && discountRateConfig.get() != null) {
                            price.getPaymentMethod().add(discountRateConfig.get());
                        }
                    });
                });
            }
        }
        return price;
    }

    /**
     * 得到互联网接入产品价格配置信息
     *
     * @return List<InternetAccessProductPrice>
     */
    @Cacheable(value = CacheKeyConstants.INTERNET_ACCESS_PRODUCT_PRICE_CONFIG, cacheManager = "caffeineCacheManager")
    public List<InternetAccessProductPriceConfig> getInternetAccessProductPrices() {
        ClassPathResource resource = new ClassPathResource("price-config/internet-access-product-price-config.json");
        StringBuilder sb = new StringBuilder();
        try {
            InputStream inputStream = resource.getInputStream();
            for (String str : IOUtils.readLines(inputStream)) {
                sb.append(str);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return JSONArray.parseArray(sb.toString(), InternetAccessProductPriceConfig.class);
    }
}
