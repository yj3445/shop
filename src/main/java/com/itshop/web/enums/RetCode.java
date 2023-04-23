package com.itshop.web.enums;

/**
 * 响应码
 *
 * @author liufenglong
 * @date 2022/5/7
 */
public enum RetCode {
    /**
     * 200成功
     */
    SUCCESS(200, "操作成功"),
    /**
     * 400失败
     */
    FAILURE(400, "操作失败"),
    /**
     * 401未登录
     */
    UNAUTHORIZED(401, "登录未认证"),
    /**
     * 403无权限
     */
    FORBIDDEN(403, "资源未授权"),
    /**
     * 404访问不存在
     */
    NOT_FOUND(404, "访问不存在"),
    /**
     * 500服务器错误
     */
    INTERNAL_SERVER_ERROR(500, "系统内部错误"),

    USER_DOES_NOT_EXIST(7000,"用户不存在"),
    /**
     * 用户名或密码错误
     */
    USER_NAME_OR_PASS_WORD_ERROR(7001,"用户名或密码错误"),

    USER_PASS_WORD_ERROR(7002,"密码错误");

    private int code;

    private String desc;

    RetCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
