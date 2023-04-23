package com.itshop.web.dao.mysql;

import com.itshop.web.bo.InternetAccessOrderBeforeChangeQueryParam;
import com.itshop.web.dao.po.TInternetAccessOrderBeforeChange;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TInternetAccessOrderBeforeChangeDAO {
    int deleteByPrimaryKey(Integer changeId);

    int insertSelective(TInternetAccessOrderBeforeChange record);

    TInternetAccessOrderBeforeChange selectByPrimaryKey(Integer changeId);

    int updateByPrimaryKeySelective(TInternetAccessOrderBeforeChange record);

    /**
     * 根据订单号得到订单历史信息
     *
     * @param orderId
     * @return
     */
    List<TInternetAccessOrderBeforeChange> selectByOrderId(Integer orderId);

    /**
     * 根据订单号得到订单历史数
     *
     * @param orderId
     * @return
     */
    Integer selectCountByOrderId(InternetAccessOrderBeforeChangeQueryParam queryParam);

    /**
     * @param orderId
     * @return
     */
    TInternetAccessOrderBeforeChange selectLastTimeChangeInfo(@Param("orderId") Integer orderId,@Param("status") Integer status);
}