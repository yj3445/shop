package com.itshop.web.dto.auth;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginUserAuthVO implements Serializable {

    private UserVO userVO;

    private AppUserVO appUserVO;

    private OrganizationalDetailVO organizationalVO;
}
