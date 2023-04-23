package com.itshop.web.dto.auth;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserIdResource extends AppIdResource{
    private String userId;//用户ID
    private String domainCode;
}
