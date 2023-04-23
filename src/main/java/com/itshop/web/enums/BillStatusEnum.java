package com.itshop.web.enums;

/**
 * 账单状态枚举
 */
public enum BillStatusEnum {
    UN_OUT_ACCOUNT(10,"未出账"),
    OUT_OF_ACCOUNT(20,"已出账");

    private Integer value;
    private String desc;

    BillStatusEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
