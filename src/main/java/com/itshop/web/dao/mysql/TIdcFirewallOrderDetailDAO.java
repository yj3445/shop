package com.itshop.web.dao.mysql;

import com.itshop.web.dao.po.TIdcFirewallOrderDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TIdcFirewallOrderDetailDAO {

    int deleteByPrimaryKey(Integer orderDetailId);

    int deleteByOrderId(Integer orderId);

    int insert(TIdcFirewallOrderDetail record);

    int insertSelective(TIdcFirewallOrderDetail record);

    TIdcFirewallOrderDetail selectByPrimaryKey(Integer orderDetailId);

    int updateByPrimaryKeySelective(TIdcFirewallOrderDetail record);

    int updateByPrimaryKey(TIdcFirewallOrderDetail record);

    int insertBatch(@Param("list") List<TIdcFirewallOrderDetail> list);

    List<TIdcFirewallOrderDetail> selectByOrderId(@Param("list") List<Integer> orderIds);
}