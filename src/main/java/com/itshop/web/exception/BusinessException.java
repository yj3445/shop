package com.itshop.web.exception;


import com.itshop.web.enums.RetCode;

/**
 * 业务异常
 *
 * @author liufenglong
 * @date 2022/5/7
 */
public class BusinessException extends BaseException {

    public BusinessException(String msg) {
        this(RetCode.FAILURE.getCode(), msg, null);
    }

    public BusinessException(String msg, Throwable cause) {
        this(RetCode.FAILURE.getCode(), msg, cause);
    }

    public BusinessException(int code, String msg, Throwable cause) {
        super(code, msg, cause);
    }

}
