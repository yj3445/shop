package com.itshop.web.exception;


import com.itshop.web.enums.RetCode;
import lombok.Data;

/**
 * 自定义异常
 *
 * @author liufenglong
 * @date 2022/5/7
 */
@Data
public abstract class BaseException extends RuntimeException {
    /**
     * 响应码
     */
    private int code;
    /**
     * 消息
     */
    private String msg;


    public BaseException(int code, String msg) {
        this(code, msg, null);
    }

    public BaseException(int code, String msg, Throwable cause) {
        super(msg, cause);
        this.code = code;
        this.msg = msg;
    }

    public BaseException(RetCode retCode, Throwable cause) {
        this(retCode.getCode(), retCode.getDesc(), cause);
    }
}
