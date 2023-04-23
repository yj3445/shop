package com.itshop.web.enums;

/**
 * 设备控制 服务类型
 *
 * @author liufenglong
 * @date 2022/5/9
 */
public enum DeviceControlServiceType {
    BROAD_BAND("BROAD_BAND","宽带业务"),
    FIRE_WALL("FIRE_WALL","防火墙业务"),
    URL_SPEED_UP("URL_SPEED_UP","url加速业务");

    private String code;
    private String desc;

    DeviceControlServiceType(String code,String desc){
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
