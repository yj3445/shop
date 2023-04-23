package com.itshop.web.enums;

/**
 * 结算单-支付状态
 */
public enum PaymentStatusEnum {
    NO_PAY("NO_PAY","未结算"),
    PAY("PAY","已结算");

    private String code;
    private String value;


    PaymentStatusEnum(String code,String value) {
        this.code= code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
