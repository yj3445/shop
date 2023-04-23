package com.itshop.web.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 * 代理月账单概览
 */
@Data
public class AgentMonthlyBillOverviewPageResp implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 组织机构链表
     */
    LinkedList<OrganizationalResp> orgLinked;

    /**
     * 代理月账单集合
     */
    List<AgentMonthlyBillOverviewResp> agentMonthlyBills;


    /**
     * 给上级结算金额
     */
    BigDecimal payAmountForParentOrg;

    /**
     * 账期
     */
    String statementMonth;
}
