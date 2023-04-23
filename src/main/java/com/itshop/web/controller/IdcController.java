package com.itshop.web.controller;

import com.github.pagehelper.PageInfo;
import com.itshop.web.annotation.PrintReqResp;
import com.itshop.web.dto.PageParam;
import com.itshop.web.dto.RetResult;
import com.itshop.web.dto.RetWrapper;
import com.itshop.web.dto.request.IdcFireWallOrderSaveParam;
import com.itshop.web.dto.request.TIdcFirewallOrderQueryParam;
import com.itshop.web.dto.response.IdcFireWallOrderResp;
import com.itshop.web.manager.IdcFireWallOrderManager;
import com.itshop.web.service.IdcFireWallOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * idc控制器
 *
 * @description idc控制器接口
 * @author liufenglong
 * @date 2022/5/7
 */
@Slf4j
@RestController
@RequestMapping("/idc")
@Api(value = "IdcController", description = "idc控制器接口")
public class IdcController extends BaseController {
    @Autowired
    IdcFireWallOrderManager idcFirewallOrderManager;

    @Autowired
    IdcFireWallOrderService idcFireWallOrderService;

    /**
     * 保存 idc-防火墙-信息
     *
     * @param productDOrderParam
     * @return RetResult<Integer>
     */
    @PrintReqResp
    @PostMapping("/firewall/order/save")
    @ApiOperation(value = "保存idc-防火墙-信息")
    public RetResult<Integer> save(@RequestBody IdcFireWallOrderSaveParam productDOrderParam) {
        if(productDOrderParam.getProductId() == null) {
            productDOrderParam.setProductId(201);
        }
        productDOrderParam.setCreaterBy(getCurrentUserId());
        int result = idcFireWallOrderService.save(productDOrderParam);
        return RetWrapper.success(result);
    }

    /**
     * 得到idc-防火墙-订单详情
     *
     * @param orderId
     * @return RetResult
     */
    @GetMapping("/firewall/order/detail")
    @ApiOperation(value = "得到idc-防火墙-订单详情")
    public RetResult<IdcFireWallOrderResp> getOrderDetail(@RequestParam("orderId") Integer orderId) {
        IdcFireWallOrderResp result = idcFirewallOrderManager.getOrderDetail(orderId);
        return RetWrapper.success(result);
    }

    /**
     * 得到idc-防火墙-订单列表
     *
     * @param pageParam
     * @return RetResult
     */
    @PostMapping("/firewall/order/list")
    @ApiOperation(value = "得到idc-防火墙-订单列表")
    public RetResult<PageInfo<IdcFireWallOrderResp>> selectByQueryParam(@RequestBody PageParam<TIdcFirewallOrderQueryParam> pageParam) {
        if(pageParam.getSearchParam() == null){
            TIdcFirewallOrderQueryParam orderQueryParam= new TIdcFirewallOrderQueryParam();
            orderQueryParam.setCreaterBy(getCurrentUserId());
            pageParam.setSearchParam(orderQueryParam);
        }
        else {
            pageParam.getSearchParam().setCreaterBy(getCurrentUserId());
        }
        PageInfo<IdcFireWallOrderResp> result = idcFirewallOrderManager.selectByQueryParam(pageParam);
        return RetWrapper.success(result);
    }
}
