package com.itshop.web.dto.response;

import com.itshop.web.dto.request.IdcFireWallOrderSaveParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * idc-防火墙-订单明细-响应
 *
 * @author liufenglong
 * @date 2022/5/7
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class IdcFireWallOrderResp  extends OrderResp implements Serializable {

    /**
     * 防火墙订单id
     */
    private Integer orderId;

    /**
     * 订单单号
     */
    private String orderNo;

    /**
     * 启用标识(0-关闭,1-启用)
     */
    private Boolean open;

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
     * 互联网接入-订单类型（本地固定返回 “CUSTOM”）
     */
    private String internetAccessOrderType;

    /**
     * (互联网接入)业务编号
     */
    private String businessId;

    /**
     * 总价
     */
    private BigDecimal totalPrice;

    /**
     * 商品id
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
     * 端口集合
     */
    private List<IdcFireWallOrderSaveParam.PortInfo> portInfos;

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


    /**
     *   操作人(账户)
     */
    private String modifiedUserId;

    /**
     * 操作人(用户名)
     */
    private String modifiedUserName;

    /**
     * 订单开始时间
     */
    private Date startTime;
    /**
     * 订单结束时间
     */
    private Date endTime;
}
