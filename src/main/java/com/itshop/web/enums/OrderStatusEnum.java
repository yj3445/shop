package com.itshop.web.enums;

import org.apache.logging.log4j.util.Strings;

import java.util.Objects;

/**
 * 订单状态 枚举
 */
public enum OrderStatusEnum {
    NOT_AUDIT(10,"待审核"),
    AUDIT_PASS(20,"审核通过"),
    AUDIT_REFUSED(30,"审核拒绝");

    private Integer value;
    private String desc;

    OrderStatusEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static String getDescByValue(Integer value) {
        if (value != null) {
            for (OrderStatusEnum orgType : OrderStatusEnum.values()) {
                if (Objects.equals(value, orgType.value)) {
                    return orgType.desc;
                }
            }
        }
        return Strings.EMPTY;
    }
}
