package com.itshop.web.dto.auth;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BaseUserAuth extends ApiToken {

    /**
     * 用户域
     */
    private String userDomain;
    /**
     * 用户ID
     */
    private String userId;
}
