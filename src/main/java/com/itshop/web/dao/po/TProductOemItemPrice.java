package com.itshop.web.dao.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * t_product_oem_item_price
 * @author 
 */
public class TProductOemItemPrice implements Serializable {
    /**
     * OEM产品项价格id
     */
    private Integer productOemItemPriceId;

    /**
     * OEM产品id
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
     * 产品项ID
     */
    private Integer productItemId;

    /**
     * 价格序列ID
     */
    private Integer agentPriceId;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 价格单位(YEAR,MONTH,DAY,HOUR)
     */
    private String priceUnit;

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

    private static final long serialVersionUID = 1L;

    public Integer getProductOemItemPriceId() {
        return productOemItemPriceId;
    }

    public void setProductOemItemPriceId(Integer productOemItemPriceId) {
        this.productOemItemPriceId = productOemItemPriceId;
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

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getProductItemId() {
        return productItemId;
    }

    public void setProductItemId(Integer productItemId) {
        this.productItemId = productItemId;
    }

    public Integer getAgentPriceId() {
        return agentPriceId;
    }

    public void setAgentPriceId(Integer agentPriceId) {
        this.agentPriceId = agentPriceId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(String priceUnit) {
        this.priceUnit = priceUnit;
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

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        TProductOemItemPrice other = (TProductOemItemPrice) that;
        return (this.getProductOemItemPriceId() == null ? other.getProductOemItemPriceId() == null : this.getProductOemItemPriceId().equals(other.getProductOemItemPriceId()))
            && (this.getProductOemId() == null ? other.getProductOemId() == null : this.getProductOemId().equals(other.getProductOemId()))
            && (this.getProductId() == null ? other.getProductId() == null : this.getProductId().equals(other.getProductId()))
            && (this.getOrgId() == null ? other.getOrgId() == null : this.getOrgId().equals(other.getOrgId()))
            && (this.getProductItemId() == null ? other.getProductItemId() == null : this.getProductItemId().equals(other.getProductItemId()))
            && (this.getAgentPriceId() == null ? other.getAgentPriceId() == null : this.getAgentPriceId().equals(other.getAgentPriceId()))
            && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
            && (this.getPriceUnit() == null ? other.getPriceUnit() == null : this.getPriceUnit().equals(other.getPriceUnit()))
            && (this.getCreaterBy() == null ? other.getCreaterBy() == null : this.getCreaterBy().equals(other.getCreaterBy()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getModifiedBy() == null ? other.getModifiedBy() == null : this.getModifiedBy().equals(other.getModifiedBy()))
            && (this.getModifiedTime() == null ? other.getModifiedTime() == null : this.getModifiedTime().equals(other.getModifiedTime()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getProductOemItemPriceId() == null) ? 0 : getProductOemItemPriceId().hashCode());
        result = prime * result + ((getProductOemId() == null) ? 0 : getProductOemId().hashCode());
        result = prime * result + ((getProductId() == null) ? 0 : getProductId().hashCode());
        result = prime * result + ((getOrgId() == null) ? 0 : getOrgId().hashCode());
        result = prime * result + ((getProductItemId() == null) ? 0 : getProductItemId().hashCode());
        result = prime * result + ((getAgentPriceId() == null) ? 0 : getAgentPriceId().hashCode());
        result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
        result = prime * result + ((getPriceUnit() == null) ? 0 : getPriceUnit().hashCode());
        result = prime * result + ((getCreaterBy() == null) ? 0 : getCreaterBy().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getModifiedBy() == null) ? 0 : getModifiedBy().hashCode());
        result = prime * result + ((getModifiedTime() == null) ? 0 : getModifiedTime().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", productOemItemPriceId=").append(productOemItemPriceId);
        sb.append(", productOemId=").append(productOemId);
        sb.append(", productId=").append(productId);
        sb.append(", orgId=").append(orgId);
        sb.append(", productItemId=").append(productItemId);
        sb.append(", agentPriceId=").append(agentPriceId);
        sb.append(", price=").append(price);
        sb.append(", priceUnit=").append(priceUnit);
        sb.append(", createrBy=").append(createrBy);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifiedBy=").append(modifiedBy);
        sb.append(", modifiedTime=").append(modifiedTime);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}