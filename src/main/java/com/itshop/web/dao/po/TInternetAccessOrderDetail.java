package com.itshop.web.dao.po;

import java.io.Serializable;

/**
 * t_internet_access_order_detail
 *
 * @author liufenglong
 * @date 2022/5/7
 */
public class TInternetAccessOrderDetail implements Serializable {
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