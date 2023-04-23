package com.itshop.web.dto.request;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class OrderAuditParam implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 订单ID
     */
    private Integer orderId;

    /**
     * 商品id
     */
    private Integer productId;

    /**
     * 审核通过(备注)
     */
    private String auditPassRemark;
    /**
     * 审核拒绝(备注)
     */
    private String auditRefusedRemark;

    /**
     * 审核价格
     */
    private BigDecimal auditPrice;
}
