package com.itshop.web.dto.response;

import com.google.common.collect.Lists;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 产品OEM
 *
 * @author liufenglong
 * @date 2022/7/21
 */
@Data
public class ProductOemResp implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 产品OEMid
     */
    private Integer productOemId;

    /**
     * 产品id
     */
    private Integer productId;

    /**
     * 产品类别id
     */
    private Integer categoryId;

    /**
     * 产品编码
     */
    private String productCode;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 组织id
     */
    private Integer orgId;

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
     * 创建人
     */
    private Integer createrBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 操作人
     */
    private Integer modifiedBy;

    /**
     * 更新时间
     */
    private Date modifiedTime;

    /**
     * 核心应用场景
     */
    private List<ProductOemSceneResp> scenes;

    /**
     * 客户案例
     */
    private List<ProductOemCustomeCaseResp> customerCases;

    /**
     * OEM产品 折扣规则 项类型
     */
    private List<ProductOemDiscountRulesResp> discountRules;

    public ProductOemResp() {
        scenes = Lists.newArrayList();
        customerCases = Lists.newArrayList();
        discountRules = Lists.newArrayList();
    }
}
