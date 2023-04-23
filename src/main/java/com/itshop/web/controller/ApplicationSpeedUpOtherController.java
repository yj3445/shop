package com.itshop.web.controller;

import com.github.pagehelper.PageInfo;
import com.itshop.web.annotation.PrintReqResp;
import com.itshop.web.dao.price.ApplicationSpeedUpProductPriceRepository;
import com.itshop.web.dto.PageParam;
import com.itshop.web.dto.RetResult;
import com.itshop.web.dto.RetWrapper;
import com.itshop.web.dto.price.ApplicationSpeedUpProductPriceConfig;
import com.itshop.web.dto.request.ApplicationSpeedUpOtherOrderQueryParam;
import com.itshop.web.dto.request.ApplicationSpeedUpOtherOrderSaveParam;
import com.itshop.web.dto.response.ApplicationSpeedUpOrderPriceResp;
import com.itshop.web.dto.response.ApplicationSpeedUpOtherOrderResp;
import com.itshop.web.manager.ApplicationSpeedUpOtherOrderManager;
import com.itshop.web.service.ApplicationSpeedUpOtherOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 应用加速-除URL加速外-其他类型-控制器
 *
 * @description 应用加速-除URL加速外-其他类型-控制器
 * @author liufenglong
 * @date 2022/6/11
 */
@Slf4j
@RestController
@RequestMapping("/applicationSpeedUp")
@Api(value = "ApplicationSpeedUpOtherController", description = "应用加速-除URL加速外-其他类型-控制器")
public class ApplicationSpeedUpOtherController extends BaseController {

    @Autowired
    ApplicationSpeedUpOtherOrderManager applicationSpeedUpOtherOrderManager;

    @Autowired
    ApplicationSpeedUpOtherOrderService applicationSpeedUpOtherOrderService;

    @Autowired
    ApplicationSpeedUpProductPriceRepository applicationSpeedUpProductPriceRepository;

    /**
     * 得到应用加速-产品-价格
     *
     * @param productId 产品id
     * @return ApplicationSpeedUpProductPriceConfig
     */
    @GetMapping("/other/product/price")
    @ApiOperation(value = "得到应用加速-产品-价格")
    public RetResult<ApplicationSpeedUpProductPriceConfig> getInternetAccessProductPrice(@RequestParam("productId") Integer productId) {
        ApplicationSpeedUpProductPriceConfig result = applicationSpeedUpProductPriceRepository.getApplicationSpeedUpProductPrice(productId);
        return RetWrapper.success(result);
    }

    /**
     * 应用加速订单-计算价格
     *
     * @param productDOrderParam 应用加速-订单-保存-请求参数
     * @return RetResult<ApplicationSpeedUpOrderPriceResp>
     */
    @PrintReqResp
    @PostMapping("/other/order/calcTotalPrice")
    @ApiOperation(value = "应用加速订单-计算价格")
    public RetResult<ApplicationSpeedUpOrderPriceResp> calcTotalPriceAndDiscount(@RequestBody ApplicationSpeedUpOtherOrderSaveParam productDOrderParam) {
        ApplicationSpeedUpOrderPriceResp result = applicationSpeedUpOtherOrderService.calcTotalPriceAndDiscount(productDOrderParam);
        return RetWrapper.success(result);
    }

    /**
     * 保存 应用加速-信息
     *
     * @param productDOrderParam
     * @return RetResult<Integer>
     */
    @PrintReqResp
    @PostMapping("/other/order/save")
    @ApiOperation(value = "保存应用加速-信息")
    public RetResult<Integer> save(@RequestBody ApplicationSpeedUpOtherOrderSaveParam productDOrderParam) {
        productDOrderParam.setCreaterBy(getCurrentUserId());
        int result = applicationSpeedUpOtherOrderService.save(productDOrderParam);
        return RetWrapper.success(result);
    }

    /**
     * 得到应用加速-信息订单详情
     *
     * @param orderId
     * @return RetResult
     */
    @GetMapping("/other/order/detail")
    @ApiOperation(value = "得到应用加速-信息订单详情")
    public RetResult<ApplicationSpeedUpOtherOrderResp> getOrderDetail(@RequestParam("orderId") Integer orderId) {
        ApplicationSpeedUpOtherOrderResp result = applicationSpeedUpOtherOrderManager.getOrderDetail(orderId);
        return RetWrapper.success(result);
    }

    /**
     * 得到应用加速-信息订单列表
     *
     * @param pageParam
     * @return RetResult
     */
    @PostMapping("/other/order/list")
    @ApiOperation(value = "得到应用加速-信息订单列表")
    public RetResult<PageInfo<ApplicationSpeedUpOtherOrderResp>> selectByQueryParam(@RequestBody PageParam<ApplicationSpeedUpOtherOrderQueryParam> pageParam) {
        if (pageParam.getSearchParam() == null) {
            ApplicationSpeedUpOtherOrderQueryParam orderQueryParam = new ApplicationSpeedUpOtherOrderQueryParam();
            orderQueryParam.setCreaterBy(getCurrentUserId());
            pageParam.setSearchParam(orderQueryParam);
        } else {
            pageParam.getSearchParam().setCreaterBy(getCurrentUserId());
        }
        PageInfo<ApplicationSpeedUpOtherOrderResp> result = applicationSpeedUpOtherOrderManager.selectByQueryParam(pageParam);
        return RetWrapper.success(result);
    }
}
