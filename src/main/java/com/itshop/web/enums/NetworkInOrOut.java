package com.itshop.web.enums;

/**
 * 网络上行 下行
 */
public enum NetworkInOrOut {
    IN(1,"外网放行端口"),
    OUT(0,"内网禁用端口");
    private Integer code;
    private String desc;

    NetworkInOrOut(Integer code,String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
