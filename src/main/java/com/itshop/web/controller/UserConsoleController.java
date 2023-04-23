package com.itshop.web.controller;

/**
 * @author liufenglong
 * @date 2022/6/13
 */

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.itshop.web.annotation.PrintReqResp;
import com.itshop.web.bo.UserInfoVO;
import com.itshop.web.dto.PageParam;
import com.itshop.web.dto.RetResult;
import com.itshop.web.dto.RetWrapper;
import com.itshop.web.dto.deviceControl.DeviceControlResponseResult;
import com.itshop.web.dto.deviceControl.TrendStackResponse;
import com.itshop.web.dto.request.ApplicationSpeedUpOrderQueryParam;
import com.itshop.web.dto.request.InternetAccessOrderQueryParam;
import com.itshop.web.dto.request.OrderAuditParam;
import com.itshop.web.dto.request.OrderMonitorQueryParam;
import com.itshop.web.dto.response.ApplicationSpeedUpUnionOrderResp;
import com.itshop.web.dto.response.InternetAccessOrderResp;
import com.itshop.web.dto.response.InternetAccessUnionOrderResp;
import com.itshop.web.dto.response.OrderHistoryResp;
import com.itshop.web.enums.OrderStatusEnum;
import com.itshop.web.enums.OrgTypeEnum;
import com.itshop.web.enums.RetCode;
import com.itshop.web.manager.InternetAccessOrderManager;
import com.itshop.web.manager.UserConsoleManager;
import com.itshop.web.service.InternetAccessOrderService;
import com.itshop.web.util.CompareObjectPropertyUtil;
import com.itshop.web.util.ModifiedPropertyInfo;
import com.itshop.web.util.OrderTypeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 用户-控制台-控制器
 *
 * @description 用户-控制台-控制器
 * @author liufenglong
 * @date 2022/6/11
 */
@Slf4j
@RestController
@RequestMapping("/UserConsole")
@Api(value = "UserConsoleController", description = "用户-控制台-控制器")
public class UserConsoleController extends BaseController {

    @Autowired
    UserConsoleManager userConsoleManager;


    @Autowired
    InternetAccessOrderManager internetAccessOrderManager;

    @Autowired
    InternetAccessOrderService internetAccessOrderService;


    /**
     * 查询互联网接入(升级服务)订单列表
     *
     * @param pageParam
     * @return RetResult
     */
    @PostMapping("/idc/order/list")
    @ApiOperation(value = "查询互联网接入(升级服务)订单列表")
    public RetResult<PageInfo<InternetAccessUnionOrderResp>> selectInternetAccessUnionOrder(@RequestBody PageParam<InternetAccessOrderQueryParam> pageParam) {
        UserInfoVO userInfoVO = getUserInfoVO();
        if (pageParam.getSearchParam() == null) {
            InternetAccessOrderQueryParam orderQueryParam = new InternetAccessOrderQueryParam();
            orderQueryParam.setCreaterBy(userInfoVO.getAppUserInfoId());
            pageParam.setSearchParam(orderQueryParam);
        } else {
            pageParam.getSearchParam().setCreaterBy(userInfoVO.getAppUserInfoId());
        }
        pageParam.getSearchParam().setOrgId(getAgentOrgId());
        pageParam.getSearchParam().setOrgFullPath(userInfoVO.getOrgFullPath());
        PageInfo<InternetAccessUnionOrderResp> result = userConsoleManager.selectInternetAccessUnionOrder(pageParam);
        if(result!=null && CollectionUtils.isNotEmpty(result.getList())) {
            result.getList().forEach(internetAccessUnionOrderResp -> {
                internetAccessUnionOrderResp.setCanAuditPrice(
                        //“待审核订单”
                        Objects.equals(OrderStatusEnum.NOT_AUDIT.getValue(), internetAccessUnionOrderResp.getStatus())
                                &&
                                (
                                        Objects.equals(userInfoVO.getOrgId(), internetAccessUnionOrderResp.getParentOrgId())
                                                ||
                                                (
                                                        //代理商||供应商下单,可以自己审批
                                                        Objects.equals(userInfoVO.getOrgId(), internetAccessUnionOrderResp.getOrgId())
                                                                && !Objects.equals(userInfoVO.getOrgType(), OrgTypeEnum.CUSTOMER_COMPANY.getCode())
                                                )
                                )
                );
            });
        }
        return RetWrapper.success(result);
    }

    /**
     * 查询应用加速(URL加速及其他类型加速)订单列表
     *
     * @param pageParam
     * @return RetResult
     */
    @PostMapping("/acc/order/list")
    @ApiOperation(value = "查询应用加速(URL加速及其他类型加速)订单列表")
    public RetResult<PageInfo<ApplicationSpeedUpUnionOrderResp>> selectApplicationSpeedUpUnionOrder(@RequestBody PageParam<ApplicationSpeedUpOrderQueryParam> pageParam) {
        if (pageParam.getSearchParam() == null) {
            ApplicationSpeedUpOrderQueryParam orderQueryParam = new ApplicationSpeedUpOrderQueryParam();
            orderQueryParam.setCreaterBy(getCurrentUserId());
            pageParam.setSearchParam(orderQueryParam);
        } else {
            pageParam.getSearchParam().setCreaterBy(getCurrentUserId());
        }
        pageParam.getSearchParam().setOrgId(getAgentOrgId());
        PageInfo<ApplicationSpeedUpUnionOrderResp> result = userConsoleManager.selectApplicationSpeedUpUnionOrder(pageParam);
        return RetWrapper.success(result);
    }

