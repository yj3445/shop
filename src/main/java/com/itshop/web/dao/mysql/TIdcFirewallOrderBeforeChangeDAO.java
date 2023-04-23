package com.itshop.web.dao.mysql;

import com.itshop.web.dao.po.TIdcFirewallOrderBeforeChange;

public interface TIdcFirewallOrderBeforeChangeDAO {
    int deleteByPrimaryKey(Integer changeId);

    int insert(TIdcFirewallOrderBeforeChange record);

    int insertSelective(TIdcFirewallOrderBeforeChange record);

    TIdcFirewallOrderBeforeChange selectByPrimaryKey(Integer changeId);

    int updateByPrimaryKeySelective(TIdcFirewallOrderBeforeChange record);

    int updateByPrimaryKey(TIdcFirewallOrderBeforeChange record);
}