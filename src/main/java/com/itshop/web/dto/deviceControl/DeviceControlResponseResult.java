package com.itshop.web.dto.deviceControl;

import lombok.Data;

import java.io.Serializable;

/**
 * 社保控制业务响应
 *
 * @author liufenglong
 * @date 2022/5/7
 */
@Data
public class DeviceControlResponseResult<T> implements Serializable {

    public final static String SUCCEED="0";

    public final static String EXCEPTION="-1";

    /**
     * 错误编码，0：成功，其它失败
     */
    private String code;
    /**
     * 消息提示
     */
    private String msg;
    /**
     * 数据
     */
    private T data;
}
