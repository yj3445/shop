package com.itshop.web.dao.po;

import lombok.Data;

import java.io.Serializable;

@Data
public class TCategoryProduct implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 品类id
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
     * 品类级别(1,2,3,4...)
     */
    private Integer categoryLevel;

    /**
     * 排序
     */
    private Integer categoryOrderNum;

    /**
     * 品类启用标识
     */
    private Boolean categoryEnable;

    /**
     * 品类url路径
     */
    private String categoryPath;

    /**
     * 父品类id
     */
    private Integer parentCategoryId;

    /**
     * 产品id
     */
    private Integer productId;

    /**
     * 产品编码
     */
    private String productCode;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 排序
     */
    private Integer productOrderNum;

    /**
     * 产品是否启用标识
     */
    private Boolean productEnable;

    /**
     * 产品url路径
     */
    private String productPath;

    /**
     * 产品描述
     */
    private String productDescribe;

    /**
     * 产品大图
     */
    private String productLargerImage;

    /**
     * 产品摘要
     */
    private String productSummary;
}
