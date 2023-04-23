package com.itshop.web.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class InternetAccessUpdateServiceOrderPriceResp  implements Serializable {
    /**
     * 基础价格
     */
    private BigDecimal basicPrice;

    /**
     * 缴费周期(1月,2季,3年)折扣价
     */
    private BigDecimal paymentCycleDiscountPrice;

    /**
     * 付费方式(1预付,2后付)折扣价
     */
    private BigDecimal paymentMethodDiscountPrice;

    /**
     * 总价
     */
    private BigDecimal totalPrice;
}
