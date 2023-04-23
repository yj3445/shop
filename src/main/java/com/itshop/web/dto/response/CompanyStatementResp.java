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
 * 公司月账单
 */
@Data
public class CompanyStatementResp implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 结算单编号
     */
    @ExcelProperty(value = "结算单编号",index = 0)
    private String statementNo;
    /**
     * 结算单ID
     */
    private Integer statementId;

    /**
     * 账期
     */
    @ExcelProperty(value = "账期",index = 1)
    private String statementMonth;

    /**
     * 出账日期
     */
    @JSONField(format = "yyyy-MM-dd ")
    @DateTimeFormat(pattern="yyyy-MM-dd ")
    @JsonFormat(pattern = "yyyy-MM-dd ",timezone = "GMT+08:00")
    @ExcelProperty(value = "出账日期",index = 2)
    private Date outAccountDay;
    /**
     * 账单类型
     */
    @ExcelProperty(value = "账单类型",index = 3)
    private String billType;
    /**
     * 用户账号
     */
    @ExcelProperty(value = "用户账号",index = 4)
    private String userAccount;
    /**
     * 结算金额
     */
    @ExcelProperty(value = "结算金额",index = 5)
    private BigDecimal payAmount;
    /**
     * 结算交易银行流水号
     */
    @ExcelProperty(value = "结算交易银行流水号",index = 6)
    private String bankFlow;
    /**
     * 结算成功时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+08:00")
    @ExcelProperty(value = "结算成功时间",index = 7)
    private Date paymentTime;
    /**
     * 结算状态
     */
    @ExcelProperty(value = "结算成功时间",index = 8)
    private String paymentStatus;
}
