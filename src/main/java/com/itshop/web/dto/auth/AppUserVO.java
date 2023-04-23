package com.itshop.web.dto.auth;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liufenglong
 * @date 2022/7/5
 */
@Data
public class AppUserVO implements Serializable {
    /**
     * 应用成员信息标识
     */
    private Integer appUserInfoId;

    /**
     * 应用系统ID
     */
    private String appId;

    /**
     * 用户标识
     */
    private String userId;

    /**
     * 用户域编码
     */
    private String userDomainCode;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户邮箱
     */
    private String userEmail;

    /**
     * 备注
     */
    private String comment;

    /**
     * 性别(0-女,1-男)
     */
    private Boolean gender;

    /**
     * 手机号码(11位)
     */
    private String cellPhoneNumber;

    /**
     * 电话号码(区号-电话组成)
     */
    private String telePhoneNumber;

    /**
     * 组织架构ID
     */
    private Integer orgId;
}
