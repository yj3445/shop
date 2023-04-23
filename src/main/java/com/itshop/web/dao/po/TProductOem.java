package com.itshop.web.dao.po;

import java.io.Serializable;
import java.util.Date;

/**
 * t_product_oem
 * @author 
 */
public class TProductOem implements Serializable {
    /**
     * 产品OEMid
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
     * 产品OEM名称
     */
    private String productOemName;

    /**
     * 产品OEM大图
     */
    private String productOemLargerImage;

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
     * 删除标识(0-未删除,1-删除)
     */
    private Boolean isDeleted;

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

    private static final long serialVersionUID = 1L;

    public Integer getProductOemId() {
        return productOemId;
    }

    public void setProductOemId(Integer productOemId) {
        this.productOemId = productOemId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getProductOemName() {
        return productOemName;
    }

    public void setProductOemName(String productOemName) {
        this.productOemName = productOemName;
    }

    public String getProductOemLargerImage() {
        return productOemLargerImage;
    }

    public void setProductOemLargerImage(String productOemLargerImage) {
        this.productOemLargerImage = productOemLargerImage;
    }

    public Integer getCreaterBy() {
        return createrBy;
    }

    public void setCreaterBy(Integer createrBy) {
        this.createrBy = createrBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Integer modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getProductOemDescribe() {
        return productOemDescribe;
    }

    public void setProductOemDescribe(String productOemDescribe) {
        this.productOemDescribe = productOemDescribe;
    }

    public String getProductOemSummary() {
        return productOemSummary;
    }

    public void setProductOemSummary(String productOemSummary) {
        this.productOemSummary = productOemSummary;
    }

    public String getProductOemApplicableCompany() {
        return productOemApplicableCompany;
    }

    public void setProductOemApplicableCompany(String productOemApplicableCompany) {
        this.productOemApplicableCompany = productOemApplicableCompany;
    }
}