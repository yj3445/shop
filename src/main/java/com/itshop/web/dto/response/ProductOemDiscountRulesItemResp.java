package com.itshop.web.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * OEM产品 折扣规则 项
 *
 * @author liufenglong
 * @date 2022/7/21
 */
@Data
public class ProductOemDiscountRulesItemResp implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 产品OEM折扣规则id
     */
    private Integer productOemDiscountRulesId;

    /**
     * 规则项对应的值
     */
    private Integer productItemId;

    /**
     * 产品项名称
     */
    private String itemName;

    /**
     * 产品项值
     */
    private Integer itemValue;

    /**
     * 产品项排序
     */
    private Integer orderNum;

    /**
     * 默认折扣
     */
    private BigDecimal defaultDiscount;

    /**
     * 折扣率(形如:90%)
     */
    private Integer discountRate;
}
