package com.itshop.web.dto.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 产品OEM折扣规则
 *
 * @author liufenglong
 * @date 2022/7/22
 */
@Data
public class ProductOemDiscountRulesParam   implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 产品OEM折扣规则id
     */
    private Integer productOemDiscountRulesId;

    /**
     * 产品项ID
     */
    private Integer productItemId;

    /**
     * 折扣率(形如:90%)
     */
    private Integer discountRate;
}
