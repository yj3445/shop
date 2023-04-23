package com.itshop.web.dto;

import com.itshop.web.enums.RetCode;

/**
 * 响应结果包装器
 *
 * @author liufenglong
 * @date 2022/5/7
 */
public class RetWrapper {

    public static <T> RetResult<T> success() {
        return new RetResult<T>().setCode(RetCode.SUCCESS).setMsg(RetCode.SUCCESS);
    }

    public static <T> RetResult<T> success(T data) {
        return new RetResult<T>().setCode(RetCode.SUCCESS).setMsg(RetCode.SUCCESS).setData(data);
    }

    public static <T> RetResult<T> failure(String message) {
        return new RetResult<T>().setCode(RetCode.FAILURE).setMsg(message);
    }

    public static <T> RetResult<T> unauthorized() {
        return new RetResult<T>().setCode(RetCode.UNAUTHORIZED).setMsg(RetCode.UNAUTHORIZED);
    }

    public static <T> RetResult<T> forbidden() {
        return new RetResult<T>().setCode(RetCode.FORBIDDEN).setMsg(RetCode.FORBIDDEN);
    }

    public static <T> RetResult<T> notFound(String message) {
        return new RetResult<T>().setCode(RetCode.NOT_FOUND).setMsg(message);
    }

    public static <T> RetResult<T> internalServerError(String message) {
        return new RetResult<T>().setCode(RetCode.INTERNAL_SERVER_ERROR).setMsg(message);
    }

    public static <T> RetResult<T> userNameOrPassWordError() {
        return new RetResult<T>().setCode(RetCode.FAILURE).setMsg(RetCode.USER_NAME_OR_PASS_WORD_ERROR);
    }

    public static <T> RetResult<T> userPassWordError() {
        return new RetResult<T>().setCode(RetCode.FAILURE).setMsg(RetCode.USER_PASS_WORD_ERROR);
    }

    public static <T> RetResult<T> userDoesNotExist() {
        return new RetResult<T>().setCode(RetCode.FAILURE).setMsg(RetCode.USER_DOES_NOT_EXIST);
    }

    public static <T> RetResult<T> makeRsp(int code, String msg) {
        return new RetResult<T>().setCode(code).setMsg(msg);
    }

    public static <T> RetResult<T> makeRsp(int code, String msg, T data) {
        return new RetResult<T>().setCode(code).setMsg(msg).setData(data);
    }

}

