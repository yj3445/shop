package com.itshop.web.dto.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 互联网接入-升级服务-订单-保存-请求参数
 */
@Data
@ApiModel(value = "互联网接入-升级服务-订单-保存-请求参数")
public class InternetAccessUpdateServiceOrderSaveParam  implements Serializable {
    /**
     * 订单ID
     */
    private Integer orderId;

    /**
     * 产品id
     */
    private Integer productId;

    /**
     * 互联网接入-订单id
     */
    private Integer internetAccessOrderId;

    /**
     * 终端数量
     */
    private Integer terminalNum;

    /**
     * ip数量
     */
    private Integer ipNum;

    /**
     * ip备份(1-是,0-否)
     */
    private Boolean ipBackup;

    /**
     * ip方向(2联通,3电信,4移动)
     */
    private Integer ipExport;

    /**
     * 突发带宽(单位:兆)
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
