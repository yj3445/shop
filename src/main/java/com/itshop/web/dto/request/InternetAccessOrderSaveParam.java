package com.itshop.web.dto.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 互联网接入-自定义-订单-保存-请求参数
 *
 * @author liufenglong
 * @date 2022/5/7
 */
@Data
@ApiModel(value = "互联网接入-自定义-订单-保存-请求参数")
public class InternetAccessOrderSaveParam  implements Serializable {

    /**
     * 应用加速订单id
     */
    private Integer orderId;

    /**
     * 商品id
     */
    private Integer productId;

    /**
     * 带宽(单位:兆)
     */
    private Integer broadBand;

    /**
     * 出口(1智选,2联通,3电信,4移动)
     */
    private Integer export;

    /**
     * 合同期限(0,12,36)
     */
    private Integer contractPeriod;

    /**
     * 缴费周期(1月,2季,3年)
     */
    private Integer paymentCycle;

    /**
     * 付费方式(1预付,2后付)
     */
    private Integer paymentMethod;

    /**
     * ip地址数量选择方式(1智选,2自选)
     */
    private Integer ipNum;

    /**
     * ip地址数量
     */
    private Integer ipNumValue;

    /**
     * 应用实例组(1产品A,2产品B,3产品C,4自定义)
     */
    private Integer application;

    /**
     * 应用线路指标ID 集合
     * */
    private List<Integer> applicationLineIndicatorsIds;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 安装地址
     */
    private String installAddress;

    /**
     * 线路名称
     */
    private String lineName;
}
