package com.itshop.web.dao.mysql;

import com.itshop.web.dao.po.TIdcFirewallOrder;
import com.itshop.web.dto.request.TIdcFirewallOrderQueryParam;

import java.util.List;

public interface TIdcFirewallOrderDAO {

    int deleteByPrimaryKey(Integer orderId);

    int insert(TIdcFirewallOrder record);

    int insertSelective(TIdcFirewallOrder record);

    TIdcFirewallOrder selectByPrimaryKey(Integer orderId);

    int updateByPrimaryKeySelective(TIdcFirewallOrder record);

    int updateByPrimaryKey(TIdcFirewallOrder record);

    List<TIdcFirewallOrder> selectByQueryParam(TIdcFirewallOrderQueryParam queryParam);
}