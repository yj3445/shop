package com.itshop.web.dto.auth;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class ListUserVO implements Serializable {
    /**
     *   应用成员信息标识
     */
    private Integer appUserInfoId;

    /**
     *   应用系统ID
     */
    private String appId;

    /**
     *   用户标识
     */
    private String userId;

    /**
     *   用户域编码
     */
    private String userDomainCode;

    /**
     *   用户名
     */
    private String userName;
    /**
     *   邮箱
     */
    private String userEmail;

    /**
     *   状态
     */
    private Integer status;

    /**
     *   删除标识
     */
    private Integer isDelete;

    /**
     *   创建ID
     */
    private String createUserId;

    /**
     *   创建时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+08:00")

    private Date createAt;

    /**
     *   创建域
     */
    private String createUserDomain;

    /**
     *   更新ID
     */
    private String updateUserId;

    /**
     *   更新时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+08:00")
    private Date updateAt;

    /**
     *   更新域
     */
    private String updateUserDomain;

    /**
     *   备注
     */
    private String comment;
    private String config;

    /**
     * 岗位编码
     */
    private String positionCodes;

    /**
     * 岗位名称
     */
    private String positionNames;
    /**
     * 同步组织结构标识
     */
    private Integer syncFlag;

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
