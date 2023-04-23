package com.itshop.web.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 应用加速(URL加速及其他类型加速)相结合订单响应类
 *
 * @author liufenglong
 * @date 2022/6/13
 */
@Data
public class ApplicationSpeedUpUnionOrderResp  implements Serializable {
    /**
     * 订单ID
     */
    private Integer orderId;
    /**
     * 订单编号
     */
    private String orderNo;
    /**
     * 订单开始时间
     */
    private Date startTime;
    /**
     * 订单结束时间
     */
    private Date endTime;
    /**
     * 产品ID
     */
    private Integer productId;
    /**
     * 产品编码
     */
    private String productCode;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 创建人ID
     */
    private Integer createrBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新人ID
     */
    private Integer modifiedBy;
    /**
     * 更新时间
     */
    private Date modifiedTime;
    /**
     * 主线路订单ID
     */
    private Integer mainLineOrderId;

    /**
     * 主线路产品ID
     */
    private Integer mainLineProductId;

    /**
     * 主线路产品编码
     */
    private String mainLineProductCode;
    /**
     * 主线路产品名称
     */
    private String mainLineProductName;
    /**
     * 主线路带宽
     */
    private Integer mainLineBroadBand;
    /**
     * 主线路出口
     */
    private Integer mainLineExport;

    /**
     *   操作人(账户)
     */
    private String modifiedUserId;

    /**
     * 操作人(用户名)
     */
    private String modifiedUserName;
}
