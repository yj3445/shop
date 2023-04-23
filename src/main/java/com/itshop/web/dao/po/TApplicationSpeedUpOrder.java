package com.itshop.web.dao.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * t_application_speed_up_order
 * @author 
 */
public class TApplicationSpeedUpOrder implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 应用加速订单id
     */
    private Integer orderId;

    /**
     * 应用加速订单单号
     */
    private String orderNo;

    /**
     * 缴费周期(1月,2季,3年)
     */
    private Integer paymentCycle;

    /**
     * 付费方式(1预付,2后付)
     */
    private Integer paymentMethod;

    /**
     * 互联网接入-订单id
     */
    private Integer internetAccessOrderId;

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
     * 互联网接入-订单类型
     */
    private String InternetAccessOrderType;

    /**
     * 订单状态
     */
    private Integer status;

    /**
     * 基础价格
     */
    private BigDecimal basicPrice;

    /**
     * 缴费周期-折扣价
     */
    private BigDecimal paymentCycleDiscountPrice;

    /**
     * 付费方式-折扣价
     */
    private BigDecimal paymentMethodDiscountPrice;

    /**
     * 总价
     */
    private BigDecimal totalPrice;

    /**
     * 商品id
     */
    private Integer productId;


    /**
     * (互联网接入)业务编号
     */
    private String businessId;

    /**
     * 产品编码
     */
    private String productCode;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 主线路产品编码
     */
    private String mainLineProductCode;

    /**
     * 主线路产品名称
     */
    private String mainLineProductName;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getPaymentCycle() {
        return paymentCycle;
    }

    public void setPaymentCycle(Integer paymentCycle) {
        this.paymentCycle = paymentCycle;
    }

    public Integer getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Integer paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Integer getInternetAccessOrderId() {
        return internetAccessOrderId;
    }

    public void setInternetAccessOrderId(Integer internetAccessOrderId) {
        this.internetAccessOrderId = internetAccessOrderId;
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

    public String getInternetAccessOrderType() {
        return InternetAccessOrderType;
    }

    public void setInternetAccessOrderType(String internetAccessOrderType) {
        InternetAccessOrderType = internetAccessOrderType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getBasicPrice() {
        return basicPrice;
    }

    public void setBasicPrice(BigDecimal basicPrice) {
        this.basicPrice = basicPrice;
    }

    public BigDecimal getPaymentCycleDiscountPrice() {
        return paymentCycleDiscountPrice;
    }

    public void setPaymentCycleDiscountPrice(BigDecimal paymentCycleDiscountPrice) {
        this.paymentCycleDiscountPrice = paymentCycleDiscountPrice;
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

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
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

    public String getMainLineProductCode() {
        return mainLineProductCode;
    }

    public void setMainLineProductCode(String mainLineProductCode) {
        this.mainLineProductCode = mainLineProductCode;
    }

    public String getMainLineProductName() {
        return mainLineProductName;
    }

    public void setMainLineProductName(String mainLineProductName) {
        this.mainLineProductName = mainLineProductName;
    }
}