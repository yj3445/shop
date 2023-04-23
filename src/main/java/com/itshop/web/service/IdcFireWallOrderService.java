package com.itshop.web.service;

import com.google.common.collect.Lists;
import com.itshop.web.bo.DeviceControlParam;
import com.itshop.web.bo.IdcFireWallOrderSaveResult;
import com.itshop.web.dao.mysql.TDeviceControlTransDAO;
import com.itshop.web.dao.mysql.TInternetAccessOrderDAO;
import com.itshop.web.dao.po.TDeviceControlTrans;
import com.itshop.web.dao.po.TInternetAccessOrder;
import com.itshop.web.dto.deviceControl.DeviceControlResponseResult;
import com.itshop.web.dto.deviceControl.FireWallAddPortRequest;
import com.itshop.web.dto.deviceControl.FireWallAddPortResponse;
import com.itshop.web.dto.deviceControl.FireWallEnableRequest;
import com.itshop.web.dto.request.IdcFireWallOrderSaveParam;
import com.itshop.web.enums.DeviceControlTransCode;
import com.itshop.web.enums.DeviceControlTransStatus;
import com.itshop.web.exception.BusinessException;
import com.itshop.web.manager.DeviceControlManager;
import com.itshop.web.manager.IdcFireWallOrderManager;
import com.itshop.web.service.devicecontrol.IdcFireWallDeviceControlService;
import com.itshop.web.support.ApplicationContextProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.Objects;

@Service
public class IdcFireWallOrderService {

    @Autowired
    IdcFireWallOrderManager idcFireWallOrderManager;

    @Autowired
    TInternetAccessOrderDAO tInternetAccessOrderDAO;

    @Autowired
    IdcFireWallDeviceControlService idcFireWallDeviceControlService;

    public int save(IdcFireWallOrderSaveParam firewallOrderSaveParam) {
        //取互联网接入的10%
        TInternetAccessOrder tInternetAccessOrder = tInternetAccessOrderDAO.selectByPrimaryKey(firewallOrderSaveParam.getInternetAccessOrderId());
        if( tInternetAccessOrderDAO == null){
            throw new BusinessException(String.format("未找到编号[%d]对应的互联网接入订单,请检查!",firewallOrderSaveParam.getInternetAccessOrderId()));
        }
        BigDecimal totalPrice= tInternetAccessOrder.getTotalPrice().multiply(BigDecimal.valueOf(0.1));
        firewallOrderSaveParam.setTotalPrice(totalPrice);
        IdcFireWallOrderSaveResult saveResult = idcFireWallOrderManager.save(firewallOrderSaveParam);
        if (saveResult.getTransId() > 0) {
            idcFireWallDeviceControlService.callDeviceControl(saveResult, firewallOrderSaveParam.getCreaterBy());
        }
        return saveResult.getOrderId();
    }

}
