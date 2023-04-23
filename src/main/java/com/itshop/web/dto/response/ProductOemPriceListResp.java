package com.itshop.web.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * OEM产品项价格
 */
@Data
public class ProductOemPriceListResp implements Serializable {

    /**
     * 品类ID
     */
    private Integer categoryId;

    /**
     * 品类编码
     */
    private String categoryCode;

    /**
     * 品类名称
     */
    private String categoryName;

    /**
     * 产品编码
     */
    private String productCode;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * OEM产品id
     */
    private Integer productOemId;

    /**
     * 产品id
     */
    private Integer productId;

    /**
     * 组织id
     */
    private Integer orgId;

    /**
     * 产品项ID
     */
    private Integer productItemId;

    /**
     * 产品项类型(存:BROAD_BAND、EXPORT、CONTRACT_PERIOD、PAYMENT_CYCLE、PAYMENT_METHOD)
     */
    private String itemType;

    /**
     * 产品项类型名称
     */
    private String itemTypeName;

    /**
     * 产品项名称
     */
    private String itemName;

    /**
     * 产品项值
     */
    private Integer itemValue;

    /**
     * 默认价格
     */
    private BigDecimal defaultPrice;

    /**
     * 默认价格单位(YEAR,MONTH,DAY,HOUR)
     */
    private String defaultPriceUnit;

    /**
     * 产品项价格明细
     */
    private List<ProductOemItemPriceResp> items;

    @Data
    public static class ProductOemItemPriceResp implements Serializable {
        /**
         * OEM产品项价格id
         */
        private Integer productOemItemPriceId;

        /**
         * 价格序列ID
         */
        private Integer agentPriceId;

        /**
         * 价格序列名称
         */
        private String priceName;


        /**
         * 定价类型(1-代理价格,2-终端价格)
         */
        private Integer priceType;

        /**
         * 价格
         */
        private BigDecimal price;

        /**
         * 价格单位(YEAR,MONTH,DAY,HOUR)
         */
        private String priceUnit;
    }
}
