package com.itshop.web.dto.auth;

import lombok.Data;

import java.io.Serializable;


@Data
public class LoginUserAuthResource implements Serializable {
    /**
     * 密码
     */
    private String passWord;

    protected String apiToken;
    protected String userDomain;
    protected String userId;
}
