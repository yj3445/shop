package com.itshop.web.dto.request;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 产品OEM保存参数
 *
 * @author liufenglong
 * @date 2022/7/22
 */
@Data
public class ProductOemSaveParam  implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 产品id
     */
    private Integer productId;

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

    /**
     * 核心应用场景
     */
    private List<ProductOemSceneParam> scenes;

    /**
     * 典型客户案例
     */
    private List<ProductOemCustomeCaseParam> customerCases;

    /**
     * 折扣规则
     */
    private List<ProductOemDiscountRulesParam> discountRules;
}
