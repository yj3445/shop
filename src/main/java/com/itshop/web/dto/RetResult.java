package com.itshop.web.dto;

import com.itshop.web.enums.RetCode;

import java.io.Serializable;

/**
 * 响应结果
 *
 * @author liufenglong
 * @date 2022/5/7
 */
public class RetResult<T>  implements Serializable {
    /**
     * 状态码(200成功,400失败,401未登录,403无权限,404访问不存在,500服务器错误)
     */
    public int code;
    /**
     * 消息
     */
    private String msg;
    /**
     * 数据
     */
    private T data;
    /**
     * 消息
     */
    private String message;


    public RetResult<T> setCode(RetCode retCode) {
        this.code = retCode.getCode();
        return this;
    }

    public int getCode() {
        return code;
    }

    public RetResult<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public RetResult<T> setMsg(RetCode retCode) {
        this.msg = retCode.getDesc();
        return this;
    }

    public RetResult<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public RetResult<T> setData(T data) {
        this.data = data;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public RetResult<T> setMessage(String message) {
        this.message = message;
        return this;
    }
}

