package com.itshop.web.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 应用加速-订单明细-响应
 *
 * @author liufenglong
 * @date 2022/5/7
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ApplicationSpeedUpOrderResp extends OrderResp implements Serializable {

    /**
     * 应用加速-订单明细-响应
     *
     * @author liufenglong
     * @date 2022/5/7
     */
    @Data
    public static class OrderDetail {
        /**
         * 订单明细id
         */
        private Integer orderDetailId;

        /**
         * 订单id
         */
        private Integer orderId;

        /**
         * 单价
         */
        private BigDecimal price;

        /**
         * url
         */
        private String url;

        /**
         * 速率
         */
        private Integer speed;

        /**
         * 时间计量单位
         */
        private Integer unit;

        /**
         * 使用时长
         */
        private Integer duration;

        /**
         * 开始时间
         */
        private Date startTime;

        /**
         * 结束时间
         */
        private Date endTime;

        /**
         * url加速唯一id
         */
        private Integer urlSpeedUpId;

        /**
         * 速率单位(b,kb,mb,gb,tb,pb)
         */
        private String speedUnit;
    }

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
     * 订单明细信息
     */
    private List<OrderDetail> orderDetails;

    /**
     * 互联网接入-订单类型（本地固定返回 “CUSTOM”）
     */
    private String internetAccessOrderType;

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

    /**
     * 品类ID
     */
    private Integer categoryId;

    /**
     * 品类编码
     */
    private String categoryCode;

    /**
     * 品类名称
     */
    private String categoryName;

    /**
     * 品类级别(1,2,3,4...)
     */
    private Integer categoryLevel;

}
