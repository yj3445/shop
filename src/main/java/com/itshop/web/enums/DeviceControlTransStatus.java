package com.itshop.web.enums;

/**
 * 设备控制 事务状态
 *
 * @author liufenglong
 * @date 2022/5/12
 */
public enum DeviceControlTransStatus {
    RUNNING(0,"运行中"),
    SUCCEED(1,"成功"),
    AFTER_RETRY_MANY_TIMES_STILL_ERROR(2,"运行中");

    private int code;
    private String desc;
    DeviceControlTransStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
