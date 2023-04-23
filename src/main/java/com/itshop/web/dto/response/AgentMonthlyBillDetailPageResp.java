package com.itshop.web.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * 代理月账单概览-客户公司账单详情
 */
@Data
public class AgentMonthlyBillDetailPageResp implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 组织机构链表
     */
    LinkedList<OrganizationalResp> orgLinked;

    /**
     * 代理月账单概览-客户公司账单详情
     */
    List<AgentMonthlyBillDetailResp> agentMonthlyBillDetailList;
}
