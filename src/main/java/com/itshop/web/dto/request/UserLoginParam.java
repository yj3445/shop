package com.itshop.web.dto.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录参数
 *
 */
@Data
public class UserLoginParam  implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 密码
     */
    private String passWord;
}
