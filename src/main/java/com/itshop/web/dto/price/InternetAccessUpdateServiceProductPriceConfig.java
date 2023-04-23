package com.itshop.web.dto.price;

import com.google.common.collect.Lists;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 互联网接入-升级服务-产品价格配置
 */
@Data
public class InternetAccessUpdateServiceProductPriceConfig  implements Serializable {
    /**
     * 商品id
     */
    private Integer productId;
    /**
     * 商品名称
     */
    private String productName;

    /**
     * 缴费周期(1月,2季,3年)
     */
    private List<InternetAccessProductPriceConfig.DiscountRateConfig> paymentCycle;

    /**
     * 付费方式(1预付,2后付)
     */
    private List<InternetAccessProductPriceConfig.DiscountRateConfig> paymentMethod;

    public InternetAccessUpdateServiceProductPriceConfig() {
        this.setPaymentCycle(Lists.newArrayList());
        this.setPaymentMethod(Lists.newArrayList());
    }
}
