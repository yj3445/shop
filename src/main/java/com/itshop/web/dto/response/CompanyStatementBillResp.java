package com.itshop.web.dto.response;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 公司月账单-账单明细
 */
@Data
public class CompanyStatementBillResp implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer orderId;

    /**
     * 账单编码
     */
    @ExcelProperty(value = "账单编码",index = 0)
    private String billCode;
    /**
     * 结算单编码
     */
    @ExcelProperty(value = "结算单",index = 1)
    private String statementNo;
    /**
     * 账单年月
     */
    @ExcelProperty(value = "账单年月",index = 2)
    private String billYearMonth;
    /**
     * 下单账户
     */
    @ExcelProperty(value = "下单账户",index = 3)
    private String userAccount;
    /**
     * 业务编码
     */
    @ExcelProperty(value = "业务编码",index = 4)
    private String businessId;
    /**
     * 产品分类
     */
    @ExcelProperty(value = "产品分类",index = 5)
    private String categoryName;

    /**
     * 产品编码
     */
    private String productCode;

    /**
     * 产品名称
     */
    @ExcelProperty(value = "产品名称",index = 6)
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
    @ExcelProperty(value = "消费时间",index = 7)
    private String serviceTime;

    /**
     * 消费金额
     */
    @ExcelProperty(value = "消费金额",index = 8)
    private BigDecimal payAmount;
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
     * 组织id
     */
    private Integer orgId;
    /**
     * 组织类型
     */
    private Integer orgType;
}
