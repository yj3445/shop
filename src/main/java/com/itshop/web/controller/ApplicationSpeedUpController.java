package com.itshop.web.controller;


import com.github.pagehelper.PageInfo;
import com.itshop.web.annotation.PrintReqResp;
import com.itshop.web.dao.price.ApplicationSpeedUpProductPriceRepository;
import com.itshop.web.dto.*;
import com.itshop.web.dto.price.ApplicationSpeedUpProductPriceConfig;
import com.itshop.web.dto.request.*;
import com.itshop.web.dto.response.ApplicationSpeedUpOrderPriceResp;
import com.itshop.web.dto.response.ApplicationSpeedUpOrderResp;
import com.itshop.web.dto.response.UrlSpeedUpConfigResp;
import com.itshop.web.manager.ApplicationSpeedUpOrderManager;
import com.itshop.web.manager.UrlSpeedUpConfigManager;
import com.itshop.web.service.ApplicationSpeedUpOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 应用加速-URL加速-控制器
 *
 * @description 应用加速-URL加速-控制器
 * @author liufenglong
 * @date 2022/5/7
 */
@Slf4j
@RestController
@RequestMapping("/applicationSpeedUp")
@Api(value = "ApplicationSpeedUpController", description = "应用加速-URL加速-控制器")
public class ApplicationSpeedUpController extends BaseController {

    @Autowired
    ApplicationSpeedUpOrderService applicationSpeedUpOrderService;

    @Autowired
    ApplicationSpeedUpOrderManager applicationSpeedUpOrderManager;

    @Autowired
    UrlSpeedUpConfigManager urlSpeedUpConfigManager;

    @Autowired
    ApplicationSpeedUpProductPriceRepository applicationSpeedUpProductPriceRepository;

    /**
     * 得到应用加速-产品-价格
     *
     * @param productId 产品id
     * @return ApplicationSpeedUpProductPriceConfig
     */
    @GetMapping("/url/product/price")
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
    @PostMapping("/url/order/calcTotalPrice")
    @ApiOperation(value = "应用加速订单-计算价格")
    public RetResult<ApplicationSpeedUpOrderPriceResp> calcTotalPriceAndDiscount(@RequestBody ApplicationSpeedUpOrderSaveParam productDOrderParam) {
        ApplicationSpeedUpOrderPriceResp result = applicationSpeedUpOrderService.calcTotalPriceAndDiscount(productDOrderParam);
        return RetWrapper.success(result);
    }

    /**
     * 保存 应用加速-信息
     *
     * @param productDOrderParam
     * @return RetResult<Integer>
     */
    @PrintReqResp
    @PostMapping("/url/order/save")
    @ApiOperation(value = "保存应用加速-信息")
    public RetResult<Integer> save(@RequestBody ApplicationSpeedUpOrderSaveParam productDOrderParam) {
        if(productDOrderParam.getProductId() == null) {
            productDOrderParam.setProductId(101);
        }
        productDOrderParam.setCreaterBy(getCurrentUserId());
        int result = applicationSpeedUpOrderService.save(productDOrderParam);
        return RetWrapper.success(result);
    }

    /**
     * 得到应用加速-信息订单详情
     *
     * @param orderId
     * @return RetResult
     */
    @GetMapping("/url/order/detail")
    @ApiOperation(value = "得到应用加速-信息订单详情")
    public RetResult<ApplicationSpeedUpOrderResp> getOrderDetail(@RequestParam("orderId") Integer orderId) {
        ApplicationSpeedUpOrderResp result = applicationSpeedUpOrderManager.getOrderDetail(orderId);
        return RetWrapper.success(result);
    }

    /**
     * 得到应用加速-信息订单列表
     *
     * @param pageParam
     * @return RetResult
     */
    @PostMapping("/url/order/list")
    @ApiOperation(value = "得到应用加速-信息订单列表")
    public RetResult<PageInfo<ApplicationSpeedUpOrderResp>> selectByQueryParam(@RequestBody PageParam<ApplicationSpeedUpOrderQueryParam> pageParam) {
        if(pageParam.getSearchParam() == null){
            ApplicationSpeedUpOrderQueryParam orderQueryParam= new ApplicationSpeedUpOrderQueryParam();
            orderQueryParam.setCreaterBy(getCurrentUserId());
            pageParam.setSearchParam(orderQueryParam);
        }
        else {
            pageParam.getSearchParam().setCreaterBy(getCurrentUserId());
        }
        PageInfo<ApplicationSpeedUpOrderResp> result = applicationSpeedUpOrderManager.selectByQueryParam(pageParam);
        return RetWrapper.success(result);
    }

    /**
     * 得到URL加速配置列表
     *
     * @param pageParam 分页参数
     * @return PageInfo
     */
    @PostMapping("/url/config/list")
    @ApiOperation(value = "得到URL加速配置列表")
    public RetResult<PageInfo<UrlSpeedUpConfigResp>> selectUrlSpeedUpConfigList(@RequestBody PageParam<UrlSpeedUpConfigQueryParam> pageParam) {
        if(pageParam.getSearchParam() == null) {
            UrlSpeedUpConfigQueryParam queryParam = new UrlSpeedUpConfigQueryParam();
            //101	ACC_A00001	应用加速A
            //102	ACC_A00002	临时URL
            queryParam.setProductId(101);
            pageParam.setSearchParam(queryParam);
        }
        PageInfo<UrlSpeedUpConfigResp> result = urlSpeedUpConfigManager.selectByQueryParam(pageParam);
        return RetWrapper.success(result);
    }
}
