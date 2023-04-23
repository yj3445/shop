package com.itshop.web.controller;

import com.github.pagehelper.PageInfo;
import com.itshop.web.annotation.PrintReqResp;
import com.itshop.web.dao.price.InternetAccessUpdateServiceProductPriceConfigRepository;
import com.itshop.web.dto.PageParam;
import com.itshop.web.dto.RetResult;
import com.itshop.web.dto.RetWrapper;
import com.itshop.web.dto.price.InternetAccessUpdateServiceProductPriceConfig;
import com.itshop.web.dto.request.InternetAccessUpdateServiceOrderQueryParam;
import com.itshop.web.dto.request.InternetAccessUpdateServiceOrderSaveParam;
import com.itshop.web.dto.response.InternetAccessUpdateServiceOrderPriceResp;
import com.itshop.web.dto.response.InternetAccessUpdateServiceOrderResp;
import com.itshop.web.manager.InternetAccessUpdateServiceOrderManager;
import com.itshop.web.service.InternetAccessUpdateServiceOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 互联网接入-升级服务-控制器
 */
@Slf4j
@RestController
@RequestMapping("/internetAccess/updateService")
@Api(value = "InternetAccessUpdateServiceController", description = "互联网接入-升级服务-控制器")
public class InternetAccessUpdateServiceController extends BaseController {

    @Autowired
    InternetAccessUpdateServiceOrderManager updateServiceOrderManager;

    @Autowired
    InternetAccessUpdateServiceOrderService updateServiceOrderService;

    @Autowired
    InternetAccessUpdateServiceProductPriceConfigRepository productPriceConfigRepository;

    /**
     * 得到互联网接入-升级服务-产品-价格
     *
     * @param productId 产品id
     * @return InternetAccessUpdateServiceProductPriceConfig
     */
    @GetMapping("/product/price")
    @ApiOperation(value = "得到互联网接入-升级服务-产品-价格")
    public RetResult<InternetAccessUpdateServiceProductPriceConfig> getInternetAccessProductPrice(@RequestParam("productId") Integer productId) {
        InternetAccessUpdateServiceProductPriceConfig result = productPriceConfigRepository.getInternetAccessProductPrice(productId);
        return RetWrapper.success(result);
    }

    /**
     * 互联网接入-升级服务-订单-计算价格
     *
     * @param productDOrderParam 互联网接入-升级服务-订单-保存-请求参数
     * @return RetResult<InternetAccessUpdateServiceOrderPriceResp>
     */
    @PrintReqResp
    @PostMapping("/order/calcTotalPrice")
    @ApiOperation(value = "互联网接入订单-计算价格")
    public RetResult<InternetAccessUpdateServiceOrderPriceResp> calcTotalPriceAndDiscount(@RequestBody InternetAccessUpdateServiceOrderSaveParam productDOrderParam) {
        InternetAccessUpdateServiceOrderPriceResp result = updateServiceOrderService.calcTotalPriceAndDiscount(productDOrderParam);
        return RetWrapper.success(result);
    }

    /**
     * 保存互联网接入-升级服务-订单信息
     *
     * @param productDOrderParam
     * @return RetResult<Integer>
     */
    @PrintReqResp
    @PostMapping("/order/save")
    @ApiOperation(value = "保存互联网接入-升级服务-订单信息")
    public RetResult<Integer> save(@RequestBody InternetAccessUpdateServiceOrderSaveParam productDOrderParam) {
        productDOrderParam.setCreaterBy(getCurrentUserId());
        int result = updateServiceOrderService.save(productDOrderParam);
        return RetWrapper.success(result);
    }

    /**
     * 得到互联网接入-升级服务-订单详情
     *
     * @param orderId 订单id
     * @return RetResult
     */
    @GetMapping("/order/detail")
    @ApiOperation(value = "得到互联网接入-升级服务-订单详情")
    public RetResult<InternetAccessUpdateServiceOrderResp> getOrderDetail(@RequestParam("orderId") Integer orderId) {
        InternetAccessUpdateServiceOrderResp result = updateServiceOrderManager.getOrderDetail(orderId);
        return RetWrapper.success(result);
    }

    /**
     * 得到互联网接入-升级服务-订单列表
     *
     * @param pageParam 分页参数
     * @return RetResult
     */
    @PostMapping("/order/list")
    @ApiOperation(value = "得到互联网接入-升级服务-订单列表")
    public RetResult<PageInfo<InternetAccessUpdateServiceOrderResp>> selectByQueryParam(@RequestBody PageParam<InternetAccessUpdateServiceOrderQueryParam> pageParam) {
        if (pageParam.getSearchParam() == null) {
            InternetAccessUpdateServiceOrderQueryParam orderQueryParam = new InternetAccessUpdateServiceOrderQueryParam();
            orderQueryParam.setCreaterBy(getCurrentUserId());
            pageParam.setSearchParam(orderQueryParam);
        } else {
            pageParam.getSearchParam().setCreaterBy(getCurrentUserId());
        }
        PageInfo<InternetAccessUpdateServiceOrderResp> result = updateServiceOrderManager.selectByQueryParam(pageParam);
        return RetWrapper.success(result);
    }
}
