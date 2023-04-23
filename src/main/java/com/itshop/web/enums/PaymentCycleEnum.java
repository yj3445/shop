package com.itshop.web.enums;

import org.apache.logging.log4j.util.Strings;

import java.util.Objects;

public enum PaymentCycleEnum {
    MONTH(1,"月"),
    QUARTER(2,"季"),
    YEAR(3,"年");

    private Integer code;
    private String value;


    PaymentCycleEnum(Integer code,String value) {
        this.code= code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public static String getValueByCode(Integer code) {
        if (code != null) {
            for (PaymentCycleEnum orgType : PaymentCycleEnum.values()) {
                if (Objects.equals(code, orgType.code)) {
                    return orgType.value;
                }
            }
        }
        return Strings.EMPTY;
    }

    public String getValue() {
        return value;
    }
}
