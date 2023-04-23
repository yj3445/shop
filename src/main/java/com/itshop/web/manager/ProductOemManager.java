package com.itshop.web.manager;

import com.google.common.collect.Lists;
import com.itshop.web.annotation.RedisLock;
import com.itshop.web.bo.UserInfoVO;
import com.itshop.web.dao.mysql.*;
import com.itshop.web.dao.po.*;
import com.itshop.web.dto.request.ProductAgentRelationSaveParam;
import com.itshop.web.dto.request.ProductOemItemPriceSaveParam;
import com.itshop.web.dto.request.ProductOemSaveParam;
import com.itshop.web.dto.response.*;
import com.itshop.web.enums.OrgTypeEnum;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 产品OEM管理器
 *
 * @author liufenglong
 * @date 2022/7/21
 */
@Service
public class ProductOemManager {

    @Autowired
    TCategoryDAO tCategoryDAO;

    @Autowired
    TProductDAO tProductDAO;

    @Autowired
    TProductItemDAO tProductItemDAO;

    @Autowired
    TProductOemDAO productOemDAO;

    @Autowired
    TProductOemDiscountRulesDAO tProductOemDiscountRulesDAO;

    @Autowired
    TProductOemItemPriceDAO tProductOemItemPriceDAO;

    @Autowired
    TAgentPriceDAO agentPriceDAO;

    @Autowired
    TProductAgentRelationDAO tProductAgentRelationDAO;

    @Autowired
    TProductOemSceneDAO tProductOemSceneDAO;

    @Autowired
    TProductOemCustomeCaseDAO tProductOemCustomeCaseDAO;

    @Autowired
    private TProductCustomeCaseDAO tProductCustomeCaseDAO;

    @Autowired
    private TProductSceneDAO tProductSceneDAO;

