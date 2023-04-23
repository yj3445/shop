package com.itshop.web.dto.request;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * OEM产品项&定价序列--价格设置
 *
 * @author liufenglong
 * @date 2022/7/25
 */
@Data
public class ProductOemItemPriceSaveParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * OEM产品id
     */
    private Integer productOemId;

    /**
     * 产品id
     */
    private Integer productId;

    /**
     * 产品项ID
     */
    private Integer productItemId;

    /**
     * 价格序列ID
     */
    private Integer agentPriceId;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 价格单位(YEAR,MONTH,DAY,HOUR)
     */
    private String priceUnit;
}
