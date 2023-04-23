package com.itshop.web.service.devicecontrol;

import com.itshop.web.bo.DeviceControlParam;
import com.itshop.web.bo.InternetAccessOrderAuditResult;
import com.itshop.web.dao.mysql.TDeviceControlTransDAO;
import com.itshop.web.dao.po.TDeviceControlTrans;
import com.itshop.web.dto.deviceControl.BandWidthIspChangeRequest;
import com.itshop.web.dto.deviceControl.BandWidthUpdateRequest;
import com.itshop.web.dto.deviceControl.DeviceControlResponseResult;
import com.itshop.web.enums.DeviceControlIspId;
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
 * @author liufenglong
 * @className InternetAccessDeviceControlService
 * @description 互联网接入设备控制服务
 * @date 2023/3/2
 **/
@Service
public class InternetAccessDeviceControlService {

    @Autowired
    DeviceControlManager deviceControlManager;

    @Autowired
    TDeviceControlTransDAO deviceControlTransDAO;

    @Async
    public void callDeviceControl(InternetAccessOrderAuditResult saveResult, Integer currentUserId) {
        LinkedList<Integer> afterStatusList = saveResult.getAfterStatusList(saveResult.getTransCurrent());
        for (int i = 0; i < afterStatusList.size(); i++) {
            Integer currentStatus = afterStatusList.get(i);
            int nextStatus = saveResult.getTransEnd();
            if (i < afterStatusList.size() - 1) {
                nextStatus = afterStatusList.get(i + 1);
            }
            if (Objects.equals(DeviceControlTransCode.BROAD_BAND_UPDATE.getCode(), currentStatus)) {
                broadBandUpdate(saveResult, currentUserId, currentStatus, nextStatus);
            }
            if (Objects.equals(DeviceControlTransCode.BROAD_BAND_ISP_CHANGE.getCode(), currentStatus)) {
                broadBandIspChange(saveResult, currentUserId, currentStatus, nextStatus);
            }
        }
    }

    void broadBandUpdate(InternetAccessOrderAuditResult saveResult, Integer currentUserId,
                         Integer currentStatus, Integer nextStatus) {
        BandWidthUpdateRequest request = new BandWidthUpdateRequest();
        request.setBusiness_id(saveResult.getBusiness_id());
        request.setBandwidth(saveResult.getBroadBand());
        DeviceControlParam<BandWidthUpdateRequest> param = new DeviceControlParam<>();
        param.setRequest(request);
        param.setCurrentUserId(currentUserId);
        DeviceControlResponseResult<String> responseResult = deviceControlManager.broadBandUpdate(param);
        updateTrans(saveResult, currentUserId,currentStatus, nextStatus, responseResult);
    }

    void broadBandIspChange(InternetAccessOrderAuditResult saveResult, Integer currentUserId,
                            Integer currentStatus, Integer nextStatus) {
        BandWidthIspChangeRequest request = new BandWidthIspChangeRequest();
        request.setBusiness_id(saveResult.getBusiness_id());
        request.setIsp_id(DeviceControlIspId.getIspIdByExport(saveResult.getExport()));
        DeviceControlParam<BandWidthIspChangeRequest> param = new DeviceControlParam<>();
        param.setRequest(request);
        param.setCurrentUserId(currentUserId);
        DeviceControlResponseResult<String> responseResult = deviceControlManager.broadBandIspChange(param);
        updateTrans(saveResult, currentUserId,currentStatus, nextStatus, responseResult);
    }

    private void updateTrans(InternetAccessOrderAuditResult saveResult, Integer currentUserId,
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
