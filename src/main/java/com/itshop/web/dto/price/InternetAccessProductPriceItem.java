package com.itshop.web.dto.price;

import lombok.Data;

import java.io.Serializable;

/**
 * 互联网接入产品价格项目
 */
@Data
public class InternetAccessProductPriceItem  implements Serializable {
    /**
     * 带宽价格配置
     */
    private InternetAccessProductPriceConfig.BroadBandPriceConfig broadBandPriceConfig;

    /**
     * 出口(1智选,2联通,3电信,4移动)折扣配置
     */
    private InternetAccessProductPriceConfig.DiscountRateConfig exportDiscountRateConfig;

    /**
     * 合同期限(0,12,36)折扣配置
     */
    private InternetAccessProductPriceConfig.DiscountRateConfig contractPeriodDiscountRateConfig;

    /**
     * 缴费周期(1月,2季,3年)折扣配置
     */
    private InternetAccessProductPriceConfig.DiscountRateConfig paymentCycleDiscountRateConfig;

    /**
     * 付费方式(1预付,2后付)折扣配置
     */
    private InternetAccessProductPriceConfig.DiscountRateConfig paymentMethodDiscountRateConfig;
}
