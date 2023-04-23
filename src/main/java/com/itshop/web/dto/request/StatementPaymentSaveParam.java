package com.itshop.web.dto.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 结算单-交易银行流水信息
 */
@Data
public class StatementPaymentSaveParam  implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 结算单ID
     */
    private Integer statementId;


    /**
     * 支付时间
     */
    private Date paymentTime;

    /**
     * 银行流水
     */
    private String bankFlow;
}
