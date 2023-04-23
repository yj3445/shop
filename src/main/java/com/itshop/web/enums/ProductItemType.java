package com.itshop.web.enums;

/**
 * 产品项类型
 *
 * @author liufenglong
 * @date 2022/7/21
 */
public enum  ProductItemType {
    BROAD_BAND("BROAD_BAND","带宽"),
    EXPORT("EXPORT","出口"),
    CONTRACT_PERIOD("CONTRACT_PERIOD","合同期限"),
    PAYMENT_CYCLE("PAYMENT_CYCLE","缴费周期"),
    PAYMENT_METHOD("PAYMENT_METHOD","付费方式");

    private String code;
    private String name;
    ProductItemType(String code,String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
