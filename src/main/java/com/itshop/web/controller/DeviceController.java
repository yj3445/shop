package com.itshop.web.controller;

import com.itshop.web.bo.DeviceControlParam;
import com.itshop.web.dto.deviceControl.*;
import com.itshop.web.manager.DeviceControlManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/device")
@Api(value = "DeviceController", description = "设备控制器")
public class DeviceController extends BaseController {
    @Autowired
    DeviceControlManager deviceControlManager;


    @PostMapping("/broadBandQuery")
    @ApiOperation(value = "查询上网业务的带宽")
    public DeviceControlResponseResult<BandWidthQueryResponse> broadBandQuery(@RequestBody BandWidthQueryRequest request) {
        DeviceControlParam<BandWidthQueryRequest> param = new DeviceControlParam<>();
        param.setRequest(request);
        param.setCurrentUserId(getCurrentUserId());
        return deviceControlManager.broadBandQuery(param);
    }

    @PostMapping("/broadBandUpdate")
    @ApiOperation(value = "修改上网业务的带宽")
    public DeviceControlResponseResult<String> broadBandUpdate(@RequestBody BandWidthUpdateRequest request) {
        DeviceControlParam<BandWidthUpdateRequest> param = new DeviceControlParam<>();
        param.setRequest(request);
        param.setCurrentUserId(getCurrentUserId());
        return deviceControlManager.broadBandUpdate(param);
    }

    @PostMapping("/broadBandIspChange")
    @ApiOperation(value = "变更上网业务的出口（运营商）")
    public DeviceControlResponseResult<String> broadBandIspChange(@RequestBody BandWidthIspChangeRequest request) {
        DeviceControlParam<BandWidthIspChangeRequest> param = new DeviceControlParam<>();
        param.setRequest(request);
        param.setCurrentUserId(getCurrentUserId());
        return deviceControlManager.broadBandIspChange(param);
    }

    @PostMapping("/fireWallEnable")
    @ApiOperation(value = "使防火墙生效")
    public DeviceControlResponseResult<String> fireWallEnable(@RequestBody FireWallEnableRequest request) {
        DeviceControlParam<FireWallEnableRequest> param = new DeviceControlParam<>();
        param.setRequest(request);
        param.setCurrentUserId(getCurrentUserId());
        return deviceControlManager.fireWallEnable(param);
    }

    @PostMapping("/fireWallDisable")
    @ApiOperation(value = "使防火墙关闭")
    public DeviceControlResponseResult<String> fireWallDisable(@RequestBody FireWallEnableRequest request) {
        DeviceControlParam<FireWallEnableRequest> param = new DeviceControlParam<>();
        param.setRequest(request);
        param.setCurrentUserId(getCurrentUserId());
        return deviceControlManager.fireWallDisable(param);
    }

    @PostMapping("/fireWallEnableInPort")
    @ApiOperation(value = "增加防火墙开放的下行服务端口（防火墙默认关闭所有下行服务端口）")
    public DeviceControlResponseResult<FireWallAddPortResponse> fireWallEnableInPort(@RequestBody FireWallAddPortRequest request) {
        DeviceControlParam<FireWallAddPortRequest> param = new DeviceControlParam<>();
        param.setRequest(request);
        param.setCurrentUserId(getCurrentUserId());
        return deviceControlManager.fireWallEnableInPort(param);
    }

    @PostMapping("/fireWallDisableOutPort")
    @ApiOperation(value = "增加防火墙屏蔽的上行访问端口（内部访问外部，默认均开放）")
    public DeviceControlResponseResult<FireWallAddPortResponse> fireWallDisableOutPort(@RequestBody FireWallAddPortRequest request) {
        DeviceControlParam<FireWallAddPortRequest> param = new DeviceControlParam<>();
        param.setRequest(request);
        param.setCurrentUserId(getCurrentUserId());
        return deviceControlManager.fireWallDisableOutPort(param);
    }

    @PostMapping("/urlSpeedUpQuery")
    @ApiOperation(value = "查询已设置加速的URL列表")
    public DeviceControlResponseResult<UrlSpeedUpQueryResponse> urlSpeedUpQuery(@RequestBody UrlSpeedUpQueryRequest request) {
        DeviceControlParam<UrlSpeedUpQueryRequest> param = new DeviceControlParam<>();
        param.setRequest(request);
        param.setCurrentUserId(getCurrentUserId());
        return deviceControlManager.urlSpeedUpQuery(param);
    }

    @PostMapping("/urlSpeedUpAdd")
    @ApiOperation(value = "向URL加速列表中添加URL")
    public DeviceControlResponseResult<UrlSpeedUpQueryResponse> urlSpeedUpAdd(@RequestBody UrlSpeedUpAddRequest request) {
        DeviceControlParam<UrlSpeedUpAddRequest> param = new DeviceControlParam<>();
        param.setRequest(request);
        param.setCurrentUserId(getCurrentUserId());
        return deviceControlManager.urlSpeedUpAdd(param);
    }
}
