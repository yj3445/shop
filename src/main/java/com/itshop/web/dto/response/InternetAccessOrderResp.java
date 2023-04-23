package com.itshop.web.dto.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 互联网接入-自定义-订单明细-响应
 *
 * @author liufenglong
 * @date 2022/5/7
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class InternetAccessOrderResp extends OrderResp implements Serializable {

    /**
     * 互联网接入-自定义-订单明细-响应
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
         * 自定义应用实例组-线路指标id
         */
        private Integer applicationLineIndicatorsId;
    }

    /**
     * 变更记录id
     */
    private Integer changeId;
    
    /**
     * 订单ID
     */
    private Integer orderId;

    /**
     * 品类编码
     */
    private String orderNo;

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
     * 订单状态
     */
    private Integer status;

    /**
     * 订单状态描述
     */
    private String statusDesc;

    /**
     * 基础价格
     */
    private BigDecimal basicPrice;

    /**
     * 出口-折扣价
     */
    private BigDecimal exportDiscountPrice;

    /**
     * 合同期限-折扣价
     */
    private BigDecimal contractPeriodDiscountPrice;

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
     * 业务编号
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
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

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

    /**
     * 组织id
     */
    private Integer orgId;

    /**
     * 组织全称
     */
    private String orgFullPath;
    /**
     * 审核人
     */
    private Integer auditBy;
    /**
     * 审核时间
     */
    private Date auditTime;
    /**
     * 审核通过(备注)
     */
    private String auditPassRemark;
    /**
     * 审核拒绝(备注)
     */
    private String auditRefusedRemark;

    /**
     * 审核价格人
     */
    private Integer auditPriceBy;
    /**
     * 审核价格
     */
    private BigDecimal auditPrice;


    /**
     * 安装地址
     */
    private String installAddress;

    /**
     * 线路名称
     */
    private String lineName;

    /**
     * 创建人(英文名称)
     */
    private String createrUserId;

    /**
     * 创建人(用户名)
     */
    private String createrUserName;

    /**
     * 操作人(账户)
     */
    private String modifiedUserId;

    /**
     * 操作人(用户名)
     */
    private String modifiedUserName;

    /**
     * 审核人（账户)
     */
    private String auditUserId;

    /**
     * 审核人(用户名)
     */
    private String auditUserName;

    /**
     * 改价人（账户）
     */
    private String auditPriceUserId;

    /**
     * 改价人(用户名)
     */
    private String auditPriceUserName;

    /**
     * 交付时间
     */
    private Date deliveryTime;
}