    /**
     * 得到品类、产品(OEM)树
     *
     * @return List<CategoryProductOEMResp>
     */
    public List<CategoryProductOEMResp> getCategoryProductOEM(Integer orgId) {
        List<CategoryProductOEMResp> result = Lists.newArrayList();
        List<TCategoryProductOEM> categoryProducts = productOemDAO.selectCategoryProductOEM(orgId);
        List<TProductOem> productOems = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(categoryProducts)) {
            List<Integer> productIds = categoryProducts.stream()
                    .filter(p -> p.getProductId() != null)
                    .map(TCategoryProductOEM::getProductId)
                    .collect(Collectors.toList());
            productOems = productOemDAO.selectByProductAndOrgIds(productIds, orgId);
            categoryProducts.stream()
                    .filter(p -> Objects.equals(-1, p.getParentCategoryId()))
                    .sorted(Comparator.comparingInt(TCategoryProductOEM::getCategoryOrderNum))
                    .forEach(p -> {
                        CategoryProductOEMResp resp = new CategoryProductOEMResp();
                        resp.setId(p.getCategoryId());
                        resp.setCode(p.getCategoryCode());
                        resp.setName(p.getCategoryName());
                        resp.setOrderNum(p.getCategoryOrderNum());
                        resp.setType("CATEGORY");
                        resp.setEnable(p.getCategoryEnable());
                        resp.setPath(p.getCategoryPath());
                        resp.setOemName(p.getCategoryName());
                        boolean noneMatch = result.stream().noneMatch(t -> Objects.equals(resp.getId(), t.getId())
                                && "CATEGORY".equalsIgnoreCase(t.getType()));
                        if (noneMatch) {
                            result.add(resp);
                        }
                    });
        }
        Queue<CategoryProductOEMResp> queue = new LinkedList<>();
        result.forEach(queue::offer);
        while (!queue.isEmpty()) {
            CategoryProductOEMResp temp = queue.poll();
            categoryProducts.stream()
                    .filter(p -> Objects.equals(temp.getId(), p.getParentCategoryId()))
                    .sorted(Comparator.comparingInt(TCategoryProductOEM::getCategoryOrderNum))
                    .forEach(p -> {
                        CategoryProductOEMResp resp = new CategoryProductOEMResp();
                        resp.setId(p.getCategoryId());
                        resp.setCode(p.getCategoryCode());
                        resp.setName(p.getCategoryName());
                        resp.setOrderNum(p.getCategoryOrderNum());
                        resp.setType("CATEGORY");
                        resp.setEnable(p.getCategoryEnable());
                        resp.setPath(p.getCategoryPath());
                        resp.setOemName(p.getCategoryName());
                        boolean noneMatch = temp.getChildren().stream().noneMatch(t -> Objects.equals(resp.getId(), t.getId())
                                && "CATEGORY".equalsIgnoreCase(t.getType()));
                        if (noneMatch) {
                            temp.getChildren().add(resp);
                            queue.offer(resp);
                        }
                    });
            List<TProductOem> finalProductOems = productOems;
            categoryProducts.stream()
                    .filter(p -> Objects.equals(temp.getId(), p.getCategoryId()))
                    .sorted(Comparator.comparingInt(TCategoryProductOEM::getProductId))
                    .forEach(p -> {
                        if (p.getProductId() != null) {
                            CategoryProductOEMResp resp = new CategoryProductOEMResp();
                            resp.setId(p.getProductId());
                            resp.setCode(p.getProductCode());
                            resp.setName(p.getProductName());
                            resp.setOrderNum(p.getProductOrderNum());
                            resp.setType("PRODUCT");
                            resp.setEnable(p.getProductEnable());
                            resp.setOemName(p.getProductName());
                            finalProductOems.stream().filter(t -> Objects.equals(p.getProductId(), t.getProductId()))
                                    .findFirst()
                                    .ifPresent(t -> {
                                        resp.setOemId(t.getProductOemId());
                                        resp.setOemName(t.getProductOemName());
                                        resp.setOemDescribe(t.getProductOemDescribe());
                                        resp.setOemLargerImage(t.getProductOemLargerImage());
                                        resp.setOemSummary(t.getProductOemSummary());
                                    });
                            resp.setPath(temp.getPath() + "/" + p.getProductPath());
                            boolean noneMatch = temp.getChildren().stream().noneMatch(t -> Objects.equals(resp.getId(), t.getId())
                                    && "PRODUCT".equalsIgnoreCase(t.getType()));
                            if (noneMatch) {
                                temp.getChildren().add(resp);
                            }
                        }
                    });
        }
        return result;
    }

    /**
     * 得到品类(产品)代理关系树
     *
     * @return List<CategoryProductResp>
     */
    public List<CategoryProductAgentResp> getCategoryProductAgentTree(UserInfoVO userInfoVO) {
        List<CategoryProductAgentResp> result = Lists.newArrayList();
        List<TCategoryProductAgent> categoryProducts = tProductAgentRelationDAO.selectCategoryProductAgent(userInfoVO.getOrgId());
        if (CollectionUtils.isNotEmpty(categoryProducts)) {
            if (OrgTypeEnum.SERVICE_PROVIDER.getCode().equals(userInfoVO.getOrgType())) {
                categoryProducts.forEach(p -> p.setProductIsAgent(true));
            }
            categoryProducts.stream()
                    .filter(p -> Objects.equals(-1, p.getParentCategoryId()))
                    .sorted(Comparator.comparingInt(TCategoryProductAgent::getCategoryOrderNum))
                    .forEach(p -> {
                        CategoryProductAgentResp resp = new CategoryProductAgentResp();
                        resp.setId(p.getCategoryId());
                        resp.setCode(p.getCategoryCode());
                        resp.setName(p.getCategoryName());
                        resp.setOrderNum(p.getCategoryOrderNum());
                        resp.setType("CATEGORY");
                        boolean noneMatch = result.stream().noneMatch(t -> Objects.equals(resp.getId(), t.getId())
                                && "CATEGORY".equalsIgnoreCase(t.getType()));
                        if (noneMatch) {
                            result.add(resp);
                        }
                    });
            Queue<CategoryProductAgentResp> queue = new LinkedList<>();
            result.forEach(queue::offer);
            while (!queue.isEmpty()) {
                CategoryProductAgentResp temp = queue.poll();
                categoryProducts.stream()
                        .filter(p -> Objects.equals(temp.getId(), p.getParentCategoryId()))
                        .sorted(Comparator.comparingInt(TCategoryProductAgent::getCategoryOrderNum))
                        .forEach(p -> {
                            CategoryProductAgentResp resp = new CategoryProductAgentResp();
                            resp.setId(p.getCategoryId());
                            resp.setCode(p.getCategoryCode());
                            resp.setName(p.getCategoryName());
                            resp.setOrderNum(p.getCategoryOrderNum());
                            resp.setType("CATEGORY");
                            boolean noneMatch = temp.getChildren().stream().noneMatch(t -> Objects.equals(resp.getId(), t.getId())
                                    && "CATEGORY".equalsIgnoreCase(t.getType()));
                            if (noneMatch) {
                                temp.getChildren().add(resp);
                                queue.offer(resp);
                            }
                        });
                categoryProducts.stream()
                        .filter(p -> Objects.equals(temp.getId(), p.getCategoryId()))
                        .sorted(Comparator.comparingInt(TCategoryProductAgent::getProductId))
                        .forEach(p -> {
                            if (p.getProductId() != null) {
                                CategoryProductAgentResp resp = new CategoryProductAgentResp();
                                resp.setId(p.getProductId());
                                resp.setCode(p.getProductCode());
                                resp.setName(p.getProductName());
                                resp.setOrderNum(p.getProductOrderNum());
                                resp.setType("PRODUCT");
                                resp.setIsAgent(p.getProductIsAgent());
                                resp.setAgentRelationId(p.getProductAgentRelationId());
                                boolean noneMatch = temp.getChildren().stream().noneMatch(t -> Objects.equals(resp.getId(), t.getId())
                                        && "PRODUCT".equalsIgnoreCase(t.getType()));
                                if (noneMatch) {
                                    temp.getChildren().add(resp);
                                }
                            }
                        });
            }
        }

        return result;
    }


    /**
     * 根据产品id和组织id得到产品OEM信息
     *
     * @param productId 产品id
     * @param orgId     组织id
     * @return
     */
    public ProductOemResp getProductOemSimpleInfo(Integer productId, Integer orgId) {
        ProductOemResp resp = new ProductOemResp();
        TProduct product = tProductDAO.selectByPrimaryKey(productId);
        if (product == null) {
            return resp;
        }
        resp.setProductId(product.getProductId());
        resp.setCategoryId(product.getCategoryId());
        resp.setProductCode(product.getProductCode());
        resp.setProductName(product.getProductName());
        resp.setProductOemName(product.getProductName());
        resp.setProductOemDescribe(product.getProductDescribe());
        resp.setProductOemSummary(product.getProductSummary());
        resp.setProductOemApplicableCompany(product.getProductApplicableCompany());

        TProductOem productOem = productOemDAO.selectByProductAndOrgId(productId, orgId);
        if (productOem != null) {
            BeanUtils.copyProperties(productOem, resp);
            List<TProductOemScene> oemScenes = tProductOemSceneDAO.selectByProductOemId(productOem.getProductOemId());
            if (CollectionUtils.isNotEmpty(oemScenes)) {
                oemScenes.forEach(p -> {
                    ProductOemSceneResp sceneResp = new ProductOemSceneResp();
                    sceneResp.setProductOemSceneId(p.getProductOemSceneId());
                    sceneResp.setSceneName(p.getSceneName());
                    sceneResp.setSceneDesc(p.getSceneDesc());
                    sceneResp.setPicUrl(p.getPicUrl());
                    resp.getScenes().add(sceneResp);
                });
            }
            List<TProductOemCustomeCase> customeCases = tProductOemCustomeCaseDAO.selectByProductOemId(productOem.getProductOemId());
            if (CollectionUtils.isNotEmpty(customeCases)) {
                customeCases.forEach(p -> {
                    ProductOemCustomeCaseResp caseResp = new ProductOemCustomeCaseResp();
                    caseResp.setProductOemCustomeCaseId(p.getProductOemCustomeCaseId());
                    caseResp.setPicUrl(p.getPicUrl());
                    caseResp.setCustomeCaseName(p.getCustomeCaseName());
                    caseResp.setCustomeCaseDesc(p.getCustomeCaseDesc());
                    resp.getCustomerCases().add(caseResp);
                });
            }
        }
        if(CollectionUtils.isEmpty(resp.getScenes())){
            List<TProductScene> scenes=  tProductSceneDAO.selectByProductId(productId);
            if (CollectionUtils.isNotEmpty(scenes)) {
                scenes.forEach(p -> {
                    ProductOemSceneResp sceneResp = new ProductOemSceneResp();
                    sceneResp.setSceneName(p.getSceneName());
                    sceneResp.setSceneDesc(p.getSceneDesc());
                    sceneResp.setPicUrl(p.getPicUrl());
                    resp.getScenes().add(sceneResp);
                });
            }
        }
        if(CollectionUtils.isEmpty(resp.getCustomerCases())){
            List<TProductCustomeCase> customeCases= tProductCustomeCaseDAO.selectByProductId(productId);
            if(CollectionUtils.isNotEmpty(customeCases)){
                customeCases.forEach(p ->{
                    ProductOemCustomeCaseResp caseResp = new ProductOemCustomeCaseResp();
                    caseResp.setPicUrl(p.getPicUrl());
                    caseResp.setCustomeCaseName(p.getCustomeCaseName());
                    caseResp.setCustomeCaseDesc(p.getCustomeCaseDesc());
                    resp.getCustomerCases().add(caseResp);
                });
            }
        }
        return resp;
    }

    /**
     * 根据产品id和组织id得到产品OEM信息
     *
     * @param productId 产品id
     * @param orgId     组织id
     * @return
     */
    public ProductOemResp selectByProductAndOrgId(Integer productId, Integer orgId) {
        ProductOemResp resp = new ProductOemResp();
        TProduct product = tProductDAO.selectByPrimaryKey(productId);
        if (product == null) {
            return resp;
        }
        resp.setProductId(product.getProductId());
        resp.setCategoryId(product.getCategoryId());
        resp.setProductCode(product.getProductCode());
        resp.setProductName(product.getProductName());
        //折扣信息
        List<TProductItem> productItemList = tProductItemDAO.selectByProductId(productId);
        if (CollectionUtils.isNotEmpty(productItemList)) {
            productItemList.stream().filter(p -> BooleanUtils.isTrue(p.getDiscountItem()))
                    .sorted(Comparator.comparingInt(TProductItem::getItemTypeOrderNum))
                    .forEach(p -> {
                        ProductOemDiscountRulesResp itemTypeResp = new ProductOemDiscountRulesResp();
                        itemTypeResp.setItemType(p.getItemType());
                        itemTypeResp.setItemTypeName(p.getItemTypeName());
                        itemTypeResp.setItemTypeOrderNum(p.getItemTypeOrderNum());
                        if (!resp.getDiscountRules().contains(itemTypeResp)) {
                            resp.getDiscountRules().add(itemTypeResp);
                        }
                    });
            resp.getDiscountRules().forEach(p -> {
                productItemList.stream().filter(t -> BooleanUtils.isTrue(t.getDiscountItem()))
                        .filter(t -> p.getItemType().equalsIgnoreCase(t.getItemType()))
                        .sorted(Comparator.comparingInt(TProductItem::getOrderNum))
                        .forEach(t -> {
                            ProductOemDiscountRulesItemResp itemResp = new ProductOemDiscountRulesItemResp();
                            itemResp.setProductItemId(t.getProductItemId());
                            itemResp.setItemName(t.getItemName());
                            itemResp.setItemValue(t.getItemValue());
                            itemResp.setOrderNum(t.getOrderNum());
                            itemResp.setDefaultDiscount(t.getDefaultDiscount());
                            itemResp.setDiscountRate(t.getDefaultDiscount().intValue());
                            if (!p.getRulesItems().contains(itemResp)) {
                                p.getRulesItems().add(itemResp);
                            }
                        });
            });
        }
        TProductOem productOem = productOemDAO.selectByProductAndOrgId(productId, orgId);
        if (productOem != null) {
            BeanUtils.copyProperties(productOem, resp);
            //客户场景
            List<TProductOemScene> scenes = tProductOemSceneDAO.selectByProductOemId(productOem.getProductOemId());
            if (CollectionUtils.isNotEmpty(scenes)) {
                scenes.forEach(p -> {
                    ProductOemSceneResp sceneResp = new ProductOemSceneResp();
                    sceneResp.setProductOemSceneId(p.getProductOemSceneId());
                    sceneResp.setPicUrl(p.getPicUrl());
                    sceneResp.setSceneName(p.getSceneName());
                    sceneResp.setSceneDesc(p.getSceneDesc());
                    resp.getScenes().add(sceneResp);
                });
            }
            //用户案例
            List<TProductOemCustomeCase> customerCases = tProductOemCustomeCaseDAO.selectByProductOemId(productOem.getProductOemId());
            if (CollectionUtils.isNotEmpty(customerCases)) {
                customerCases.forEach(p -> {
                    ProductOemCustomeCaseResp caseResp = new ProductOemCustomeCaseResp();
                    caseResp.setProductOemCustomeCaseId(p.getProductOemCustomeCaseId());
                    caseResp.setPicUrl(p.getPicUrl());
                    caseResp.setCustomeCaseName(p.getCustomeCaseName());
                    caseResp.setCustomeCaseDesc(p.getCustomeCaseDesc());
                    resp.getCustomerCases().add(caseResp);
                });
            }
            //折扣信息
            List<TProductOemDiscountRules> discountRules = tProductOemDiscountRulesDAO.selectByProductOemId(productOem.getProductOemId());
            if (CollectionUtils.isNotEmpty(discountRules)) {
                resp.getDiscountRules().forEach(p -> {
                    p.getRulesItems().forEach(t -> {
                        discountRules.stream().filter(r -> Objects.equals(r.getProductItemId(), t.getProductItemId()))
                                .findFirst()
                                .ifPresent(r -> {
                                    t.setProductOemDiscountRulesId(r.getProductOemDiscountRulesId());
                                    t.setDiscountRate(r.getDiscountRate());
                                });
                    });
                });
            }
        }
        return resp;
    }

    /**
     * 保存OEM产品信息及折扣规则
     *
     * @param oemSaveParam
     * @return
     */
    @Transactional(value = "mysqlTransactionManager", rollbackFor = Exception.class)
    public void saveProductOemAndDiscountRules(ProductOemSaveParam oemSaveParam, UserInfoVO userInfoVO) {
        TProductOem oldInfo = productOemDAO.selectByProductAndOrgId(oemSaveParam.getProductId(), userInfoVO.getOrgId());
        if (oldInfo == null) {
            //产品OEM信息
            TProductOem productOem = insertProductOem(oemSaveParam, userInfoVO);
            //更新[OEM产品项价格表]信息
            updateProductOemItemPrice(userInfoVO,productOem);
            //客户场景
            insertScene(oemSaveParam, userInfoVO, productOem);
            //用户案例
            insertCustomerCase(oemSaveParam, userInfoVO, productOem);
            //折扣规则
            insertDiscountRules(oemSaveParam,userInfoVO,productOem);
        } else {
            //产品OEM信息
            updateProductOem(oemSaveParam, userInfoVO, oldInfo);
            //客户场景
            insertScene(oemSaveParam, userInfoVO, oldInfo);
            //用户案例
            insertCustomerCase(oemSaveParam, userInfoVO, oldInfo);
            //折扣规则
            updateDiscountRules(oemSaveParam, userInfoVO);
        }
    }

    private void updateProductOemItemPrice(UserInfoVO userInfoVO, TProductOem productOem) {
        TProductOemItemPrice oemItemPrice = new TProductOemItemPrice();
        oemItemPrice.setProductId(productOem.getProductId());
        oemItemPrice.setOrgId(userInfoVO.getOrgId());
        oemItemPrice.setProductOemId(productOem.getProductOemId());
        oemItemPrice.setModifiedBy(userInfoVO.getAppUserInfoId());
        tProductOemItemPriceDAO.updateProductOemId(oemItemPrice);
    }

    private void updateDiscountRules(ProductOemSaveParam oemSaveParam, UserInfoVO userInfoVO) {
        Date now = new Date();
        if (CollectionUtils.isNotEmpty(oemSaveParam.getDiscountRules())) {
            oemSaveParam.getDiscountRules().forEach(p -> {
                TProductOemDiscountRules discountRules1 = new TProductOemDiscountRules();
                discountRules1.setProductOemDiscountRulesId(p.getProductOemDiscountRulesId());
                discountRules1.setProductItemId(p.getProductItemId());
                discountRules1.setDiscountRate(p.getDiscountRate());
                discountRules1.setModifiedBy(userInfoVO.getAppUserInfoId());
                discountRules1.setModifiedTime(now);
                tProductOemDiscountRulesDAO.updateByPrimaryKeySelective(discountRules1);
            });
        }
    }

    private void updateProductOem(ProductOemSaveParam oemSaveParam, UserInfoVO userInfoVO, TProductOem oldInfo) {
        Date now = new Date();
        oldInfo.setProductOemName(oemSaveParam.getProductOemName());
        oldInfo.setProductOemLargerImage(oemSaveParam.getProductOemLargerImage());
        oldInfo.setProductOemDescribe(oemSaveParam.getProductOemDescribe());
        oldInfo.setProductOemSummary(oemSaveParam.getProductOemSummary());
        oldInfo.setProductOemApplicableCompany(oemSaveParam.getProductOemApplicableCompany());
        oldInfo.setModifiedBy(userInfoVO.getAppUserInfoId());
        oldInfo.setModifiedTime(now);
        oldInfo.setProductOemId(oldInfo.getProductOemId());
        productOemDAO.updateByPrimaryKeySelective(oldInfo);
    }


    private TProductOem insertProductOem(ProductOemSaveParam oemSaveParam, UserInfoVO userInfoVO) {
        Date now = new Date();
        TProductOem productOem = new TProductOem();
        productOem.setProductOemName(oemSaveParam.getProductOemName());
        productOem.setProductOemLargerImage(oemSaveParam.getProductOemLargerImage());
        productOem.setProductOemDescribe(oemSaveParam.getProductOemDescribe());
        productOem.setProductOemSummary(oemSaveParam.getProductOemSummary());
        productOem.setProductOemApplicableCompany(oemSaveParam.getProductOemApplicableCompany());
        productOem.setModifiedBy(userInfoVO.getAppUserInfoId());
        productOem.setModifiedTime(now);
        productOem.setProductId(oemSaveParam.getProductId());
        productOem.setOrgId(userInfoVO.getOrgId());
        productOem.setIsDeleted(false);
        productOem.setCreaterBy(userInfoVO.getAppUserInfoId());
        productOem.setCreateTime(now);
        productOemDAO.insertSelective(productOem);
        return productOem;
    }

    private void insertScene(ProductOemSaveParam oemSaveParam, UserInfoVO userInfoVO,TProductOem productOem){
        Date now = new Date();
        tProductOemSceneDAO.deleteByProductOemId(productOem.getProductOemId(),userInfoVO.getAppUserInfoId());
        if(CollectionUtils.isNotEmpty(oemSaveParam.getScenes())){
            List<TProductOemScene> scenes = Lists.newArrayList();
            oemSaveParam.getScenes().forEach(p -> {
                TProductOemScene scene  = new TProductOemScene();
                scene.setOrgId(userInfoVO.getOrgId());
                scene.setProductOemId(productOem.getProductOemId());
                scene.setProductId(productOem.getProductId());
                scene.setPicUrl(p.getPicUrl());
                scene.setSceneName(p.getSceneName());
                scene.setSceneDesc(p.getSceneDesc());
                scene.setIsDeleted(false);
                scene.setCreaterBy(userInfoVO.getAppUserInfoId());
                scene.setCreateTime(now);
                scene.setModifiedBy(userInfoVO.getAppUserInfoId());
                scene.setModifiedTime(now);
                scenes.add(scene);
            });
            tProductOemSceneDAO.insertBatch(scenes);
        }
    }

    private void insertCustomerCase(ProductOemSaveParam oemSaveParam, UserInfoVO userInfoVO,TProductOem productOem) {
        Date now = new Date();
        tProductOemCustomeCaseDAO.deleteByProductOemId(productOem.getProductOemId(),userInfoVO.getAppUserInfoId());
        List<TProductOemCustomeCase> customeCases = Lists.newArrayList();
        if(CollectionUtils.isNotEmpty(oemSaveParam.getCustomerCases())){
            oemSaveParam.getCustomerCases().forEach(p ->{
                TProductOemCustomeCase customeCase = new TProductOemCustomeCase();
                customeCase.setOrgId(userInfoVO.getOrgId());
                customeCase.setProductOemId(productOem.getProductOemId());
                customeCase.setProductId(productOem.getProductId());
                customeCase.setPicUrl(p.getPicUrl());
                customeCase.setCustomeCaseName(p.getCustomeCaseName());
                customeCase.setCustomeCaseDesc(p.getCustomeCaseDesc());
                customeCase.setIsDeleted(false);
                customeCase.setCreaterBy(userInfoVO.getAppUserInfoId());
                customeCase.setCreateTime(now);
                customeCase.setModifiedBy(userInfoVO.getAppUserInfoId());
                customeCase.setModifiedTime(now);
                customeCases.add(customeCase);
            });
            tProductOemCustomeCaseDAO.insertBatch(customeCases);
        }
    }

    private void insertDiscountRules(ProductOemSaveParam oemSaveParam, UserInfoVO userInfoVO,TProductOem productOem) {
        Date now = new Date();
        tProductOemDiscountRulesDAO.deleteByProductOemId(productOem.getProductOemId(), userInfoVO.getAppUserInfoId());
        List<TProductOemDiscountRules> discountRulesList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(oemSaveParam.getDiscountRules())) {
            oemSaveParam.getDiscountRules().forEach(p -> {
                TProductOemDiscountRules discountRules1 = new TProductOemDiscountRules();
                discountRules1.setProductOemId(productOem.getProductOemId());
                discountRules1.setProductId(productOem.getProductId());
                discountRules1.setOrgId(productOem.getOrgId());
                discountRules1.setProductItemId(p.getProductItemId());
                discountRules1.setDiscountRate(p.getDiscountRate());
                discountRules1.setIsDeleted(false);
                discountRules1.setCreaterBy(userInfoVO.getAppUserInfoId());
                discountRules1.setCreateTime(now);
                discountRules1.setModifiedBy(userInfoVO.getAppUserInfoId());
                discountRules1.setModifiedTime(now);
                discountRulesList.add(discountRules1);
            });
            tProductOemDiscountRulesDAO.insertBatch(discountRulesList);
        }
    }

    /**
     * 根据产品ID及组织ID得到价格项列表信息
     *
     * @param productId 产品ID
     * @param userInfoVO  当前登录用户信息
     * @return
     */
    public List<ProductOemPriceListResp> selectProductOemItemPrice(Integer productId, UserInfoVO userInfoVO) {
        List<ProductOemPriceListResp> resp = Lists.newArrayList();
        TProduct product = tProductDAO.selectByPrimaryKey(productId);
        if (product == null) {
            return resp;
        }
        TCategory category = tCategoryDAO.selectByPrimaryKey(product.getCategoryId());
        if (category == null) {
            return resp;
        }
        List<TAgentPrice> agentPriceList = agentPriceDAO.selectByOrgId(userInfoVO.getOrgId());
        List<TProductItem> productItemList = tProductItemDAO.selectByProductId(productId);
        if (CollectionUtils.isNotEmpty(productItemList)) {
            productItemList.stream()
                    .filter(t -> BooleanUtils.isTrue(t.getPriceItem()))
                    .sorted(Comparator.comparingInt(TProductItem::getOrderNum))
                    .forEach(t -> {
                        ProductOemPriceListResp listResp = new ProductOemPriceListResp();
                        listResp.setCategoryId(category.getCategoryId());
                        listResp.setCategoryCode(category.getCategoryCode());
                        listResp.setCategoryName(category.getCategoryName());
                        listResp.setProductId(product.getProductId());
                        listResp.setProductCode(product.getProductCode());
                        listResp.setProductName(product.getProductName());
                        listResp.setProductItemId(t.getProductItemId());
                        listResp.setItemType(t.getItemType());
                        listResp.setItemTypeName(t.getItemTypeName());
                        listResp.setItemName(t.getItemName());
                        listResp.setItemValue(t.getItemValue());
                        listResp.setDefaultPrice(t.getDefaultPrice());
                        listResp.setDefaultPriceUnit(t.getDefaultPriceUnit());
                        if (CollectionUtils.isNotEmpty(agentPriceList)) {
                            listResp.setItems(Lists.newArrayList());
                            agentPriceList.forEach(p -> {
                                ProductOemPriceListResp.ProductOemItemPriceResp itemPriceResp = new ProductOemPriceListResp.ProductOemItemPriceResp();
                                itemPriceResp.setAgentPriceId(p.getAgentPriceId());
                                itemPriceResp.setPriceName(p.getPriceName());
                                itemPriceResp.setPriceType(p.getPriceType());
                                itemPriceResp.setPrice(BigDecimal.ZERO);
                                itemPriceResp.setPriceUnit(t.getDefaultPriceUnit());
                                listResp.getItems().add(itemPriceResp);
                            });
                        }
                        resp.add(listResp);
                    });
        }
        List<TProductOemItemPrice> itemPrices = tProductOemItemPriceDAO.selectByProductAndOrgId(productId, userInfoVO.getOrgId());
        if (CollectionUtils.isNotEmpty(itemPrices)) {
            itemPrices.forEach(p -> {
                resp.stream().filter(t -> Objects.equals(t.getProductItemId(), p.getProductItemId()))
                        .findFirst()
                        .ifPresent(t -> {
                            t.getItems().stream().filter(s -> Objects.equals(p.getAgentPriceId(), s.getAgentPriceId()))
                                    .findFirst()
                                    .ifPresent(s -> {
                                        s.setProductOemItemPriceId(p.getProductOemItemPriceId());
                                        s.setPrice(p.getPrice());
                                        s.setPriceUnit(p.getPriceUnit());
                                    });
                        });
            });
        }
        TProductOem productOem = productOemDAO.selectByProductAndOrgId(productId, userInfoVO.getOrgId());
        if (productOem != null) {
            resp.stream().filter(p -> Objects.equals(p.getProductId(), productOem.getProductId()))
                    .forEach(t -> {
                        t.setProductOemId(productOem.getProductOemId());
                    });
        }
        //代理商
        if (OrgTypeEnum.AGENT_COMPANY.getCode().equals(userInfoVO.getOrgType())) {
            resp.forEach(p -> p.setDefaultPrice(BigDecimal.ZERO));
            //上级设置的价格
            List<TProductOemItemPrice> productOemItemPriceList = tProductOemItemPriceDAO.selectByTargetOrgIdAndProductId(userInfoVO.getOrgId(), userInfoVO.getParentOrgId(), productId);
            if (CollectionUtils.isNotEmpty(productOemItemPriceList)) {
                resp.forEach(p -> {
                    productOemItemPriceList.stream().filter(t -> t.getProductItemId().equals(p.getProductItemId())).findFirst().ifPresent(
                            tProductOemItemPrice -> {
                                p.setDefaultPrice(tProductOemItemPrice.getPrice());
                                p.setDefaultPriceUnit(tProductOemItemPrice.getPriceUnit());
                            }
                    );
                });
            }
        }
        return resp;
    }

    /**
     * 保存 OEM产品项&定价序列--价格设置
     *
     * @param saveParam
     * @param userInfoVO
     */
    @Transactional(value = "mysqlTransactionManager", rollbackFor = Exception.class)
    @RedisLock(key = "'orgId:'+ #userInfoVO.orgId+':productItemId:'+ #saveParam.productItemId+':agentPriceId:'+ #saveParam.agentPriceId+':ItemPriceSave'")
    public void saveProductOemItemPrice(ProductOemItemPriceSaveParam saveParam, UserInfoVO userInfoVO) {
        Date now = new Date();

        TProductOemItemPrice oemItemPrice = new TProductOemItemPrice();
        BeanUtils.copyProperties(saveParam, oemItemPrice);
        oemItemPrice.setModifiedBy(userInfoVO.getAppUserInfoId());
        oemItemPrice.setModifiedTime(now);

        TProductOemItemPrice oldInfo= tProductOemItemPriceDAO.selectByProductItemIdAndAgentPriceId(saveParam.getProductItemId(), saveParam.getAgentPriceId());
        if (oldInfo !=null) {
            oemItemPrice.setProductOemItemPriceId(oldInfo.getProductOemItemPriceId());
            tProductOemItemPriceDAO.updateByPrimaryKeySelective(oemItemPrice);
        } else {
            oemItemPrice.setOrgId(userInfoVO.getOrgId());
            oemItemPrice.setCreaterBy(userInfoVO.getAppUserInfoId());
            oemItemPrice.setCreateTime(now);
            oemItemPrice.setIsDeleted(false);
            tProductOemItemPriceDAO.insertSelective(oemItemPrice);
        }
    }


    /**
     * 保存产品代理管理
     *
     * @param saveParam
     * @param userInfoVO
     */
    @Transactional(value = "mysqlTransactionManager", rollbackFor = Exception.class)
    public void saveProductAgentRelation(ProductAgentRelationSaveParam saveParam, UserInfoVO userInfoVO) {
        TProductAgentRelation persistence = tProductAgentRelationDAO.selectByOrgIdAndProductId(userInfoVO.getOrgId(), saveParam.getProductId());
        TProductAgentRelation agentRelation = new TProductAgentRelation();
        agentRelation.setProductId(saveParam.getProductId());
        agentRelation.setIsAgent(saveParam.getIsAgent());
        agentRelation.setModifiedBy(userInfoVO.getAppUserInfoId());
        agentRelation.setModifiedTime(new Date());
        if (persistence != null) {
            agentRelation.setProductAgentRelationId(persistence.getProductAgentRelationId());
            tProductAgentRelationDAO.updateByPrimaryKeySelective(agentRelation);
        } else {
            agentRelation.setOrgId(userInfoVO.getOrgId());
            agentRelation.setIsDeleted(false);
            agentRelation.setCreaterBy(userInfoVO.getAppUserInfoId());
            agentRelation.setCreateTime(new Date());
            tProductAgentRelationDAO.insertSelective(agentRelation);
        }
    }
}
