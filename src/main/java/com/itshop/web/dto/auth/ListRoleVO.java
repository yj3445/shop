package com.itshop.web.dto.auth;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ListRoleVO implements Serializable {
    /**
     *   角色ID
     */
    private Integer roleId;

    /**
     *   应用系统ID
     */
    private String appId;

    /**
     *   角色名
     */
    private String name;


    /**
     *   APP元数据
     */
    private String metaForapp;

    /**
     *   备注
     */
    private String comment;

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

    private Date updateAt;

    /**
     *   更新域
     */
    private String updateUserDomain;

    private String code;
}

