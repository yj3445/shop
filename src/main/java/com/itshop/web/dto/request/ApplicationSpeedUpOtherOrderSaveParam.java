package com.itshop.web.dto.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 其他类-应用加速-订单保存参数
 */
@Data
public class ApplicationSpeedUpOtherOrderSaveParam  implements Serializable {
    /**
     * 订单ID
     */
    private Integer orderId;

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
     * 创建人
     */
    private Integer createrBy;
}
