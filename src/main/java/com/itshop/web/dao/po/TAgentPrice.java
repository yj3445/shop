package com.itshop.web.dao.po;

import java.io.Serializable;
import java.util.Date;

/**
 * t_agent_price
 * @author 
 */
public class TAgentPrice implements Serializable {
    /**
     * 代理-定价序列id
     */
    private Integer agentPriceId;

    /**
     * 组织id
     */
    private Integer orgId;

    /**
     * 价格序列名称
     */
    private String priceName;

    /**
     * 价格序列说明
     */
    private String priceDesc;

    /**
     * 定价类型(1-代理价格,2-终端价格)
     */
    private Integer priceType;

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

    public Integer getAgentPriceId() {
        return agentPriceId;
    }

    public void setAgentPriceId(Integer agentPriceId) {
        this.agentPriceId = agentPriceId;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getPriceName() {
        return priceName;
    }

    public void setPriceName(String priceName) {
        this.priceName = priceName;
    }

    public String getPriceDesc() {
        return priceDesc;
    }

    public void setPriceDesc(String priceDesc) {
        this.priceDesc = priceDesc;
    }

    public Integer getPriceType() {
        return priceType;
    }

    public void setPriceType(Integer priceType) {
        this.priceType = priceType;
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
        TAgentPrice other = (TAgentPrice) that;
        return (this.getAgentPriceId() == null ? other.getAgentPriceId() == null : this.getAgentPriceId().equals(other.getAgentPriceId()))
            && (this.getOrgId() == null ? other.getOrgId() == null : this.getOrgId().equals(other.getOrgId()))
            && (this.getPriceName() == null ? other.getPriceName() == null : this.getPriceName().equals(other.getPriceName()))
            && (this.getPriceDesc() == null ? other.getPriceDesc() == null : this.getPriceDesc().equals(other.getPriceDesc()))
            && (this.getPriceType() == null ? other.getPriceType() == null : this.getPriceType().equals(other.getPriceType()))
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
        result = prime * result + ((getAgentPriceId() == null) ? 0 : getAgentPriceId().hashCode());
        result = prime * result + ((getOrgId() == null) ? 0 : getOrgId().hashCode());
        result = prime * result + ((getPriceName() == null) ? 0 : getPriceName().hashCode());
        result = prime * result + ((getPriceDesc() == null) ? 0 : getPriceDesc().hashCode());
        result = prime * result + ((getPriceType() == null) ? 0 : getPriceType().hashCode());
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
        sb.append(", agentPriceId=").append(agentPriceId);
        sb.append(", orgId=").append(orgId);
        sb.append(", priceName=").append(priceName);
        sb.append(", priceDesc=").append(priceDesc);
        sb.append(", priceType=").append(priceType);
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