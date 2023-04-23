package com.itshop.web.dao.po;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * t_statement
 * @author 
 */
@Data
public class TStatement implements Serializable {
    private Integer statementId;

    /**
     * 父结算单ID
     */
    private Integer parentStatementId;

    /**
     * 收款组织ID
     */
    private Integer payeeOrgId;

    /**
     * 付款方组织ID
     */
    private Integer payerOrgId;

    /**
     * 账期年月
     */
    private String statementMonth;

    /**
     * 是否是叶子节点
     */
    private Boolean isLeaf;

    /**
     * 支付状态
     */
    private String paymentStatus;

    /**
     * 支付时间
     */
    private Date paymentTime;

    /**
     * 银行流水
     */
    private String bankFlow;

    /**
     * 支付确认人ID
     */
    private Integer paymentConfirmUserId;

    /**
     * 结算金额
     */
    private BigDecimal payAmount;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 逻辑删除
     */
    private Boolean isDeleted;

    /**
     * 成本金额
     */
    private BigDecimal costAmount;

    private static final long serialVersionUID = 1L;
}