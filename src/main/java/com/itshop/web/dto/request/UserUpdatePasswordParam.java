package com.itshop.web.dto.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户更新密码
 *
 * @author liufenglong
 * @date 2022/8/3
 */
@Data
public class UserUpdatePasswordParam   implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 旧密码
     */
    private String oldPassword;

    /**
     * 新密码
     */
    private String newPassword;


    /**
     * 新密码
     */
    private String confirmPassword;
}
