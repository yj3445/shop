package com.itshop.web.controller;

import com.github.pagehelper.PageInfo;
import com.itshop.web.annotation.PrintReqResp;
import com.itshop.web.dao.price.InternetAccessProductPriceRepository;
import com.itshop.web.dto.price.InternetAccessProductPriceConfig;
import com.itshop.web.dto.PageParam;
import com.itshop.web.dto.RetResult;
import com.itshop.web.dto.RetWrapper;
import com.itshop.web.dto.request.ApplicationGroupParam;
import com.itshop.web.dto.request.InternetAccessOrderQueryParam;
import com.itshop.web.dto.request.InternetAccessOrderSaveParam;
import com.itshop.web.dto.response.*;
import com.itshop.web.manager.InternetAccessApplicationGroupManager;
import com.itshop.web.manager.InternetAccessOrderManager;
import com.itshop.web.manager.InternetAccessExportConfigManager;
import com.itshop.web.service.InternetAccessCustomOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 互联网接入控制器
 *
 * @description 互联网接入控制器接口
 * @author liufenglong
 * @date 2022/5/7
 */
@Slf4j
@RestController
@RequestMapping("/internetAccess")
@Api(value = "InternetAccessController", description = "互联网接入控制器")
public class InternetAccessController extends BaseController {

    @Autowired
    InternetAccessApplicationGroupManager internetAccessApplicationGroupManager;

    @Autowired
    InternetAccessOrderManager internetAccessOrderManager;

    @Autowired
    InternetAccessCustomOrderService internetAccessCustomOrderService;

    @Autowired
    InternetAccessExportConfigManager internetAccessExportConfigManager;

    @Autowired
    InternetAccessProductPriceRepository internetAccessProductPriceRepository;

    /**
     * 得到出口配置列表
     *
     * @param pageParam 分页参数
     * @return PageInfo
     */
    @PostMapping("/export/config/list")
    @ApiOperation(value = "得到出口配置列表")
    public RetResult<PageInfo<InternetAccessExportConfigResp>> selectUrlSpeedUpConfigList(@RequestBody PageParam pageParam) {
        PageInfo<InternetAccessExportConfigResp> result = internetAccessExportConfigManager.selectByQueryParam(pageParam);
        return RetWrapper.success(result);
    }

    /**
     * 应用实例组(产品D) 应用线路性能指标列表
     *
     * @param pageParam
     * @return RetResult<PageInfo<ApplicationGroupCustomAndLineIndicators>>
     */
    @PostMapping("/applicationAndLineIndicators")
    @ApiOperation(value = "应用实例组(产品D) 应用线路性能指标列表")
    public RetResult<PageInfo<ApplicationGroupCustomAndLineIndicators>> selectTApplicationGroupCustomAndLineIndicators(@RequestBody PageParam pageParam) {
        PageInfo<ApplicationGroupCustomAndLineIndicators> pageInfo = internetAccessApplicationGroupManager.selectTApplicationGroupCustomAndLineIndicators(pageParam);
        return RetWrapper.success(pageInfo);
    }

    /**
     * 应用实例组(产品A、产品B、产品C) 应用列表
     *
     * @param pageParam
     * @return RetResult<PageInfo<ApplicationGroupResp>>
     */
    @PostMapping("/applications")
    @ApiOperation(value = "应用实例组(产品A、产品B、产品C) 应用列表")
    public RetResult<PageInfo<ApplicationGroupResp>> selectTApplicationGroups(@RequestBody PageParam<ApplicationGroupParam> pageParam) {
        PageInfo<ApplicationGroupResp> pageInfo = internetAccessApplicationGroupManager.selectTApplicationGroups(pageParam);
        return RetWrapper.success(pageInfo);
    }

    /**
     * 得到互联网接入-产品-价格
     *
     * @param productId 产品id
     * @return InternetAccessProductPrice
     */
    @GetMapping("/custom/product/price")
    @ApiOperation(value = "得到互联网接入-产品-价格")
    public RetResult<InternetAccessProductPriceConfig> getInternetAccessProductPrice(@RequestParam("productId") Integer productId) {
        InternetAccessProductPriceConfig result = internetAccessProductPriceRepository.getInternetAccessProductPrice(productId,getUserInfoVO(),true);
        return RetWrapper.success(result);
    }

    /**
     * 互联网接入订单-计算价格
     * @param productDOrderParam 互联网接入-自定义-订单-保存-请求参数
     * @return RetResult<InternetAccessOrderPriceResp>
     */
    @PrintReqResp
    @PostMapping("/custom/order/calcTotalPrice")
    @ApiOperation(value = "互联网接入订单-计算价格")
    public RetResult<InternetAccessOrderPriceResp> calcTotalPriceAndDiscount(@RequestBody InternetAccessOrderSaveParam productDOrderParam) {
        InternetAccessOrderPriceResp result = internetAccessCustomOrderService.calcTotalPriceAndDiscount(productDOrderParam,getUserInfoVO(),true);
        return RetWrapper.success(result);
    }

    /**
     * 保存互联网接入-自定义-信息
     *
     * @param productDOrderParam
     * @return RetResult<Integer>
     */
    @PrintReqResp
    @PostMapping("/custom/order/save")
    @ApiOperation(value = "保存互联网接入-自定义-信息")
    public RetResult<Integer> save(@RequestBody InternetAccessOrderSaveParam productDOrderParam) {
        if (productDOrderParam.getProductId() == null) {
            productDOrderParam.setProductId(5);
        }
        int result = internetAccessCustomOrderService.save(productDOrderParam,getUserInfoVO());
        return RetWrapper.success(result);
    }

    /**
     * 得到互联网接入-自定义-订单详情
     *
     * @param orderId
     * @return RetResult
     */
    @GetMapping("/custom/order/detail")
    @ApiOperation(value = "得到互联网接入-自定义-订单详情")
    public RetResult<InternetAccessOrderResp> getOrderDetail(@RequestParam("orderId") Integer orderId) {
        InternetAccessOrderResp result = internetAccessOrderManager.getOrderDetail(orderId,getAgentOrgId());
        return RetWrapper.success(result);
    }

    /**
     * 得到互联网接入-自定义-订单列表
     *
     * @param pageParam
     * @return RetResult
     */
    @PostMapping("/custom/order/list")
    @ApiOperation(value = "得到互联网接入-自定义-订单列表")
    public RetResult<PageInfo<InternetAccessOrderResp>> selectByQueryParam(@RequestBody PageParam<InternetAccessOrderQueryParam> pageParam) {
        if(pageParam.getSearchParam() == null){
            InternetAccessOrderQueryParam orderQueryParam= new InternetAccessOrderQueryParam();
            orderQueryParam.setCreaterBy(getCurrentUserId());
            pageParam.setSearchParam(orderQueryParam);
        }
        else {
            pageParam.getSearchParam().setCreaterBy(getCurrentUserId());
        }
        PageInfo<InternetAccessOrderResp> result = internetAccessOrderManager.selectByQueryParam(pageParam);
        return RetWrapper.success(result);
    }
}
