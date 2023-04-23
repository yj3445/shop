package com.itshop.web.dao.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * t_bill
 * @author 
 */
public class TBill implements Serializable {
    private Integer billId;

    /**
     * 账单编码
     */
    private String billCode;

    /**
     * 所属结算单ID
     */
    private Integer statementId;

    /**
     * 所属组织ID
     */
    private Integer orgId;

    /**
     * 账单年月
     */
    private String billYearMonth;

    /**
     * 出账日期
     */
    private Date outAccountDay;

    /**
     * 订单创建者ID
     */
    private Integer userId;

    /**
     * 订单ID
     */
    private Integer orderId;

    /**
     * 变更前记录id
     */
    private Integer changeId;

    /**
     * 订单主表名称
     */
    private String orderTableName;

    /**
     * 产品ID
     */
    private Integer productId;

    /**
     * 服务起始时间
     */
    private Date serviceStartTime;

    /**
     * 服务结束时间
     */
    private Date serviceEndTime;

    /**
     * 服务商金额
     */
    private BigDecimal providerPayAmount;

    /**
     * 一级代理金额
     */
    private BigDecimal agentLevel1PayAmount;

    /**
     * 二级代理金额
     */
    private BigDecimal agentLevel2PayAmount;

    /**
     * 三级代理金额
     */
    private BigDecimal agentLevel3PayAmount;

    /**
     * 终端用户金额
     */
    private BigDecimal endUserPayAmount;

    /**
     * 账单创建时间
     */
    private Date createTime;

    /**
     * 逻辑删除
     */
    private Boolean isDeleted;

    /**
     * 账单状态
     */
    private Integer billStatus;

    /**
     * 更新时间
     */
    private Date modifiedTime;

    /**
     * 全路径
     */
    private String orgFullPath;

    private static final long serialVersionUID = 1L;

    public Integer getBillId() {
        return billId;
    }

    public void setBillId(Integer billId) {
        this.billId = billId;
    }

    public String getBillCode() {
        return billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    public Integer getStatementId() {
        return statementId;
    }

    public void setStatementId(Integer statementId) {
        this.statementId = statementId;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getBillYearMonth() {
        return billYearMonth;
    }

    public void setBillYearMonth(String billYearMonth) {
        this.billYearMonth = billYearMonth;
    }

    public Date getOutAccountDay() {
        return outAccountDay;
    }

    public void setOutAccountDay(Date outAccountDay) {
        this.outAccountDay = outAccountDay;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getChangeId() {
        return changeId;
    }

    public void setChangeId(Integer changeId) {
        this.changeId = changeId;
    }

    public String getOrderTableName() {
        return orderTableName;
    }

    public void setOrderTableName(String orderTableName) {
        this.orderTableName = orderTableName;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Date getServiceStartTime() {
        return serviceStartTime;
    }

    public void setServiceStartTime(Date serviceStartTime) {
        this.serviceStartTime = serviceStartTime;
    }

    public Date getServiceEndTime() {
        return serviceEndTime;
    }

    public void setServiceEndTime(Date serviceEndTime) {
        this.serviceEndTime = serviceEndTime;
    }

    public BigDecimal getProviderPayAmount() {
        return providerPayAmount;
    }

    public void setProviderPayAmount(BigDecimal providerPayAmount) {
        this.providerPayAmount = providerPayAmount;
    }

    public BigDecimal getAgentLevel1PayAmount() {
        return agentLevel1PayAmount;
    }

    public void setAgentLevel1PayAmount(BigDecimal agentLevel1PayAmount) {
        this.agentLevel1PayAmount = agentLevel1PayAmount;
    }

    public BigDecimal getAgentLevel2PayAmount() {
        return agentLevel2PayAmount;
    }

    public void setAgentLevel2PayAmount(BigDecimal agentLevel2PayAmount) {
        this.agentLevel2PayAmount = agentLevel2PayAmount;
    }

    public BigDecimal getAgentLevel3PayAmount() {
        return agentLevel3PayAmount;
    }

    public void setAgentLevel3PayAmount(BigDecimal agentLevel3PayAmount) {
        this.agentLevel3PayAmount = agentLevel3PayAmount;
    }

    public BigDecimal getEndUserPayAmount() {
        return endUserPayAmount;
    }

    public void setEndUserPayAmount(BigDecimal endUserPayAmount) {
        this.endUserPayAmount = endUserPayAmount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(Integer billStatus) {
        this.billStatus = billStatus;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getOrgFullPath() {
        return orgFullPath;
    }

    public void setOrgFullPath(String orgFullPath) {
        this.orgFullPath = orgFullPath;
    }
}