package com.itshop.web.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 价格序列信息
 *
 * @author liufenglong
 * @date 2022/7/23
 */
@Data
public class AgentPriceResp  implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 代理-定价序列id
     */
    private Integer agentPriceId;

    /**
     * 组织id
     */
    private Integer orgId;

    /**
     * 价格序列名称
     */
    private String priceName;

    /**
     * 价格序列说明
     */
    private String priceDesc;

    /**
     * 定价类型(1-代理价格,2-终端价格)
     */
    private Integer priceType;

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
}
