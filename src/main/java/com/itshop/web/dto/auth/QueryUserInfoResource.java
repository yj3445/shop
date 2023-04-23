package com.itshop.web.dto.auth;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class QueryUserInfoResource extends BaseUserAuth implements Serializable {
    private Integer appUserInfoId;//用户表主键ID
}
