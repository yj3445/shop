package com.itshop.web.dao.mysql;

import com.itshop.web.dao.po.TApplicationSpeedUpOrderBeforeChange;

public interface TApplicationSpeedUpOrderBeforeChangeDAO {
    int deleteByPrimaryKey(Integer changeId);

    int insert(TApplicationSpeedUpOrderBeforeChange record);

    int insertSelective(TApplicationSpeedUpOrderBeforeChange record);

    TApplicationSpeedUpOrderBeforeChange selectByPrimaryKey(Integer changeId);

    int updateByPrimaryKeySelective(TApplicationSpeedUpOrderBeforeChange record);

    int updateByPrimaryKey(TApplicationSpeedUpOrderBeforeChange record);
}