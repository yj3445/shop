package com.itshop.web.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 其他类-应用加速-订单响应
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ApplicationSpeedUpOtherOrderResp extends OrderResp  implements Serializable {
    /**
     * 订单ID
     */
    private Integer orderId;

    /**
     * 订单单号
     */
    private String orderNo;

    /**
     * 产品id
     */
    private Integer productId;

    /**
     * 字典id
     */
    private Integer dictionaryId;

    /**
     * 互联网接入-订单id
     */
    private Integer internetAccessOrderId;

    /**
     * 带宽(单位:兆)
     */
    private Integer broadBand;

    /**
     * 时间单位(4-日,1-月,2-季,3-年)
     */
    private Integer unit;

    /**
     * 使用时长
     */
    private Integer duration;

    /**
     * 缴费周期(4-日,1月,2季,3年)
     */
    private Integer paymentCycle;

    /**
     * 付费方式(1预付,2后付)
     */
    private Integer paymentMethod;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 总价
     */
    private BigDecimal totalPrice;

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
     * 产品编码
     */
    private String productCode;

    /**
     * 产品名称
     */
    private String productName;

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
