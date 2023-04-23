package com.itshop.web.dao.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * t_url_speed_up_config
 * @author 
 */
public class TUrlSpeedUpConfig implements Serializable {
    /**
     * url加速唯一id
     */
    private Integer urlSpeedUpId;

    /**
     * url或ip
     */
    private String url;

    /**
     * ping指标参考值
     */
    private Integer referencePing;

    /**
     * lost指标参考值
     */
    private Integer referenceLost;

    /**
     * trace性能参考值
     */
    private Integer referenceTrace;

    /**
     * 速率单位(B,KB,MB,GB,TB,PB)
     */
    private String speedUnit;

    /**
     * 单价
     */
    private BigDecimal price;

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
     * 单价单位(MONTH-月,YEAR-年)
     */
    private String priceUnit;

    /**
     * 网站英文名称
     */
    private String urlEnName;

    /**
     * 网站中文名称
     */
    private String urlCnName;

    /**
     * 网站分类
     */
    private String urlGroup;

    /**
     * URL加速产品ID
     */
    private Integer productId;
    private static final long serialVersionUID = 1L;

    public Integer getUrlSpeedUpId() {
        return urlSpeedUpId;
    }

    public void setUrlSpeedUpId(Integer urlSpeedUpId) {
        this.urlSpeedUpId = urlSpeedUpId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getReferencePing() {
        return referencePing;
    }

    public void setReferencePing(Integer referencePing) {
        this.referencePing = referencePing;
    }

    public Integer getReferenceLost() {
        return referenceLost;
    }

    public void setReferenceLost(Integer referenceLost) {
        this.referenceLost = referenceLost;
    }

    public Integer getReferenceTrace() {
        return referenceTrace;
    }

    public void setReferenceTrace(Integer referenceTrace) {
        this.referenceTrace = referenceTrace;
    }

    public String getSpeedUnit() {
        return speedUnit;
    }

    public void setSpeedUnit(String speedUnit) {
        this.speedUnit = speedUnit;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    public String getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(String priceUnit) {
        this.priceUnit = priceUnit;
    }

    public String getUrlEnName() {
        return urlEnName;
    }

    public void setUrlEnName(String urlEnName) {
        this.urlEnName = urlEnName;
    }

    public String getUrlCnName() {
        return urlCnName;
    }

    public void setUrlCnName(String urlCnName) {
        this.urlCnName = urlCnName;
    }

    public String getUrlGroup() {
        return urlGroup;
    }

    public void setUrlGroup(String urlGroup) {
        this.urlGroup = urlGroup;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}