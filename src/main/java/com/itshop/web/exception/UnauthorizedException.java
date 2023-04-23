package com.itshop.web.exception;


import com.itshop.web.enums.RetCode;

/**
 * 登录未认证异常
 *
 * @author liufenglong
 * @date 2022/5/7
 */
public class UnauthorizedException extends BaseException {
    public UnauthorizedException(String msg) {
        super(RetCode.UNAUTHORIZED.getCode(), msg, null);
    }

    public UnauthorizedException(String msg, Throwable cause) {
        super(RetCode.UNAUTHORIZED.getCode(), msg, cause);
    }

    public UnauthorizedException() {
        this(RetCode.UNAUTHORIZED.getDesc());
    }
}
