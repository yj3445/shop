package com.itshop.web.enums;

import org.apache.logging.log4j.util.Strings;

import java.util.Objects;

/**
 * 付款方式
 */
public enum PaymentMethodEnum {
    PREPAID(1,"预付"),
    AFTER_PAYING(2,"后付");

    private Integer code;
    private String value;


    PaymentMethodEnum(Integer code,String value) {
        this.code= code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }


    public static String getValueByCode(Integer code) {
        if (code != null) {
            for (PaymentMethodEnum orgType : PaymentMethodEnum.values()) {
                if (Objects.equals(code, orgType.code)) {
                    return orgType.value;
                }
            }
        }
        return Strings.EMPTY;
    }
}

