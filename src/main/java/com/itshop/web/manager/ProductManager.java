package com.itshop.web.manager;

import com.google.common.collect.Lists;
import com.itshop.web.bo.UserInfoVO;
import com.itshop.web.dao.mysql.TProductCustomeCaseDAO;
import com.itshop.web.dao.mysql.TProductDAO;
import com.itshop.web.dao.mysql.TProductItemDAO;
import com.itshop.web.dao.mysql.TProductSceneDAO;
import com.itshop.web.dao.po.*;
import com.itshop.web.dto.request.ProductItemPriceUpdateParam;
import com.itshop.web.dto.request.ProductOemSaveParam;
import com.itshop.web.dto.response.CategoryProductOEMResp;
import com.itshop.web.dto.response.ProductOemCustomeCaseResp;
import com.itshop.web.dto.response.ProductOemResp;
import com.itshop.web.dto.response.ProductOemSceneResp;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author liufenglong
 * @date 2022/7/20
 */
@Service
public class ProductManager {

    @Autowired
    private TProductDAO tProductDAO;

    @Autowired
    private TProductCustomeCaseDAO tProductCustomeCaseDAO;

    @Autowired
    private TProductSceneDAO tProductSceneDAO;

    @Autowired
    TProductItemDAO tProductItemDAO;

    public ProductOemResp getProductOemSimpleInfo(Integer productId) {
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
        List<TProductScene> scenes = tProductSceneDAO.selectByProductId(productId);
        if (CollectionUtils.isNotEmpty(scenes)) {
            scenes.forEach(p -> {
                ProductOemSceneResp sceneResp = new ProductOemSceneResp();
                sceneResp.setSceneName(p.getSceneName());
                sceneResp.setSceneDesc(p.getSceneDesc());
                sceneResp.setPicUrl(p.getPicUrl());
                resp.getScenes().add(sceneResp);
            });
        }
        List<TProductCustomeCase> customeCases = tProductCustomeCaseDAO.selectByProductId(productId);
        if (CollectionUtils.isNotEmpty(customeCases)) {
            customeCases.forEach(p -> {
                ProductOemCustomeCaseResp caseResp = new ProductOemCustomeCaseResp();
                caseResp.setPicUrl(p.getPicUrl());
                caseResp.setCustomeCaseName(p.getCustomeCaseName());
                caseResp.setCustomeCaseDesc(p.getCustomeCaseDesc());
                resp.getCustomerCases().add(caseResp);
            });
        }
        return resp;
    }

    /**
     * 得到 品类、品牌 树形结构
     * @return
     */
    public List<CategoryProductOEMResp> getCategoryProduct() {
        List<CategoryProductOEMResp> result = Lists.newArrayList();
        List<TCategoryProduct> categoryProducts = tProductDAO.selectCategoryProduct();
        if (CollectionUtils.isNotEmpty(categoryProducts)) {
            categoryProducts.stream()
                    .filter(p -> Objects.equals(-1, p.getParentCategoryId()))
                    .sorted(Comparator.comparingInt(TCategoryProduct::getCategoryOrderNum))
                    .forEach(p -> {
                        CategoryProductOEMResp resp = new CategoryProductOEMResp();
                        resp.setIsAgent(true);
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
                    .sorted(Comparator.comparingInt(TCategoryProduct::getCategoryOrderNum))
                    .forEach(p -> {
                        CategoryProductOEMResp resp = new CategoryProductOEMResp();
                        resp.setIsAgent(true);
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
            categoryProducts.stream()
                    .filter(p -> Objects.equals(temp.getId(), p.getCategoryId()))
                    .sorted(Comparator.comparingInt(TCategoryProduct::getProductId))
                    .forEach(p -> {
                        if (p.getProductId() != null) {
                            CategoryProductOEMResp resp = new CategoryProductOEMResp();
                            resp.setIsAgent(true);
                            resp.setId(p.getProductId());
                            resp.setCode(p.getProductCode());
                            resp.setName(p.getProductName());
                            resp.setOrderNum(p.getProductOrderNum());
                            resp.setType("PRODUCT");
                            resp.setEnable(p.getProductEnable());
                            resp.setOemName(p.getProductName());
                            resp.setOemDescribe(p.getProductDescribe());
                            resp.setOemLargerImage(p.getProductLargerImage());
                            resp.setOemSummary(p.getProductSummary());
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
     * 保存产品信息
     *
     * @param oemSaveParam
     * @param userInfoVO
     */
    @Transactional(value = "mysqlTransactionManager", rollbackFor = Exception.class)
    public void saveProduct(ProductOemSaveParam oemSaveParam, UserInfoVO userInfoVO) {
        Date now = new Date();
        TProduct product = new TProduct();
        product.setProductId(oemSaveParam.getProductId());
        product.setProductName(oemSaveParam.getProductOemName());
        product.setProductLargerImage(oemSaveParam.getProductOemLargerImage());
        product.setProductSummary(oemSaveParam.getProductOemSummary());
        product.setProductApplicableCompany(oemSaveParam.getProductOemApplicableCompany());
        product.setProductDescribe(oemSaveParam.getProductOemDescribe());
        product.setModifiedBy(userInfoVO.getAppUserInfoId());
        product.setModifiedTime(now);
        tProductDAO.updateByPrimaryKeySelective(product);
        if (CollectionUtils.isNotEmpty(oemSaveParam.getScenes())) {
            List<TProductScene> scenes = Lists.newArrayList();
            oemSaveParam.getScenes().forEach(p ->{
                TProductScene scene = new TProductScene();
                scene.setProductId(oemSaveParam.getProductId());
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
            tProductSceneDAO.deleteByProductId(oemSaveParam.getProductId(), userInfoVO.getAppUserInfoId());
            tProductSceneDAO.insertBatch(scenes);
        }
        if (CollectionUtils.isNotEmpty(oemSaveParam.getCustomerCases())) {
            List<TProductCustomeCase> customeCaseList = Lists.newArrayList();
            oemSaveParam.getCustomerCases().forEach(p -> {
                TProductCustomeCase customeCase = new TProductCustomeCase();
                customeCase.setProductId(oemSaveParam.getProductId());
                customeCase.setPicUrl(p.getPicUrl());
                customeCase.setCustomeCaseName(p.getCustomeCaseName());
                customeCase.setCustomeCaseDesc(p.getCustomeCaseDesc());
                customeCase.setIsDeleted(false);
                customeCase.setCreaterBy(userInfoVO.getAppUserInfoId());
                customeCase.setCreateTime(now);
                customeCase.setModifiedBy(userInfoVO.getAppUserInfoId());
                customeCase.setModifiedTime(now);
                customeCaseList.add(customeCase);
            });
            tProductCustomeCaseDAO.deleteByProductId(oemSaveParam.getProductId(), userInfoVO.getAppUserInfoId());
            tProductCustomeCaseDAO.insertBatch(customeCaseList);
        }
    }

    /**
     * 更新产品项价格
     *
     * @param updateParam
     * @return
     */
    @Transactional(value = "mysqlTransactionManager", rollbackFor = Exception.class)
    public int  updateProductItemPrice(ProductItemPriceUpdateParam updateParam) {
        TProductItem productItem = new TProductItem();
        productItem.setProductItemId(updateParam.getProductItemId());
        productItem.setDefaultPrice(updateParam.getPrice());
        return tProductItemDAO.updateByPrimaryKeySelective(productItem);
    }
}
