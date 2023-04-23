package com.itshop.web.dto.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.itshop.web.dto.price.InternetAccessProductPriceItem;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 互联网接入产品价格响应
 */
@Data
public class InternetAccessOrderPriceResp  implements Serializable {
    /**
     * 商品id
     */
    private Integer productId;

    /**
     * 产品OEMid
     */
    private Integer productOemId;

    /**
     * 基础价格
     */
    private BigDecimal basicPrice =BigDecimal.ZERO;

    /**
     * 出口(1智选,2联通,3电信,4移动)折扣价
     */
    private BigDecimal exportDiscountPrice=BigDecimal.ZERO;

    /**
     * 合同期限(0,12,36)折扣价
     */
    private BigDecimal contractPeriodDiscountPrice=BigDecimal.ZERO;

    /**
     * 缴费周期(1月,2季,3年)折扣价
     */
    private BigDecimal paymentCycleDiscountPrice=BigDecimal.ZERO;

    /**
     * 付费方式(1预付,2后付)折扣价
     */
    private BigDecimal paymentMethodDiscountPrice=BigDecimal.ZERO;

    /**
     * 总价
     */
    private BigDecimal totalPrice=BigDecimal.ZERO;

    /**
     * 互联网接入产品价格项目
     */
    @JsonIgnore
    @JSONField(serialize = false)
    private InternetAccessProductPriceItem internetAccessProductPriceItem;
}
