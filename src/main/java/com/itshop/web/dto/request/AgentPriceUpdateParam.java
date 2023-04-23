package com.itshop.web.dto.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 价格序列
 *
 * @author liufenglong
 * @date 2022/7/23
 */
@Data
public class AgentPriceUpdateParam implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 代理-定价序列id
     */
    private Integer agentPriceId;

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
}
