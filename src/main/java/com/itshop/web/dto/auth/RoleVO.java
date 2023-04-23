package com.itshop.web.dto.auth;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liufenglong
 * @date 2022/7/5
 */
@Data
public class RoleVO implements Serializable {
    /**
     * 权限拥有者类型
     */
    private String owerType;
    /**
     * 权限拥有者域
     */
    private String owerDomain;
    /**
     * 权限拥有者ID
     */
    private String owerId;
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
}

