package com.itshop.web.dao.po;

import java.io.Serializable;

/**
 * t_agent_price_org
 * @author 
 */
public class TAgentPriceOrg implements Serializable {
    /**
     * 代理-定价序列-组织成员id
     */
    private Integer agentPriceOrgId;

    /**
     * 代理-定价序列id
     */
    private Integer agentPriceId;

    /**
     * 组织id
     */
    private Integer targetOrgId;

    private static final long serialVersionUID = 1L;

    public Integer getAgentPriceOrgId() {
        return agentPriceOrgId;
    }

    public void setAgentPriceOrgId(Integer agentPriceOrgId) {
        this.agentPriceOrgId = agentPriceOrgId;
    }

    public Integer getAgentPriceId() {
        return agentPriceId;
    }

    public void setAgentPriceId(Integer agentPriceId) {
        this.agentPriceId = agentPriceId;
    }

    public Integer getTargetOrgId() {
        return targetOrgId;
    }

    public void setTargetOrgId(Integer targetOrgId) {
        this.targetOrgId = targetOrgId;
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
        TAgentPriceOrg other = (TAgentPriceOrg) that;
        return (this.getAgentPriceOrgId() == null ? other.getAgentPriceOrgId() == null : this.getAgentPriceOrgId().equals(other.getAgentPriceOrgId()))
            && (this.getAgentPriceId() == null ? other.getAgentPriceId() == null : this.getAgentPriceId().equals(other.getAgentPriceId()))
            && (this.getTargetOrgId() == null ? other.getTargetOrgId() == null : this.getTargetOrgId().equals(other.getTargetOrgId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getAgentPriceOrgId() == null) ? 0 : getAgentPriceOrgId().hashCode());
        result = prime * result + ((getAgentPriceId() == null) ? 0 : getAgentPriceId().hashCode());
        result = prime * result + ((getTargetOrgId() == null) ? 0 : getTargetOrgId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", agentPriceOrgId=").append(agentPriceOrgId);
        sb.append(", agentPriceId=").append(agentPriceId);
        sb.append(", targetOrgId=").append(targetOrgId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}