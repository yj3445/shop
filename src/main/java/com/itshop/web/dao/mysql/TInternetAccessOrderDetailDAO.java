package com.itshop.web.dao.mysql;

import com.itshop.web.dao.po.TInternetAccessOrderDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 互联网接入-自定义-订单详情 数据处理
 *
 * @author liufenglong
 * @date 2022/5/7
 */
public interface TInternetAccessOrderDetailDAO {
    int deleteByPrimaryKey(Integer orderDetailId);

    int deleteByOrderId(Integer orderId);

    int insert(TInternetAccessOrderDetail record);

    int insertSelective(TInternetAccessOrderDetail record);

    TInternetAccessOrderDetail selectByPrimaryKey(Integer orderDetailId);

    int updateByPrimaryKeySelective(TInternetAccessOrderDetail record);

    int updateByPrimaryKey(TInternetAccessOrderDetail record);

    int insertBatch(@Param("list") List<TInternetAccessOrderDetail> list);

    List<TInternetAccessOrderDetail> selectByOrderId(@Param("list") List<Integer> orderIds);
}