    @GetMapping("/order/history")
    @ApiOperation(value = "得到订单历史")
    public RetResult<OrderHistoryResp> selectOrderHistory(@RequestParam("orderId") Integer orderId,
                                                          @RequestParam("productId") Integer productId) {
        if (OrderTypeUtil.isInternetAccessOrder(productId)) {
            OrderHistoryResp orderHistoryResp = internetAccessOrderManager.selectOrderHistory(orderId, getAgentOrgId());
            return RetWrapper.success(orderHistoryResp);
        }
        return RetWrapper.success();
    }


    @GetMapping("/order/history/detail")
    @ApiOperation(value = "得到订单历史详情")
    public  Map<String,Object> selectOrderHistoryDetail(@RequestParam("orderId") Integer orderId,
                                                         @RequestParam("changeId") Integer changeId,
                                                         @RequestParam("productId") Integer productId) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("code", RetCode.SUCCESS.getCode());
        result.put("source", new Object());
        if (OrderTypeUtil.isInternetAccessOrder(productId)) {
            Map<String, InternetAccessOrderResp> respMap = internetAccessOrderManager.getOrderHistoryDetail(changeId, orderId, getAgentOrgId());
            if (changeId > 0) {
                InternetAccessOrderResp changeOrder = respMap.get("change");
                InternetAccessOrderResp sourceOrder = respMap.get("source");
                result.put("data", changeOrder);
                List<ModifiedPropertyInfo> modifiedPropertyInfos = CompareObjectPropertyUtil.getDifferentProperty(sourceOrder, changeOrder, "createrBy", "createTime", "modifiedBy", "modifiedTime");
                if (CollectionUtils.isNotEmpty(modifiedPropertyInfos)) {
                    Map<String, Object> oldModel = Maps.newHashMap();
                    modifiedPropertyInfos.forEach(p -> {
                        oldModel.put(p.getPropertyName(), p.getOldValue());
                    });
                    result.put("source", oldModel);
                }
            } else {
                result.put("data", respMap.get("source"));
            }
        }
        return result;
    }

    @PostMapping("/order/monitor")
    @ApiOperation(value = "订单流量监控")
    public RetResult<TrendStackResponse> trendStacking(@RequestBody OrderMonitorQueryParam queryParam) {
        DeviceControlResponseResult<TrendStackResponse> result = internetAccessOrderManager.trendStacking(queryParam);
        if (DeviceControlResponseResult.SUCCEED.equalsIgnoreCase(result.getCode())) {
            TrendStackResponse stackResponse = result.getData();
            BigDecimal bit2Mb= BigDecimal.valueOf(1024*1024);
            if(CollectionUtils.isNotEmpty(stackResponse.getDown())){
                stackResponse.getDown().forEach(trendStackData -> {
                    List<Long> newData = Lists.newArrayList();
                    trendStackData.getData().forEach(d ->{
                        newData.add(BigDecimal.valueOf(d).divide(bit2Mb,2, RoundingMode.HALF_UP).longValue());
                    });
                    trendStackData.setData(newData);
                });
            }
//            if(CollectionUtils.isNotEmpty(stackResponse.getFlow())){
//                stackResponse.getFlow().forEach(trendStackData -> {
//                    List<Long> newData = Lists.newArrayList();
//                    trendStackData.getData().forEach(d ->{
//                        newData.add(BigDecimal.valueOf(d).divide(bit2Mb,2, RoundingMode.HALF_UP).longValue());
//                    });
//                    trendStackData.setData(newData);
//                });
//            }
            if(CollectionUtils.isNotEmpty(stackResponse.getUp())){
                stackResponse.getUp().forEach(trendStackData -> {
                    List<Long> newData = Lists.newArrayList();
                    trendStackData.getData().forEach(d ->{
                        newData.add(BigDecimal.valueOf(d).divide(bit2Mb,2, RoundingMode.HALF_UP).longValue());
                    });
                    trendStackData.setData(newData);
                });
            }
            return RetWrapper.success(stackResponse);
        }
        return RetWrapper.failure("得到订单流量监控失败！");
    }

    @PrintReqResp
    @PostMapping("/order/audit")
    @ApiOperation(value = "订单审核")
    public RetResult<Integer> OrderAudit(@RequestBody OrderAuditParam orderAuditParam) {
        if (orderAuditParam == null
                || orderAuditParam.getOrderId() == null
                || orderAuditParam.getProductId() == null) {
            return RetWrapper.failure("参数错误(orderId或productId为空)!");
        }
        if (OrderTypeUtil.isInternetAccessOrder(orderAuditParam.getProductId())) {
            return RetWrapper.success(internetAccessOrderService.orderAudit(orderAuditParam, getUserInfoVO()));
        }
        return RetWrapper.success();
    }
}


