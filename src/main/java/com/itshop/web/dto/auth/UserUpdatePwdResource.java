package com.itshop.web.dto.auth;

import lombok.Data;

import java.io.Serializable;

/**
 * 更新用户密码
 *
 * @author liufenglong
 * @date 2022/8/3
 */
@Data
public class UserUpdatePwdResource implements Serializable {
    /**
     * 应用成员信息标识
     */
    private Integer appUserInfoId;

    /**
     * 用户标识
     */
    private String userId;

    /**
     * 旧密码
     */
    private String oldPassword;

    /**
     * 新密码
     */
    private String newPassword;

    /**
     * API_TOKEN
     */
    private String apiToken;
}
