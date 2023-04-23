package com.itshop.web.dao.mysql;

import com.itshop.web.dao.po.TDeviceControlTrans;

public interface TDeviceControlTransDAO {
    int deleteByPrimaryKey(Integer transId);

    int insert(TDeviceControlTrans record);

    int insertSelective(TDeviceControlTrans record);

    TDeviceControlTrans selectByPrimaryKey(Integer transId);

    int updateByPrimaryKeySelective(TDeviceControlTrans record);

    int updateByPrimaryKey(TDeviceControlTrans record);
}