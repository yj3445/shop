package com.itshop.web.manager;

import com.google.common.base.Joiner;
import com.itshop.web.bo.OrderSaveResult;
import com.itshop.web.dao.mysql.TDeviceControlTransDAO;
import com.itshop.web.dao.po.TDeviceControlTrans;
import com.itshop.web.enums.DeviceControlFinalTransCode;
import com.itshop.web.enums.DeviceControlTransCode;
import io.swagger.models.auth.In;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;

@Service
public class DeviceControlTransManager {

    @Autowired
    TDeviceControlTransDAO tDeviceControlTransDAO;

    public int insertSelective(OrderSaveResult orderSaveResult, Integer currentUserId) {
        TDeviceControlTrans tDeviceControlTrans = new TDeviceControlTrans();
        tDeviceControlTrans.setServiceType(orderSaveResult.getServiceType());
        tDeviceControlTrans.setChangeId(orderSaveResult.getChangeId());
        tDeviceControlTrans.setOrderId(orderSaveResult.getOrderId());
        tDeviceControlTrans.setEnd(orderSaveResult.getTransEnd());
        tDeviceControlTrans.setMiddleStatus(orderSaveResult.getMiddleStatusStr());
        tDeviceControlTrans.setProcess(orderSaveResult.getTransCurrent());
        tDeviceControlTrans.setRetryCount(0);
        tDeviceControlTrans.setRemark(orderSaveResult.getMiddleStatusDesc());
        tDeviceControlTrans.setCreateTime(new Date());
        tDeviceControlTrans.setCreaterBy(currentUserId);
        tDeviceControlTrans.setModifiedBy(currentUserId);
        tDeviceControlTrans.setModifiedTime(new Date());
        tDeviceControlTrans.setIsDeleted(false);
        tDeviceControlTransDAO.insertSelective(tDeviceControlTrans);
        return tDeviceControlTrans.getTransId();
    }
}
