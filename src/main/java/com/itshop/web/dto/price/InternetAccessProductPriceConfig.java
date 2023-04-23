package com.itshop.web.dto.price;

import com.google.common.collect.Lists;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 互联网接入-产品价格配置
 */
@Data
public class InternetAccessProductPriceConfig  implements Serializable {

    /**
     * 带宽价格配置
     */
    @Data
    public static class BroadBandPriceConfig {
        /**
         * OEM产品项价格id
         */
        private Integer productOemItemPriceId;
        /**
         * 速率
         */
        private Integer mbps;
        /**
         * 单价
         */
        private Double price;
        /**
         * 单价单位(MONTH-月,YEAR-年)
         */
        private String priceUnit;
    }

    /**
     * 折扣率配置
     */
    @Data
    public static class DiscountRateConfig {
        /**
         * 产品OEM折扣规则id
         */
        private Integer productOemDiscountRulesId;
        /**
         * 值
         */
        private Integer value;
        /**
         * 折扣率(整数)
         */
        private Integer discountRate;
    }

    /**
     * 商品id
     */
    private Integer productId;

    /**
     * 产品OEMid
     */
    private Integer productOemId;

    /**
     * 商品名称
     */
    private String productName;
    /**
     * 带宽
     */
    private List<BroadBandPriceConfig> broadBand;

    /**
     * 出口(1智选,2联通,3电信,4移动)
     */
    private List<DiscountRateConfig> export;

    /**
     * 合同期限(0,12,36)
     */
    private List<DiscountRateConfig> contractPeriod;

    /**
     * 缴费周期(1月,2季,3年)
     */
    private List<DiscountRateConfig> paymentCycle;

    /**
     * 付费方式(1预付,2后付)
     */
    private List<DiscountRateConfig> paymentMethod;

    public InternetAccessProductPriceConfig(){
        this.setBroadBand(Lists.newArrayList());
        this.setExport(Lists.newArrayList());
        this.setContractPeriod(Lists.newArrayList());
        this.setPaymentCycle(Lists.newArrayList());
        this.setPaymentMethod(Lists.newArrayList());
    }
}
