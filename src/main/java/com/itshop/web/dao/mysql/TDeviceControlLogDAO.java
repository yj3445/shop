package com.itshop.web.dao.mysql;

import com.itshop.web.dao.po.TDeviceControlLog;
import com.itshop.web.dao.po.TDeviceControlLogWithBLOBs;

public interface TDeviceControlLogDAO {
    int deleteByPrimaryKey(Integer logId);

    int insert(TDeviceControlLogWithBLOBs record);

    int insertSelective(TDeviceControlLogWithBLOBs record);

    TDeviceControlLogWithBLOBs selectByPrimaryKey(Integer logId);

    int updateByPrimaryKeySelective(TDeviceControlLogWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(TDeviceControlLogWithBLOBs record);

    int updateByPrimaryKey(TDeviceControlLog record);
}