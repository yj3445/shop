package com.itshop.web.dto.response;

import com.itshop.web.dto.request.CustomerStatementBillQuery;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrganizationalResp implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 组织id
     */
    private Integer orgId;

    /**
     * 组织类型(服务提供商、代理商、代理商客户公司）
     */
    private Integer orgType;


    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 客户账户-月账单概览 查询条件
     */
    private CustomerStatementBillQuery query;
}
