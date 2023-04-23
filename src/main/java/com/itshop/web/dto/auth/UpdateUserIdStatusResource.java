package com.itshop.web.dto.auth;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UpdateUserIdStatusResource extends UserIdResource{
    private Integer status;
    private Integer appUserInfoId;
}
