package com.itshop.web.exception;

import com.itshop.web.enums.RetCode;

/**
 * 资源未授权异常
 *
 * @author liufenglong
 * @date 2022/5/7
 */
public class ForbiddenException extends BaseException {
    public ForbiddenException(String msg) {
        super(RetCode.FORBIDDEN.getCode(), msg, null);
    }

    public ForbiddenException(String msg, Throwable cause) {
        super(RetCode.FORBIDDEN.getCode(), msg, cause);
    }

    public ForbiddenException() {
        this(RetCode.FORBIDDEN.getDesc());
    }
}
