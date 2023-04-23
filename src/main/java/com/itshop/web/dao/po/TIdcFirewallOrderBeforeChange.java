package com.itshop.web.dao.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * t_idc_firewall_order_before_change
 * @author 
 */
public class TIdcFirewallOrderBeforeChange implements Serializable {
    /**
     * 变更记录id
     */
    private Integer changeId;

    /**
     * 防火墙订单id
     */
    private Integer orderId;

    /**
     * 订单单号
     */
    private String orderNo;

    /**
     * 启用标识(0-关闭,1-启用)
     */
    private Boolean open;

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
     * 总价
     */
    private BigDecimal totalPrice;

    /**
     * 商品id
     */
    private Integer productId;

    private static final long serialVersionUID = 1L;

    public Integer getChangeId() {
        return changeId;
    }

    public void setChangeId(Integer changeId) {
        this.changeId = changeId;
    }

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

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
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
}