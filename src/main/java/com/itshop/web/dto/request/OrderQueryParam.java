package com.itshop.web.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 订单查询参数
 */
@Data
@ApiModel(value = "订单-查询-请求参数")
public class OrderQueryParam  implements Serializable {
    /**
     * 创建人ID
     */
    @ApiModelProperty(value = "创建人ID")
    private Integer createrBy;

    /**
     * 订单日期范围
     * <p>
     * all-全部
     * lately-最近
     * history-历史
     * toAudit-待审核
     * audited-已审核
     */
    @ApiModelProperty(value = "订单日期范围")
    private String dateRange;

    /**
     * 组织ID
     */
    @ApiModelProperty(value = "组织ID")
    private Integer orgId;

    /**
     * 全路径
     */
    @ApiModelProperty(value = "组织全路径")
    private String orgFullPath;
}
