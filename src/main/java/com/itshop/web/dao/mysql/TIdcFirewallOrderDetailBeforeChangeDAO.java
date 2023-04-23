package com.itshop.web.dao.mysql;

import com.itshop.web.dao.po.TIdcFirewallOrderDetailBeforeChange;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TIdcFirewallOrderDetailBeforeChangeDAO {
    int deleteByPrimaryKey(Integer changeDetailId);

    int insert(TIdcFirewallOrderDetailBeforeChange record);

    int insertSelective(TIdcFirewallOrderDetailBeforeChange record);

    TIdcFirewallOrderDetailBeforeChange selectByPrimaryKey(Integer changeDetailId);

    int updateByPrimaryKeySelective(TIdcFirewallOrderDetailBeforeChange record);

    int updateByPrimaryKey(TIdcFirewallOrderDetailBeforeChange record);

    int insertBatch(@Param("list") List<TIdcFirewallOrderDetailBeforeChange> list);
}