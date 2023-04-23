package com.itshop.web.service.devicecontrol;

import com.itshop.web.bo.ApplicationSpeedUpOrderSaveResult;
import com.itshop.web.bo.DeviceControlParam;
import com.itshop.web.dao.mysql.TDeviceControlTransDAO;
import com.itshop.web.dao.po.TDeviceControlTrans;
import com.itshop.web.dto.deviceControl.DeviceControlResponseResult;
import com.itshop.web.dto.deviceControl.UrlSpeedUpAddRequest;
import com.itshop.web.dto.deviceControl.UrlSpeedUpQueryResponse;
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
 * 应用加速-设备控制服务
 * @author liufenglong
 * @className ApplicationSpeedUpDeviceControlService
 * @description 应用加速-设备控制服务
 * @date 2023/3/2
 **/
@Service
public class ApplicationSpeedUpDeviceControlService {

    @Autowired
    DeviceControlManager deviceControlManager;

    @Autowired
    TDeviceControlTransDAO deviceControlTransDAO;


    @Async
    public void callDeviceControl(ApplicationSpeedUpOrderSaveResult saveResult, Integer currentUserId) {
        LinkedList<Integer> afterStatusList = saveResult.getAfterStatusList(saveResult.getTransCurrent());
        for (int i = 0; i < afterStatusList.size(); i++) {
            Integer currentStatus = afterStatusList.get(i);
            int nextStatus = saveResult.getTransEnd();
            if (i < afterStatusList.size() - 1) {
                nextStatus = afterStatusList.get(i + 1);
            }
            if (Objects.equals(DeviceControlTransCode.URL_SPEED_UP_ADD.getCode(), currentStatus)) {
                urlSpeedUpAdd(saveResult, currentUserId,currentStatus, nextStatus);
            }
            if (Objects.equals(DeviceControlTransCode.URL_SPEED_UP_REMOVE.getCode(), currentStatus)) {
                urlSpeedUpRemove(saveResult, currentUserId,currentStatus, nextStatus);
            }
        }
    }

    void urlSpeedUpAdd(ApplicationSpeedUpOrderSaveResult saveResult, Integer currentUserId,
                       Integer currentStatus, Integer nextStatus) {
        UrlSpeedUpAddRequest request = new UrlSpeedUpAddRequest();
        request.setBusiness_id(saveResult.getBusiness_id());
        request.setTotal(saveResult.getNewUrls().size());
        request.setUrllist(saveResult.getNewUrls());
        DeviceControlParam<UrlSpeedUpAddRequest> param = new DeviceControlParam<>();
        param.setRequest(request);
        param.setCurrentUserId(currentUserId);
        DeviceControlResponseResult<UrlSpeedUpQueryResponse> responseResult = deviceControlManager.urlSpeedUpAdd(param);
        updateTrans(saveResult, currentUserId, currentStatus, nextStatus,responseResult);
    }

    void urlSpeedUpRemove(ApplicationSpeedUpOrderSaveResult saveResult, Integer currentUserId,
                          Integer currentStatus, Integer nextStatus) {
        DeviceControlResponseResult responseResult = new DeviceControlResponseResult<>();
        responseResult.setCode(DeviceControlResponseResult.SUCCEED);
        updateTrans(saveResult, currentUserId, currentStatus, nextStatus, responseResult);
    }

    private void updateTrans(ApplicationSpeedUpOrderSaveResult saveResult, Integer currentUserId,
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
