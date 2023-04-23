package com.itshop.web.dao.po;

import java.io.Serializable;
import java.util.Date;

/**
 * t_product_oem_custome_case
 * @author 
 */
public class TProductOemCustomeCase implements Serializable {
    private Integer productOemCustomeCaseId;

    private Integer orgId;

    private Integer productOemId;

    private Integer productId;

    /**
     * 图片地址
     */
    private String picUrl;

    /**
     * 案例名称
     */
    private String customeCaseName;

    /**
     * 案例描述
     */
    private String customeCaseDesc;

    private Integer createrBy;

    private Date createTime;

    private Integer modifiedBy;

    private Date modifiedTime;

    private Boolean isDeleted;

    private static final long serialVersionUID = 1L;

    public Integer getProductOemCustomeCaseId() {
        return productOemCustomeCaseId;
    }

    public void setProductOemCustomeCaseId(Integer productOemCustomeCaseId) {
        this.productOemCustomeCaseId = productOemCustomeCaseId;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

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

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getCustomeCaseName() {
        return customeCaseName;
    }

    public void setCustomeCaseName(String customeCaseName) {
        this.customeCaseName = customeCaseName;
    }

    public String getCustomeCaseDesc() {
        return customeCaseDesc;
    }

    public void setCustomeCaseDesc(String customeCaseDesc) {
        this.customeCaseDesc = customeCaseDesc;
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
}