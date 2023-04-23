package com.itshop.web.dto.response;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 代理月账单概览
 */
@Data
public class AgentMonthlyBillOverviewResp implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 结算单ID
     */
    private Integer statementId;

    /**
     * 结算单编号
     */
    @ExcelProperty(value = "结算单编号", index = 0)
    private String statementNo;

    /**
     * 账期
     */
    @ExcelProperty(value = "账期", index = 1)
    private String statementMonth;

    /**
     * 出账日期
     */
    @JSONField(format = "yyyy-MM-dd ")
    @DateTimeFormat(pattern = "yyyy-MM-dd ")
    @JsonFormat(pattern = "yyyy-MM-dd ", timezone = "GMT+08:00")
    @ExcelProperty(value = "出账日期", index = 2)
    private String outAccountDay;

    /**
     * 组织类型(服务提供商、代理商、代理商客户公司）
     */
    private Integer orgType;

    /**
     * 公司类型
     */
    @ExcelProperty(value = "公司类型", index = 3)
    private String orgTypeDesc;

    /**
     * 收款组织ID
     */
    private Integer payeeOrgId;

    /**
     * 付款方组织ID
     */
    private Integer payerOrgId;

    /**
     * 付款公司名称
     */
    @ExcelProperty(value = "付款公司名称", index = 4)
    private String payerOrgCompanyName;

    /**
     * 成本金额
     */
    @ExcelProperty(value = "成本金额", index = 5)
    private BigDecimal costAmount;

    /**
     * 结算金额
     */
    @ExcelProperty(value = "结算金额", index = 6)
    private BigDecimal payAmount;

    /**
     * 结算交易银行流水号
     */
    @ExcelProperty(value = "结算交易银行流水号", index = 8)
    private String bankFlow;

    /**
     * 结算人
     */
    @ExcelProperty(value = "结算人", index = 7)
    private String paymentConfirmUserName;

    /**
     * 结算成功时间
     */
    @JSONField(format = "yyyy-MM-dd ")
    @DateTimeFormat(pattern = "yyyy-MM-dd ")
    @JsonFormat(pattern = "yyyy-MM-dd ", timezone = "GMT+08:00")
    @ExcelProperty(value = "结算成功时间", index = 9)
    private Date paymentTime;

    /**
     * 结算状态
     */
    @ExcelProperty(value = "结算状态", index = 10)
    private String paymentStatus;

    /**
     * 对账单数量
     */
    private Integer billCount;

    /**
     * 是否可以下钻
     */
    private Boolean canDrillDown;

    /**
     * 是否可以显示
     */
    private Boolean canShowBillDetail;

    /**
     * 是否可以确认支付
     */
    private Boolean canConfirmPayment;

}
