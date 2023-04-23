package com.itshop.web.service.devicecontrol;

import com.google.common.collect.Lists;
import com.itshop.web.bo.DeviceControlParam;
import com.itshop.web.bo.IdcFireWallOrderSaveResult;
import com.itshop.web.dao.mysql.TDeviceControlTransDAO;
import com.itshop.web.dao.po.TDeviceControlTrans;
import com.itshop.web.dto.deviceControl.DeviceControlResponseResult;
import com.itshop.web.dto.deviceControl.FireWallAddPortRequest;
import com.itshop.web.dto.deviceControl.FireWallAddPortResponse;
import com.itshop.web.dto.deviceControl.FireWallEnableRequest;
import com.itshop.web.enums.DeviceControlTransCode;
import com.itshop.web.enums.DeviceControlTransStatus;
import com.itshop.web.manager.DeviceControlManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.Objects;

/**
 * IDC-防火墙设备控制服务
 *
 * @author liufenglong
 * @className IdcFireWallDeviceControlService
 * @description IDC-防火墙设备控制服务
 * @date 2023/3/2
 **/
@Service
public class IdcFireWallDeviceControlService {

    @Autowired
    DeviceControlManager deviceControlManager;

    @Autowired
    TDeviceControlTransDAO deviceControlTransDAO;

    @Async
    public void callDeviceControl(IdcFireWallOrderSaveResult saveResult, Integer currentUserId) {
        LinkedList<Integer> afterStatusList = saveResult.getAfterStatusList(saveResult.getTransCurrent());
        for (int i = 0; i < afterStatusList.size(); i++) {
            Integer currentStatus = afterStatusList.get(i);
            Integer nextStatus = saveResult.getTransEnd();
            if (i < afterStatusList.size() - 1) {
                nextStatus = afterStatusList.get(i + 1);
            }
            if (Objects.equals(DeviceControlTransCode.FIRE_WALL_ENABLE.getCode(), currentStatus)) {
                fireWallEnable(saveResult, currentUserId, currentStatus, nextStatus);
            }
            if (Objects.equals(DeviceControlTransCode.FIRE_WALL_DISABLE.getCode(), currentStatus)) {
                fireWallDisable(saveResult, currentUserId, currentStatus, nextStatus);
            }
            if (Objects.equals(DeviceControlTransCode.FIRE_WALL_ENABLE_IN_PORT.getCode(), currentStatus)) {
                fireWallEnableInPort(saveResult, currentUserId, currentStatus, nextStatus);
            }
            if (Objects.equals(DeviceControlTransCode.FIRE_WALL_DISABLE_OUT_PORT.getCode(), currentStatus)) {
                fireWallDisableOutPort(saveResult, currentUserId, currentStatus, nextStatus);
            }
        }
    }

    void fireWallEnable(IdcFireWallOrderSaveResult saveResult, Integer currentUserId,
                        Integer currentStatus, Integer nextStatus) {
        FireWallEnableRequest request = new FireWallEnableRequest();
        request.setBusiness_id(saveResult.getBusiness_id());
        DeviceControlParam<FireWallEnableRequest> param = new DeviceControlParam<>();
        param.setRequest(request);
        param.setCurrentUserId(currentUserId);
        DeviceControlResponseResult<String> responseResult = deviceControlManager.fireWallEnable(param);
        updateTrans(saveResult, currentUserId, currentStatus,nextStatus, responseResult);
    }

    void fireWallDisable(IdcFireWallOrderSaveResult saveResult, Integer currentUserId,
                         Integer currentStatus, Integer nextStatus) {
        FireWallEnableRequest request = new FireWallEnableRequest();
        request.setBusiness_id(saveResult.getBusiness_id());
        DeviceControlParam<FireWallEnableRequest> param = new DeviceControlParam<>();
        param.setRequest(request);
        param.setCurrentUserId(currentUserId);
        DeviceControlResponseResult<String> responseResult = deviceControlManager.fireWallDisable(param);
        updateTrans(saveResult, currentUserId, currentStatus,nextStatus, responseResult);
    }

    void fireWallEnableInPort(IdcFireWallOrderSaveResult saveResult, Integer currentUserId,
                              Integer currentStatus, Integer nextStatus) {
        FireWallAddPortRequest request = new FireWallAddPortRequest();
        request.setBusiness_id(saveResult.getBusiness_id());
        request.setPortlist(Lists.newArrayList());
        saveResult.getNewEnableInPorts().forEach(p ->{
            request.getPortlist().addAll(p.getPort());
            request.setProtocol(p.getProtocol());
        });
        DeviceControlParam<FireWallAddPortRequest> param = new DeviceControlParam<>();
        param.setRequest(request);
        param.setCurrentUserId(currentUserId);
        DeviceControlResponseResult<FireWallAddPortResponse> responseResult = deviceControlManager.fireWallEnableInPort(param);
        updateTrans(saveResult, currentUserId, currentStatus,nextStatus, responseResult);
    }

    void fireWallDisableOutPort(IdcFireWallOrderSaveResult saveResult, Integer currentUserId,
                                Integer currentStatus, Integer nextStatus) {
        FireWallAddPortRequest request = new FireWallAddPortRequest();
        request.setBusiness_id(saveResult.getOrderId().toString());
        request.setPortlist(Lists.newArrayList());
        saveResult.getNewDisableOutPorts().forEach(p ->{
            request.getPortlist().addAll(p.getPort());
            request.setProtocol(p.getProtocol());
        });
        DeviceControlParam<FireWallAddPortRequest> param = new DeviceControlParam<>();
        param.setRequest(request);
        param.setCurrentUserId(currentUserId);
        DeviceControlResponseResult<FireWallAddPortResponse> responseResult = deviceControlManager.fireWallDisableOutPort(param);
        updateTrans(saveResult, currentUserId, currentStatus,nextStatus, responseResult);
    }

    private void updateTrans(IdcFireWallOrderSaveResult saveResult, Integer currentUserId,
                             Integer currentStatus, Integer nextStatus,
                             DeviceControlResponseResult responseResult) {
        TDeviceControlTrans tDeviceControlTrans = new TDeviceControlTrans();
        tDeviceControlTrans.setTransId(saveResult.getTransId());
        tDeviceControlTrans.setModifiedBy(currentUserId);
        tDeviceControlTrans.setModifiedTime(new Date());
        if (DeviceControlResponseResult.SUCCEED.equalsIgnoreCase(responseResult.getCode())) {
            tDeviceControlTrans.setProcess(currentStatus);
            if (Objects.equals(nextStatus, saveResult.getTransEnd())) {
                tDeviceControlTrans.setProcess(nextStatus);
                tDeviceControlTrans.setStatus(DeviceControlTransStatus.SUCCEED.getCode());
            }
        }
        deviceControlTransDAO.updateByPrimaryKeySelective(tDeviceControlTrans);
    }
}
