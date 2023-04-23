package com.itshop.web.service;

import com.itshop.web.bo.InternetAccessOrderAuditResult;
import com.itshop.web.bo.InternetAccessOrderBeforeChangeAndDetail;
import com.itshop.web.bo.UserInfoVO;
import com.itshop.web.dao.price.InternetAccessProductPriceRepository;
import com.itshop.web.dto.price.InternetAccessProductPriceConfig;
import com.itshop.web.dto.price.InternetAccessProductPriceItem;
import com.itshop.web.dto.request.InternetAccessOrderSaveParam;
import com.itshop.web.dto.request.OrderAuditParam;
import com.itshop.web.dto.response.InternetAccessOrderAmountResp;
import com.itshop.web.dto.response.InternetAccessOrderPriceResp;
import com.itshop.web.enums.OrgTypeEnum;
import com.itshop.web.manager.InternetAccessOrderManager;
import com.itshop.web.service.devicecontrol.InternetAccessDeviceControlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * 互联网接入-接入-自定义-订单服务
 *
 * @author liufenglong
 * @date 2022/5/7
 */
@Service
public class InternetAccessOrderService {
    @Autowired
    InternetAccessOrderManager internetAccessOrderManager;

    @Autowired
    InternetAccessProductPriceRepository internetAccessProductPriceRepository;

    @Autowired
    InternetAccessDeviceControlService internetAccessDeviceControlService;

