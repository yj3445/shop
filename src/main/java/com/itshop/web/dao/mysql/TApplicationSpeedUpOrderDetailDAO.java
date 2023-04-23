package com.itshop.web.dao.mysql;

import com.itshop.web.dao.po.TApplicationSpeedUpOrderDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TApplicationSpeedUpOrderDetailDAO {
    int deleteByPrimaryKey(Integer orderDetailId);

    int deleteByOrderId(Integer orderId);

    int insert(TApplicationSpeedUpOrderDetail record);

    int insertSelective(TApplicationSpeedUpOrderDetail record);

    TApplicationSpeedUpOrderDetail selectByPrimaryKey(Integer orderDetailId);

    int updateByPrimaryKeySelective(TApplicationSpeedUpOrderDetail record);

    int updateByPrimaryKey(TApplicationSpeedUpOrderDetail record);

    int insertBatch(@Param("list") List<TApplicationSpeedUpOrderDetail> list);

    List<TApplicationSpeedUpOrderDetail> selectByOrderId(@Param("list") List<Integer> orderIds);
}