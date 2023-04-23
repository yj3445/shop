package com.itshop.web.dao.po;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * t_order_amount
 * @author 
 */
public class TOrderAmount implements Serializable {
    private Integer orderAmountId;

    /**
     * 订单id
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
     * 产品id
     */
    private Integer productId;

    /**
     * OEM产品id
     */
    private Integer productOemId;

    /**
     * 组织id
     */
    private Integer orgId;

    /**
     * 组织全路径
     */
    private String orgFullPath;

    /**
     * 终端用户OEM产品项价格id
     */
    private Integer endUserOemItemPriceId;

    /**
     * 终端用户单价
     */
    private BigDecimal endUserPrice;

    /**
     * 终端用户价格单位(YEAR,MONTH,DAY,HOUR)
     */
    private String endUserPriceUnit;

    /**
     * 原始价格
     */
    private BigDecimal basicPrice;

    /**
     * 出口-折扣规则ID
     */
    private Integer exportDiscountRulesId;

    /**
     * 出口-折扣率
     */
    private Integer exportDiscountRate;

    /**
     * 出口-折扣价
     */
    private BigDecimal exportDiscountPrice;

    /**
     * 合同期限-折扣规则ID
     */
    private Integer contractPeriodDiscountRulesId;

    /**
     * 合同期限-折扣率
     */
    private Integer contractPeriodDiscountRate;

    /**
     * 合同期限-折扣价
     */
    private BigDecimal contractPeriodDiscountPrice;

    /**
     * 缴费周期-折扣规则ID
     */
    private Integer paymentCycleDiscountRulesId;

    /**
     * 缴费周期-折扣率
     */
    private Integer paymentCycleDiscountRate;

    /**
     * 缴费周期-折扣价
     */
    private BigDecimal paymentCycleDiscountPrice;

    /**
     * 付费方式-折扣规则ID
     */
    private Integer paymentMethodDiscountRulesId;

    /**
     * 付费方式-折扣率
     */
    private Integer paymentMethodDiscountRate;

    /**
     * 付费方式-折扣价
     */
    private BigDecimal paymentMethodDiscountPrice;

    /**
     * 订单金额
     */
    private BigDecimal totalPrice;

    /**
     * 订单成交金额
     */
    private BigDecimal salesTotalPrice;

    /**
     * 供应商订单金额
     */
    private BigDecimal providerTotalPrice;

    /**
     * 一级代理商OEM产品项价格id
     */
    private Integer agentLevel1OemItemPriceId;

    /**
     * 一级代理商单价
     */
    private BigDecimal agentLevel1Price;

    /**
     * 一级代理商价格单位(YEAR,MONTH,DAY,HOUR)
     */
    private String agentLevel1PriceUnit;

    /**
     * 一级代理商订单金额
     */
    private BigDecimal agentLevel1TotalPrice;

    /**
     * 二级代理商OEM产品项价格id
     */
    private Integer agentLevel2OemItemPriceId;

    /**
     * 二级代理商单价
     */
    private BigDecimal agentLevel2Price;

    /**
     * 二级代理商价格单位(YEAR,MONTH,DAY,HOUR)
     */
    private String agentLevel2PriceUnit;

    /**
     * 二级代理商订单金额
     */
    private BigDecimal agentLevel2TotalPrice;

    /**
     * 三级代理商OEM产品项价格id
     */
    private Integer agentLevel3OemItemPriceId;

    /**
     * 三级代理商单价
     */
    private BigDecimal agentLevel3Price;

    /**
     * 三级代理商价格单位(YEAR,MONTH,DAY,HOUR)
     */
    private String agentLevel3PriceUnit;

    /**
     * 三级代理商订单金额
     */
    private BigDecimal agentLevel3TotalPrice;

    private static final long serialVersionUID = 1L;

    public Integer getOrderAmountId() {
        return orderAmountId;
    }