    /**
     * 计算总价和折扣金额
     *
     * @param productDOrderParam
     * @return
     */
    public InternetAccessOrderPriceResp calcTotalPriceAndDiscount(InternetAccessOrderSaveParam productDOrderParam,
                                                                  UserInfoVO userInfoVO,
                                                                  boolean agentUseEndUserPrice) {
        InternetAccessOrderPriceResp resp = new InternetAccessOrderPriceResp();
        resp.setInternetAccessProductPriceItem(new InternetAccessProductPriceItem());
        resp.setProductId(productDOrderParam.getProductId());
        InternetAccessProductPriceConfig productPrice = internetAccessProductPriceRepository.getInternetAccessProductPrice(productDOrderParam.getProductId(), userInfoVO, agentUseEndUserPrice);
        resp.setProductOemId(productPrice.getProductOemId());
        InternetAccessProductPriceConfig.BroadBandPriceConfig zeroBandConfig = new InternetAccessProductPriceConfig.BroadBandPriceConfig();
        zeroBandConfig.setPrice(0d);
        InternetAccessProductPriceConfig.DiscountRateConfig noDiscount = new InternetAccessProductPriceConfig.DiscountRateConfig();
        noDiscount.setDiscountRate(100);
        //速率-基础价格
        InternetAccessProductPriceConfig.BroadBandPriceConfig bandPriceConfig = productPrice.getBroadBand().stream()
                .filter(p -> p.getMbps().equals(productDOrderParam.getBroadBand()))
                .findFirst()
                .orElseGet(() -> zeroBandConfig);
        resp.getInternetAccessProductPriceItem().setBroadBandPriceConfig(bandPriceConfig);
        if ("YEAR".equalsIgnoreCase(bandPriceConfig.getPriceUnit())) {
            resp.setBasicPrice(BigDecimal.valueOf(bandPriceConfig.getPrice())
                    .multiply(BigDecimal.valueOf(productDOrderParam.getContractPeriod()))
                    .divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP));
        } else {
            resp.setBasicPrice(BigDecimal.valueOf(bandPriceConfig.getPrice())
                    .multiply(BigDecimal.valueOf(productDOrderParam.getContractPeriod())));
        }
        // 计算[服务提供商/一级代理商/二级代理商/三级代理商]订单金额时,不参与折扣计算
        if (agentUseEndUserPrice) {
            //出口-折扣
            InternetAccessProductPriceConfig.DiscountRateConfig discountConfig = productPrice.getExport().stream()
                    .filter(p -> p.getValue().equals(productDOrderParam.getExport()))
                    .findFirst()
                    .orElseGet(() -> noDiscount);
            resp.getInternetAccessProductPriceItem().setExportDiscountRateConfig(discountConfig);
            resp.setExportDiscountPrice(resp.getBasicPrice()
                    .multiply(BigDecimal.valueOf(-1))
                    .multiply(BigDecimal.valueOf(100 - discountConfig.getDiscountRate()))
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
            //合同期限-折扣
            discountConfig = productPrice.getContractPeriod().stream()
                    .filter(p -> p.getValue().equals(productDOrderParam.getContractPeriod()))
                    .findFirst()
                    .orElseGet(() -> noDiscount);
            resp.getInternetAccessProductPriceItem().setContractPeriodDiscountRateConfig(discountConfig);
            resp.setContractPeriodDiscountPrice(resp.getBasicPrice()
                    .multiply(BigDecimal.valueOf(-1))
                    .multiply(BigDecimal.valueOf(100 - discountConfig.getDiscountRate()))
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
            //缴费周期-折扣
            discountConfig = productPrice.getPaymentCycle().stream()
                    .filter(p -> p.getValue().equals(productDOrderParam.getPaymentCycle()))
                    .findFirst()
                    .orElseGet(() -> noDiscount);
            resp.getInternetAccessProductPriceItem().setPaymentCycleDiscountRateConfig(discountConfig);
            resp.setPaymentCycleDiscountPrice(resp.getBasicPrice()
                    .multiply(BigDecimal.valueOf(-1))
                    .multiply(BigDecimal.valueOf(100 - discountConfig.getDiscountRate()))
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
            //付费方式-折扣
            discountConfig = productPrice.getPaymentMethod().stream()
                    .filter(p -> p.getValue().equals(productDOrderParam.getPaymentMethod()))
                    .findFirst()
                    .orElseGet(() -> noDiscount);
            resp.getInternetAccessProductPriceItem().setPaymentMethodDiscountRateConfig(discountConfig);
            resp.setPaymentMethodDiscountPrice(resp.getBasicPrice()
                    .multiply(BigDecimal.valueOf(-1))
                    .multiply(BigDecimal.valueOf(100 - discountConfig.getDiscountRate()))
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
        }
        resp.setTotalPrice(resp.getBasicPrice()
                .add(resp.getExportDiscountPrice())
                .add(resp.getContractPeriodDiscountPrice())
                .add(resp.getPaymentCycleDiscountPrice())
                .add(resp.getPaymentMethodDiscountPrice()));
        return resp;
    }

    /**
     * 保存
     *
     * @param productDOrderParam 产品信息
     * @param userInfoVO 用户信息
     * @return
     */
    public int save(InternetAccessOrderSaveParam productDOrderParam, UserInfoVO userInfoVO) {
        InternetAccessOrderPriceResp orderPriceResp = calcTotalPriceAndDiscount(productDOrderParam, userInfoVO, true);
        InternetAccessOrderAmountResp orderAmountResp = getInternetAccessOrderAmountResp(orderPriceResp, productDOrderParam, userInfoVO);
        InternetAccessOrderBeforeChangeAndDetail saveResult = internetAccessOrderManager.save(productDOrderParam, orderAmountResp, userInfoVO);
        return saveResult.getChange().getOrderId();
    }

    /**
     * 得到产品金额
     *
     * @param orderPriceResp
     * @param productDOrderParam
     * @param userInfoVO
     * @return
     */
    private InternetAccessOrderAmountResp getInternetAccessOrderAmountResp(InternetAccessOrderPriceResp orderPriceResp,
                                                                           InternetAccessOrderSaveParam productDOrderParam,
                                                                           UserInfoVO userInfoVO) {
        InternetAccessOrderAmountResp resp = new InternetAccessOrderAmountResp();
        resp.setOrderAmount(orderPriceResp);
        if (userInfoVO.getServiceProviderOrgId() > 0) {
            if (Objects.equals(userInfoVO.getOrgId(), userInfoVO.getServiceProviderOrgId())) {
                resp.setServiceProviderOrderAmount(orderPriceResp);
            } else {
                UserInfoVO serviceProvider = new UserInfoVO();
                serviceProvider.setOrgType(OrgTypeEnum.SERVICE_PROVIDER.getCode());
                serviceProvider.setOrgId(userInfoVO.getServiceProviderOrgId());
                serviceProvider.setParentOrgId(-1);
                InternetAccessOrderPriceResp serviceProviderResp = calcTotalPriceAndDiscount(productDOrderParam, serviceProvider, false);
                resp.setServiceProviderOrderAmount(serviceProviderResp);
            }
        }
        if (userInfoVO.getAgentLevel1OrgId() > 0) {
            if (Objects.equals(userInfoVO.getOrgId(), userInfoVO.getAgentLevel1OrgId())) {
                resp.setAgentLevel1OrderAmount(orderPriceResp);
            } else {
                UserInfoVO agentLevel1 = new UserInfoVO();
                agentLevel1.setOrgType(OrgTypeEnum.AGENT_COMPANY.getCode());
                agentLevel1.setOrgId(userInfoVO.getAgentLevel1OrgId());
                agentLevel1.setParentOrgId(userInfoVO.getServiceProviderOrgId());
                InternetAccessOrderPriceResp agentLevel1Resp = calcTotalPriceAndDiscount(productDOrderParam, agentLevel1,false);
                resp.setAgentLevel1OrderAmount(agentLevel1Resp);
            }
        }
        if (userInfoVO.getAgentLevel2OrgId() > 0) {
            if (Objects.equals(userInfoVO.getOrgId(), userInfoVO.getAgentLevel2OrgId())) {
                resp.setAgentLevel2OrderAmount(orderPriceResp);
            } else {
                UserInfoVO agentLevel2 = new UserInfoVO();
                agentLevel2.setOrgType(OrgTypeEnum.AGENT_COMPANY.getCode());
                agentLevel2.setOrgId(userInfoVO.getAgentLevel2OrgId());
                agentLevel2.setParentOrgId(userInfoVO.getAgentLevel1OrgId());
                InternetAccessOrderPriceResp agentLevel2Resp = calcTotalPriceAndDiscount(productDOrderParam, agentLevel2,false);
                resp.setAgentLevel2OrderAmount(agentLevel2Resp);
            }
        }
        if (userInfoVO.getAgentLevel3OrgId() > 0) {
            if (Objects.equals(userInfoVO.getOrgId(), userInfoVO.getAgentLevel3OrgId())) {
                resp.setAgentLevel3OrderAmount(orderPriceResp);
            } else {
                UserInfoVO agentLevel3 = new UserInfoVO();
                agentLevel3.setOrgType(OrgTypeEnum.AGENT_COMPANY.getCode());
                agentLevel3.setOrgId(userInfoVO.getAgentLevel2OrgId());
                agentLevel3.setParentOrgId(userInfoVO.getAgentLevel1OrgId());
                InternetAccessOrderPriceResp agentLevel3Resp = calcTotalPriceAndDiscount(productDOrderParam, agentLevel3,false);
                resp.setAgentLevel3OrderAmount(agentLevel3Resp);
            }
        }
        if (userInfoVO.getCustomerCompanyOrgId() > 0) {
            resp.setCustomerCompanyOrderAmount(orderPriceResp);
        }
        return resp;
    }

    /**
     * 订单审批
     *
     * @param orderAuditParam
     * @param userInfoVO
     * @return
     */
    public int orderAudit(OrderAuditParam orderAuditParam, UserInfoVO userInfoVO){
        InternetAccessOrderAuditResult auditResult= internetAccessOrderManager.orderAudit(orderAuditParam,userInfoVO);
        if (auditResult.getTransId() != 0) {
            internetAccessDeviceControlService.callDeviceControl(auditResult, userInfoVO.getAppUserInfoId());
        }
        return auditResult.getOrderId();
    }

}
