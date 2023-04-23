package com.itshop.web.dto.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.itshop.web.dto.price.ApplicationSpeedUpPriceItem;
import com.itshop.web.dto.price.InternetAccessProductPriceConfig;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 应用加速订单价格
 */
@Data
public class ApplicationSpeedUpOrderPriceResp  implements Serializable {
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

    /**
     * 价格明细
     */
    private List<ApplicationSpeedUpPriceItem> priceItemList;

    /**
     * 缴费周期(1月,2季,3年)折扣配置
     */
    @JsonIgnore
    @JSONField(serialize = false)
    private InternetAccessProductPriceConfig.DiscountRateConfig paymentCycleDiscountRateConfig;

    /**
     * 付费方式(1预付,2后付)折扣配置
     */
    @JsonIgnore
    @JSONField(serialize = false)
    private InternetAccessProductPriceConfig.DiscountRateConfig paymentMethodDiscountRateConfig;
}
