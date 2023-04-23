package com.itshop.web.dto.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 订单流量监控
 */
@Data
public class OrderMonitorQueryParam  implements Serializable {
    /**
     * 订单id
     */
    private Integer orderId;

    /**
     * 产品id
     */
    private Integer productId;

    /**
     * 起始时间 yyyy-MM-dd HH:mm:ss
     */
    private String start;

    /**
     * 结束时间 yyyy-MM-dd HH:mm:ss
     */
    private String end;
}
