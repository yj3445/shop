package com.itshop.web.dto.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 新增价格序列
 *
 * @author liufenglong
 * @date 2022/7/23
 */
@Data
public class AgentPriceInsertParam  implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 价格序列名称
     */
    private String priceName;

    /**
     * 价格序列说明
     */
    private String priceDesc;

    /**
     * 定价类型(代理价格,终端价格)
     */
    private Integer priceType;
}
