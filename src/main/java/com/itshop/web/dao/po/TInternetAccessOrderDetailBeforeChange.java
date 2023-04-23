package com.itshop.web.dao.po;

import java.io.Serializable;

/**
 * t_internet_access_order_detail_before_change
 * @author 
 */
public class TInternetAccessOrderDetailBeforeChange implements Serializable {
    /**
     * 变更记录明细id
     */
    private Integer changeDetailId;

    /**
     * 变更记录id
     */
    private Integer changeId;

    /**
     * 订单明细id
     */
    private Integer orderDetailId;

    /**
     * 订单id
     */
    private Integer orderId;

    /**
     * 自定义应用实例组-线路指标id
     */
    private Integer applicationLineIndicatorsId;

    private static final long serialVersionUID = 1L;

    public Integer getChangeDetailId() {
        return changeDetailId;
    }

    public void setChangeDetailId(Integer changeDetailId) {
        this.changeDetailId = changeDetailId;
    }

    public Integer getChangeId() {
        return changeId;
    }

    public void setChangeId(Integer changeId) {
        this.changeId = changeId;
    }

    public Integer getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Integer orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getApplicationLineIndicatorsId() {
        return applicationLineIndicatorsId;
    }

    public void setApplicationLineIndicatorsId(Integer applicationLineIndicatorsId) {
        this.applicationLineIndicatorsId = applicationLineIndicatorsId;
    }
}