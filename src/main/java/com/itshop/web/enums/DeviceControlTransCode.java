package com.itshop.web.enums;

import io.swagger.models.auth.In;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 设备控制 事务编码
 *
 * @author liufenglong
 * @date 2022/5/12
 */
public enum DeviceControlTransCode {
    INITIAL(0,"初始状态"),
    BROAD_BAND_UPDATE(100,"更新带宽"),
    BROAD_BAND_ISP_CHANGE(110,"变更上网业务的出口（运营商）"),
    URL_SPEED_UP_ADD(200,"增加url加速"),
    URL_SPEED_UP_REMOVE(210,"删除url加速"),
    FIRE_WALL_ENABLE(300,"打开防火墙"),
    FIRE_WALL_DISABLE(310,"关闭防火墙"),
    FIRE_WALL_ENABLE_IN_PORT(320,"增加防火墙开放的下行服务端口（防火墙默认关闭所有下行服务端口）"),
    FIRE_WALL_DISABLE_OUT_PORT(330,"增加防火墙屏蔽的上行访问端口（内部访问外部，默认均开放）");
    private int code;
    private String desc;
    DeviceControlTransCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static String getDescByStatus(Integer status) {
        AtomicReference<String> desc = new AtomicReference<>(status.toString());
        Arrays.stream(DeviceControlTransCode.values())
                .filter(p -> p.getCode() == status)
                .findFirst().ifPresent(p -> desc.set(p.getDesc()));
        return desc.get();
    }
}
