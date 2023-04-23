package com.itshop.web.dao.mysql;

import com.itshop.web.dao.po.TOrderAmount;

public interface TOrderAmountDAO {
    int deleteByPrimaryKey(Integer orderAmountId);

    int insert(TOrderAmount record);

    int insertSelective(TOrderAmount record);

    TOrderAmount selectByPrimaryKey(Integer orderAmountId);

    /***
     * 根据订单id和变更id得到 订单金额
     *
     * @param orderAmount
     * @return
     */
    TOrderAmount selectByOrderIdAndChangeId(TOrderAmount orderAmount);

    int updateByPrimaryKeySelective(TOrderAmount record);

    int updateByPrimaryKey(TOrderAmount record);
}