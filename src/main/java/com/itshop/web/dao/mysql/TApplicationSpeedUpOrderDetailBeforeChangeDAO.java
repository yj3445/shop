package com.itshop.web.dao.mysql;

import com.itshop.web.dao.po.TApplicationSpeedUpOrderDetailBeforeChange;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TApplicationSpeedUpOrderDetailBeforeChangeDAO {
    int deleteByPrimaryKey(Integer changeDetailId);

    int insert(TApplicationSpeedUpOrderDetailBeforeChange record);

    int insertSelective(TApplicationSpeedUpOrderDetailBeforeChange record);

    TApplicationSpeedUpOrderDetailBeforeChange selectByPrimaryKey(Integer changeDetailId);

    int updateByPrimaryKeySelective(TApplicationSpeedUpOrderDetailBeforeChange record);

    int updateByPrimaryKey(TApplicationSpeedUpOrderDetailBeforeChange record);

    int insertBatch(@Param("list") List<TApplicationSpeedUpOrderDetailBeforeChange> list);
}