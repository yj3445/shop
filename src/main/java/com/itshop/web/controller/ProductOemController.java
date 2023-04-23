package com.itshop.web.controller;

import com.itshop.web.annotation.PrintReqResp;
import com.itshop.web.bo.UserInfoVO;
import com.itshop.web.dto.RetResult;
import com.itshop.web.dto.RetWrapper;
import com.itshop.web.dto.request.ProductAgentRelationSaveParam;
import com.itshop.web.dto.request.ProductOemItemPriceSaveParam;
import com.itshop.web.dto.request.ProductOemSaveParam;
import com.itshop.web.dto.response.CategoryProductAgentResp;
import com.itshop.web.dto.response.CategoryProductOEMResp;
import com.itshop.web.dto.response.ProductOemPriceListResp;
import com.itshop.web.dto.response.ProductOemResp;
import com.itshop.web.enums.OrgTypeEnum;
import com.itshop.web.manager.ProductManager;
import com.itshop.web.manager.ProductOemManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * 产品OEM设置-控制器
 *
 * @author liufenglong
 * @date 2022/7/20
 */
@Slf4j
@RestController
@RequestMapping("/ProductOem")
@Api(value = "ProductOemController", description = "产品OEM设置-控制器")
public class ProductOemController extends BaseController {

    @Autowired
    ProductManager productManager;

    @Autowired
    ProductOemManager productOemManager;

    /**
     * 得到品类(产品)OEM树形结构 [首页 使用到该接口]
     *
     * @return RetResult<List < CategoryProductOEMResp>>
     */
    @GetMapping("/CategoryProductOEMTree")
    @ApiOperation(value = "得到品类(产品)OEM树形结构")
    public RetResult<List<CategoryProductOEMResp>> getCategoryProductOEMTree() {
        List<CategoryProductOEMResp> tree;
        UserInfoVO userInfoVO = getUserInfoVO();
        if (Objects.equals(OrgTypeEnum.SERVICE_PROVIDER.getCode(), userInfoVO.getOrgType())) {
            tree = productManager.getCategoryProduct();
        } else {
            tree = productOemManager.getCategoryProductOEM(getAgentOrgId());
        }
        return RetWrapper.success(tree);
    }

    /**
     * 得到代理品类产品树形结构
     *
     * @return RetResult<List < CategoryProductResp>>
     */
    @GetMapping("/CategoryProductTree")
    @ApiOperation(value = "得到代理品类产品树形结构")
    public RetResult<List<CategoryProductAgentResp>> getCategoryProductTree() {
        List<CategoryProductAgentResp> tree = productOemManager.getCategoryProductAgentTree(getUserInfoVO());
        return RetWrapper.success(tree);
    }

    /**
     * 得到 产品OEM 信息（首页使用）
     *
     * @param productId 产品ID
     * @return RetResult<ProductOemResp>
     */
    @GetMapping("/simple/getProductOem")
    @ApiOperation(value = "得到产品OEM信息")
    public RetResult<ProductOemResp> getProductOemSimpleInfo(@RequestParam("productId") Integer productId) {
        UserInfoVO userInfoVO = getUserInfoVO();
        ProductOemResp oemResp = null;
        if (Objects.equals(OrgTypeEnum.SERVICE_PROVIDER.getCode(), userInfoVO.getOrgType())) {
            oemResp = productManager.getProductOemSimpleInfo(productId);
        } else {
            oemResp = productOemManager.getProductOemSimpleInfo(productId, getAgentOrgId());
        }
        return RetWrapper.success(oemResp);
    }


    /**
     * 得到产品OEM信息及折扣信息
     *
     * @param productId 产品ID
     * @return RetResult<ProductOemResp>
     */
    @GetMapping("/getProductOem")
    @ApiOperation(value = "得到产品OEM信息及折扣信息")
    public RetResult<ProductOemResp> getProductOem(@RequestParam("productId") Integer productId) {
        UserInfoVO userInfoVO = getUserInfoVO();
        ProductOemResp oemResp = productOemManager.selectByProductAndOrgId(productId, userInfoVO.getOrgId());
        return RetWrapper.success(oemResp);
    }

    /**
     * 保存OEM产品信息及折扣规则
     *
     * @param oemSaveParam OEM产品信息及折扣规则
     * @return RetResult<Void>
     */
    @PrintReqResp
    @PostMapping("/save")
    @ApiOperation(value = "保存OEM产品信息及折扣规则")
    public RetResult<Void> saveProductOem(@RequestBody ProductOemSaveParam oemSaveParam) {
        UserInfoVO userInfoVO= getUserInfoVO();
        if(Objects.equals(OrgTypeEnum.SERVICE_PROVIDER.getCode(),userInfoVO.getOrgType())){
            productManager.saveProduct(oemSaveParam,userInfoVO);
            return RetWrapper.success();
        }
        else if (Objects.equals(OrgTypeEnum.AGENT_COMPANY.getCode(),userInfoVO.getOrgType())) {
            productOemManager.saveProductOemAndDiscountRules(oemSaveParam, userInfoVO);
            return RetWrapper.success();
        }
        else {
            return RetWrapper.failure("您无操作权限，请联系管理员");
        }
    }

    /**
     * 根据产品ID得到价格项列表信息
     *
     * @param productId 产品ID
     * @return RetResult<List < ProductOemPriceListResp>>
     */
    @GetMapping("/itemPrice/getPrice")
    @ApiOperation(value = "根据产品ID得到价格项列表信息")
    public RetResult<List<ProductOemPriceListResp>> getProductOemItemPrice(@RequestParam("productId") Integer productId) {
        List<ProductOemPriceListResp> listResps = productOemManager.selectProductOemItemPrice(productId, getUserInfoVO());
        return RetWrapper.success(listResps);
    }

    /**
     * 保存OEM产品项&定价序列--价格设置
     *
     * @param saveParam OEM产品项&定价序列--价格设置
     * @return RetResult<Void>
     */
    @PrintReqResp
    @PostMapping("/itemPrice/save")
    @ApiOperation(value = "保存OEM产品项&定价序列--价格设置")
    public RetResult<Void> saveProductOemItemPrice(@RequestBody ProductOemItemPriceSaveParam saveParam) {
        productOemManager.saveProductOemItemPrice(saveParam, getUserInfoVO());
        return RetWrapper.success();
    }

    @PrintReqResp
    @PostMapping("/agentRelation/save")
    @ApiOperation(value = "保存产品代理关系")
    public RetResult<Void> saveProductAgentRelation(@RequestBody ProductAgentRelationSaveParam saveParam) {
        UserInfoVO userInfoVO= getUserInfoVO();
        if (Objects.equals(OrgTypeEnum.AGENT_COMPANY.getCode(), userInfoVO.getOrgType())) {
            productOemManager.saveProductAgentRelation(saveParam, getUserInfoVO());
        }
        return RetWrapper.success();
    }
}
