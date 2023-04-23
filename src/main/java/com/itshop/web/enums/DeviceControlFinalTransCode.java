package com.itshop.web.enums;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 设备控制 事务最终状态
 *
 * @author liufenglong
 * @date 2022/5/12
 */
public enum DeviceControlFinalTransCode {
    //应用加速
    URL_SPEED_UP_ADD(DeviceControlTransCode.URL_SPEED_UP_ADD.getCode(),
            DeviceControlTransCode.URL_SPEED_UP_ADD.getDesc(),
            new LinkedList<Integer>() {{
                add(DeviceControlTransCode.INITIAL.getCode());
                add(DeviceControlTransCode.URL_SPEED_UP_ADD.getCode());
            }}),
    URL_SPEED_UP_REMOVE(DeviceControlTransCode.URL_SPEED_UP_REMOVE.getCode(),
            DeviceControlTransCode.URL_SPEED_UP_REMOVE.getDesc(),
            new LinkedList<Integer>() {{
                add(DeviceControlTransCode.INITIAL.getCode());
                add(DeviceControlTransCode.URL_SPEED_UP_REMOVE.getCode());
            }}),
    URL_SPEED_UP_REMOVE_ADD(210200, String.format("%s;%s;",
            DeviceControlTransCode.URL_SPEED_UP_REMOVE.getDesc(),
            DeviceControlTransCode.URL_SPEED_UP_ADD.getDesc()),
            new LinkedList<Integer>() {{
                add(DeviceControlTransCode.INITIAL.getCode());
                add(DeviceControlTransCode.URL_SPEED_UP_REMOVE.getCode());
                add(DeviceControlTransCode.URL_SPEED_UP_ADD.getCode());
            }});

    private int status;
    private String desc;
    private LinkedList<Integer> middleStatus;

    DeviceControlFinalTransCode(int status, String desc, LinkedList<Integer> middleStatus) {
        this.status = status;
        this.desc = desc;
        this.middleStatus = middleStatus;
    }

    public int getStatus() {
        return status;
    }

    public LinkedList<Integer> getMiddleStatus() {
        return middleStatus;
    }

    public String getDesc() {
        return desc;
    }

}
