package com.itshop.web.dao.po;

import java.io.Serializable;
import java.util.Date;

/**
 * t_product
 * @author 
 */
public class TProduct implements Serializable {
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
     * 排序
     */
    private Integer orderNum;

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
     * 是否启用
     */
    private Boolean enable;
    /**
     * 前台路径
     */
    private String path;

    /**
     * 产品状态
     */
    private Integer status;

    /**
     * 产品大图
     */
    private String productLargerImage;

    /**
     * 产品概要
     */
    private String productSummary;

    /**
     * 适用对象/公司类型
     */
    private String productApplicableCompany;

    /**
     * 产品描述
     */
    private String productDescribe;

    private static final long serialVersionUID = 1L;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
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

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getProductLargerImage() {
        return productLargerImage;
    }

    public void setProductLargerImage(String productLargerImage) {
        this.productLargerImage = productLargerImage;
    }

    public String getProductSummary() {
        return productSummary;
    }

    public void setProductSummary(String productSummary) {
        this.productSummary = productSummary;
    }

    public String getProductApplicableCompany() {
        return productApplicableCompany;
    }

    public void setProductApplicableCompany(String productApplicableCompany) {
        this.productApplicableCompany = productApplicableCompany;
    }

    public String getProductDescribe() {
        return productDescribe;
    }

    public void setProductDescribe(String productDescribe) {
        this.productDescribe = productDescribe;
    }
}