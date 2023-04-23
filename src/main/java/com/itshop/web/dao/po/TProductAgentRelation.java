package com.itshop.web.dao.po;

import java.io.Serializable;
import java.util.Date;

/**
 * t_product_agent_relation
 * @author 
 */
public class TProductAgentRelation implements Serializable {
    /**
     * 代理商代理产品关系id
     */
    private Integer productAgentRelationId;

    /**
     * 产品id
     */
    private Integer productId;

    /**
     * 组织id
     */
    private Integer orgId;

    /**
     * 是否代理(0-未删除,1-删除)
     */
    private Boolean isAgent;

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

    public Integer getProductAgentRelationId() {
        return productAgentRelationId;
    }

    public void setProductAgentRelationId(Integer productAgentRelationId) {
        this.productAgentRelationId = productAgentRelationId;
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

    public Boolean getIsAgent() {
        return isAgent;
    }

    public void setIsAgent(Boolean isAgent) {
        this.isAgent = isAgent;
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