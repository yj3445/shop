package com.itshop.web.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * 互联网接入(升级服务)相结合订单响应类
 *
 * @author liufenglong
 * @date 2022/6/13
 */
@Data
public class InternetAccessUnionOrderResp  implements Serializable {
    /**
     * 变更记录ID
     */
    private Integer changeId;

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
     * 操作人(账户)
     */
    private String modifiedUserId;

    /**
     * 操作人(用户名)
     */
    private String modifiedUserName;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 订单金额
     */
    private BigDecimal totalPrice;

    /**
     * 所属用户(英文名称)
     */
    private String createrUserId;

    /**
     * 所属用户(中文名称)
     */
    private String createrUserName;

    /**
     * 组织ID
     */
    private Integer orgId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 订单状态描述
     */
    private String statusDesc;

    /**
     * 审核时间
     */
    private Date auditTime;

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
     * 审核金额
     */
    private BigDecimal auditPrice;

    /**
     * 变更状态
     */
    private Integer changeStatus;

    /**
     * 变更状态描述
     */
    private String changeStatusDesc;

    /**
     * 父组织ID
     */
    private Integer parentOrgId;

    /**
     * 当前用户是否可以审批价格
     */
    private Boolean canAuditPrice;
}
