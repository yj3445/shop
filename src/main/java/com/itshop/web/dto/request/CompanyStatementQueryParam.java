package com.itshop.web.dto.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 公司月账单查询
 *
 */
@Data
public class CompanyStatementQueryParam implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 账期-起始日期
     */
    private String startStatementMonth;

    /**
     * 账期-结束日期
     */
    private String endStatementMonth;

    /**
     * 账单类型
     */
    private List<String> billType;

    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 结算起始时间
     */
    private String startPaymentTime;

    /**
     * 结算结束xz时间
     */
    private String endPaymentTime;

    /**
     * 组织ID
     */
    private Integer orgId;
}
