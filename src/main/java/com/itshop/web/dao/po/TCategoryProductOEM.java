package com.itshop.web.dao.po;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liufenglong
 * @date 2022/8/1
 */
@Data
public class TCategoryProductOEM  implements Serializable {
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
     * 品类URL路径
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
     * 产品启用标识
     */
    private Boolean productEnable;

    /**
     * 产品URL路径
     */
    private String productPath;

    /**
     * 代理商代理产品关系id
     */
    private Integer productAgentRelationId;

    /**
     * 代理商是否代理该产品
     */
    private Boolean productIsAgent;

    /**
     * 产品OEMid
     */
    private Integer productOemId;

    /**
     * 产品OEM名称
     */
    private String productOemName;

    /**
     * 产品OEM大图
     */
    private String productOemLargerImage;

    /**
     * 产品OEM描述
     */
    private String productOemDescribe;

    /**
     * 产品OEM概要
     */
    private String productOemSummary;

    /**
     * 适用对象/公司类型
     */
    private String productOemApplicableCompany;
}
