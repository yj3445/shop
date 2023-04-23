package com.itshop.web.enums;

/**
 * 代理价格类型
 */
public enum AgentPriceType {
    AGENT_PRICE(1, "代理价格"),
    CUSTOMER_PRICE(2, "终端价格");
    private Integer code;
    private String desc;

    AgentPriceType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
