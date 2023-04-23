package com.itshop.web.dao.po;

import java.io.Serializable;

/**
 * t_device_control_log
 * @author 
 */
public class TDeviceControlLogWithBLOBs extends TDeviceControlLog implements Serializable {
    /**
     * 请求参数
     */
    private String requestJson;

    /**
     * 响应参数
     */
    private String responseJson;

    /**
     * 异常描述
     */
    private String exceptionDesc;

    private static final long serialVersionUID = 1L;

    public String getRequestJson() {
        return requestJson;
    }

    public void setRequestJson(String requestJson) {
        this.requestJson = requestJson;
    }

    public String getResponseJson() {
        return responseJson;
    }

    public void setResponseJson(String responseJson) {
        this.responseJson = responseJson;
    }

    public String getExceptionDesc() {
        return exceptionDesc;
    }

    public void setExceptionDesc(String exceptionDesc) {
        this.exceptionDesc = exceptionDesc;
    }
}