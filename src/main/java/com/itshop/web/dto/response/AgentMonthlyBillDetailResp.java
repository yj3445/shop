package com.itshop.web.dto.response;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 代理月账单概览-客户公司账单详情
 */
@Data
public class AgentMonthlyBillDetailResp implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 结算单编码
     */
    @ExcelProperty(value = "对账单号", index = 0)
    private String statementNo;
    /**
     * 账单年月
     */
    @ExcelProperty(value = "账期", index = 1)
    private String billYearMonth;

    /**
     * 下单账户
     */
    @ExcelProperty(value = "账户", index = 2)
    private String userAccount;

    /**
     * 账单编码
     */
    @ExcelProperty(value = "账单号", index = 3)
    private String billCode;

    /**
     * 订单ID
     */
    private Integer orderId;

    /**
     * 变更前记录id
     */
    private Integer changeId;

    /**
     * 订单
     */
    @ExcelProperty(value = "订单", index = 4)
    private String  orderNo;
    /**
     * 产品分类
     */
    @ExcelProperty(value = "产品分类", index = 5)
    private String categoryName;

    private String productCode;

    /**
     * 产品名称
     */
    @ExcelProperty(value = "产品名称", index = 6)
    private String productName;

    /**
     * 消费开始时间
     */
    private Date serviceStartTime;
    /**
     * 消费介绍时间
     */
    private Date serviceEndTime;
    /**
     * 消费时间
     */
    @ExcelProperty(value = "消费时间", index = 7)
    private String serviceTime;

    /**
     * 成本金额
     */
    @ExcelProperty(value = "成本金额", index = 8)
    private BigDecimal costAmount;

    /**
     * 服务提供商金额
     */
    private BigDecimal providerPayAmount;
    /**
     * 一级代理商金额
     */
    private BigDecimal agentLevel1PayAmount;
    /**
     * 二级代理商金额
     */
    private BigDecimal agentLevel2PayAmount;
    /**
     * 三级代理商金额
     */
    private BigDecimal agentLevel3PayAmount;
    /**
     * 终端用户金额
     */
    private BigDecimal endUserPayAmount;

    /**
     * 消费金额
     */
    @ExcelProperty(value = "消费金额", index = 9)
    private BigDecimal payAmount;


    /**
     * 对账状态
     */
    @ExcelProperty(value = "对账状态", index = 10)
    private String billStatus;

    /**
     * 对账日期
     */
    @ExcelProperty(value = "对账日期", index = 11)
    private Date outAccountDay;

    /**
     * 对账人
     */
    @ExcelProperty(value = "对账人", index = 12)
    private String paymentConfirmUserName;

    /**
     * 结算交易银行流水号
     */
    @ExcelProperty(value = "结算交易银行流水号", index = 13)
    private String bankFlow;

    /**
     * 结算成功时间
     */
    @ExcelProperty(value = "结算成功时间", index = 14)
    private Date paymentTime;

    /**
     * 结算状态
     */
    @ExcelProperty(value = "结算状态", index = 15)
    private String paymentStatus;

    /**
     * 组织id
     */
    private Integer orgId;
    /**
     * 组织类型
     */
    private Integer orgType;
}
