package com.itshop.web.dto.request;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 产品项价格更新参数
 *
 */
@Data
public class ProductItemPriceUpdateParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 产品项id
     */
    private Integer productItemId;


    /**
     * 价格
     */
    private BigDecimal price;

}