    public void setOrderAmountId(Integer orderAmountId) {
        this.orderAmountId = orderAmountId;
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

    public Integer getProductOemId() {
        return productOemId;
    }

    public void setProductOemId(Integer productOemId) {
        this.productOemId = productOemId;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getOrgFullPath() {
        return orgFullPath;
    }

    public void setOrgFullPath(String orgFullPath) {
        this.orgFullPath = orgFullPath;
    }

    public Integer getEndUserOemItemPriceId() {
        return endUserOemItemPriceId;
    }

    public void setEndUserOemItemPriceId(Integer endUserOemItemPriceId) {
        this.endUserOemItemPriceId = endUserOemItemPriceId;
    }

    public BigDecimal getEndUserPrice() {
        return endUserPrice;
    }

    public void setEndUserPrice(BigDecimal endUserPrice) {
        this.endUserPrice = endUserPrice;
    }

    public String getEndUserPriceUnit() {
        return endUserPriceUnit;
    }

    public void setEndUserPriceUnit(String endUserPriceUnit) {
        this.endUserPriceUnit = endUserPriceUnit;
    }

    public BigDecimal getBasicPrice() {
        return basicPrice;
    }

    public void setBasicPrice(BigDecimal basicPrice) {
        this.basicPrice = basicPrice;
    }

    public Integer getExportDiscountRulesId() {
        return exportDiscountRulesId;
    }

    public void setExportDiscountRulesId(Integer exportDiscountRulesId) {
        this.exportDiscountRulesId = exportDiscountRulesId;
    }

    public Integer getExportDiscountRate() {
        return exportDiscountRate;
    }

    public void setExportDiscountRate(Integer exportDiscountRate) {
        this.exportDiscountRate = exportDiscountRate;
    }

    public BigDecimal getExportDiscountPrice() {
        return exportDiscountPrice;
    }

    public void setExportDiscountPrice(BigDecimal exportDiscountPrice) {
        this.exportDiscountPrice = exportDiscountPrice;
    }

    public Integer getContractPeriodDiscountRulesId() {
        return contractPeriodDiscountRulesId;
    }

    public void setContractPeriodDiscountRulesId(Integer contractPeriodDiscountRulesId) {
        this.contractPeriodDiscountRulesId = contractPeriodDiscountRulesId;
    }

    public Integer getContractPeriodDiscountRate() {
        return contractPeriodDiscountRate;
    }

    public void setContractPeriodDiscountRate(Integer contractPeriodDiscountRate) {
        this.contractPeriodDiscountRate = contractPeriodDiscountRate;
    }

    public BigDecimal getContractPeriodDiscountPrice() {
        return contractPeriodDiscountPrice;
    }

    public void setContractPeriodDiscountPrice(BigDecimal contractPeriodDiscountPrice) {
        this.contractPeriodDiscountPrice = contractPeriodDiscountPrice;
    }

    public Integer getPaymentCycleDiscountRulesId() {
        return paymentCycleDiscountRulesId;
    }

    public void setPaymentCycleDiscountRulesId(Integer paymentCycleDiscountRulesId) {
        this.paymentCycleDiscountRulesId = paymentCycleDiscountRulesId;
    }

    public Integer getPaymentCycleDiscountRate() {
        return paymentCycleDiscountRate;
    }

    public void setPaymentCycleDiscountRate(Integer paymentCycleDiscountRate) {
        this.paymentCycleDiscountRate = paymentCycleDiscountRate;
    }

    public BigDecimal getPaymentCycleDiscountPrice() {
        return paymentCycleDiscountPrice;
    }

    public void setPaymentCycleDiscountPrice(BigDecimal paymentCycleDiscountPrice) {
        this.paymentCycleDiscountPrice = paymentCycleDiscountPrice;
    }

    public Integer getPaymentMethodDiscountRulesId() {
        return paymentMethodDiscountRulesId;
    }

    public void setPaymentMethodDiscountRulesId(Integer paymentMethodDiscountRulesId) {
        this.paymentMethodDiscountRulesId = paymentMethodDiscountRulesId;
    }

    public Integer getPaymentMethodDiscountRate() {
        return paymentMethodDiscountRate;
    }

    public void setPaymentMethodDiscountRate(Integer paymentMethodDiscountRate) {
        this.paymentMethodDiscountRate = paymentMethodDiscountRate;
    }

    public BigDecimal getPaymentMethodDiscountPrice() {
        return paymentMethodDiscountPrice;
    }

    public void setPaymentMethodDiscountPrice(BigDecimal paymentMethodDiscountPrice) {
        this.paymentMethodDiscountPrice = paymentMethodDiscountPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getSalesTotalPrice() {
        return salesTotalPrice;
    }

    public void setSalesTotalPrice(BigDecimal salesTotalPrice) {
        this.salesTotalPrice = salesTotalPrice;
    }

    public BigDecimal getProviderTotalPrice() {
        return providerTotalPrice;
    }

    public void setProviderTotalPrice(BigDecimal providerTotalPrice) {
        this.providerTotalPrice = providerTotalPrice;
    }

    public Integer getAgentLevel1OemItemPriceId() {
        return agentLevel1OemItemPriceId;
    }

    public void setAgentLevel1OemItemPriceId(Integer agentLevel1OemItemPriceId) {
        this.agentLevel1OemItemPriceId = agentLevel1OemItemPriceId;
    }

    public BigDecimal getAgentLevel1Price() {
        return agentLevel1Price;
    }

    public void setAgentLevel1Price(BigDecimal agentLevel1Price) {
        this.agentLevel1Price = agentLevel1Price;
    }

    public String getAgentLevel1PriceUnit() {
        return agentLevel1PriceUnit;
    }

    public void setAgentLevel1PriceUnit(String agentLevel1PriceUnit) {
        this.agentLevel1PriceUnit = agentLevel1PriceUnit;
    }

    public BigDecimal getAgentLevel1TotalPrice() {
        return agentLevel1TotalPrice;
    }

    public void setAgentLevel1TotalPrice(BigDecimal agentLevel1TotalPrice) {
        this.agentLevel1TotalPrice = agentLevel1TotalPrice;
    }

    public Integer getAgentLevel2OemItemPriceId() {
        return agentLevel2OemItemPriceId;
    }

    public void setAgentLevel2OemItemPriceId(Integer agentLevel2OemItemPriceId) {
        this.agentLevel2OemItemPriceId = agentLevel2OemItemPriceId;
    }

    public BigDecimal getAgentLevel2Price() {
        return agentLevel2Price;
    }

    public void setAgentLevel2Price(BigDecimal agentLevel2Price) {
        this.agentLevel2Price = agentLevel2Price;
    }

    public String getAgentLevel2PriceUnit() {
        return agentLevel2PriceUnit;
    }

    public void setAgentLevel2PriceUnit(String agentLevel2PriceUnit) {
        this.agentLevel2PriceUnit = agentLevel2PriceUnit;
    }

    public BigDecimal getAgentLevel2TotalPrice() {
        return agentLevel2TotalPrice;
    }

    public void setAgentLevel2TotalPrice(BigDecimal agentLevel2TotalPrice) {
        this.agentLevel2TotalPrice = agentLevel2TotalPrice;
    }

    public Integer getAgentLevel3OemItemPriceId() {
        return agentLevel3OemItemPriceId;
    }

    public void setAgentLevel3OemItemPriceId(Integer agentLevel3OemItemPriceId) {
        this.agentLevel3OemItemPriceId = agentLevel3OemItemPriceId;
    }

    public BigDecimal getAgentLevel3Price() {
        return agentLevel3Price;
    }

    public void setAgentLevel3Price(BigDecimal agentLevel3Price) {
        this.agentLevel3Price = agentLevel3Price;
    }

    public String getAgentLevel3PriceUnit() {
        return agentLevel3PriceUnit;
    }

    public void setAgentLevel3PriceUnit(String agentLevel3PriceUnit) {
        this.agentLevel3PriceUnit = agentLevel3PriceUnit;
    }

    public BigDecimal getAgentLevel3TotalPrice() {
        return agentLevel3TotalPrice;
    }

    public void setAgentLevel3TotalPrice(BigDecimal agentLevel3TotalPrice) {
        this.agentLevel3TotalPrice = agentLevel3TotalPrice;
    }
}