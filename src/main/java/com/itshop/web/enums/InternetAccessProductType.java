package com.itshop.web.enums;


/**
 * 互联网接入 产品类型
 *
 * @author liufenglong
 * @date 2022/5/12
 */
public enum InternetAccessProductType {

    CUSTOM("CUSTOM","产品E");

    private String code;
    private String desc;

    InternetAccessProductType(String code,String desc){
        this.code=code;
        this.desc=desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
