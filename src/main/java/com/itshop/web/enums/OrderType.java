package com.itshop.web.enums;

public enum OrderType {
    INTERNET_ACCESS_ORDER("INTERNET_ACCESS_ORDER","互联网接入订单"),
    INTERNET_ACCESS_ORDER_DETAIL("INTERNET_ACCESS_ORDER_DETAIL","互联网接入订单明细"),
    APPLICATION_SPEED_UP_ORDER("APPLICATION_SPEED_UP_ORDER","应用加速订单"),
    APPLICATION_SPEED_UP_ORDER_DETAIL("APPLICATION_SPEED_UP_ORDER_DETAIL","应用加速订单明细"),
    IDC_FIREWALL_ORDER("IDC_FIREWALL_ORDER","IDC防火墙订单"),
    IDC_FIREWALL_ORDER_DETAIL("IDC_FIREWALL_ORDER_DETAIL","IDC防火墙订单明细"),
    INTERNET_ACCESS_UPDATE_SERVICE_ORDER("INTERNET_ACCESS_UPDATE_SERVICE_ORDER","互联网接入升级服务订单"),
    APPLICATION_SPEED_UP_OTHER_ORDER("APPLICATION_SPEED_UP_OTHER_ORDER","其他类型-应用加速订单"),
    ;
    private String value;
    private String desc;
    OrderType(String value,String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
