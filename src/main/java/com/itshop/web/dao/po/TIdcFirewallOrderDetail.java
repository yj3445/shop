package com.itshop.web.dao.po;

import java.io.Serializable;

/**
 * t_idc_firewall_order_detail
 * @author 
 */
public class TIdcFirewallOrderDetail implements Serializable {
    /**
     * 防火墙订单明细id
     */
    private Integer orderDetailId;

    /**
     * 防火墙订单订单id
     */
    private Integer orderId;

    /**
     * 端口
     */
    private Integer port;

    /**
     * 上行/下行
     * 0-out-上行(内部访问外部);
     * 1-in-下行(外部访问内部)
     */
    private Integer inOrOut;

    /**
     * 协议类型
     * (Any,TCP,UDP,PPP)
     */
    private String protocol;

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

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getInOrOut() {
        return inOrOut;
    }

    public void setInOrOut(Integer inOrOut) {
        this.inOrOut = inOrOut;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
}