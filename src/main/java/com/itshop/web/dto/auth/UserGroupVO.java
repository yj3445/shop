package com.itshop.web.dto.auth;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户组
 *
 * @author liufenglong
 * @date 2022/7/5
 */
@Data
public class UserGroupVO implements Serializable {
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
     *   用户组ID
     */
    private Integer userGroupId;

    /**
     *   应用系统ID
     */
    private String appId;

    /**
     *   用户组编码
     */
    private String code;

    /**
     *   用户组名
     */
    private String name;

    /**
     *   大区编码
     */
    private String regioneCode;

    /**
     *   大区名称
     */
    private String regionName;

    /**
     *   一级分部编码
     */
    private String branchCodeOne;

    /**
     *   一级分部名称
     */
    private String branchNameOne;

    /**
     *   二级分部编码
     */
    private String branchCodeTwo;

    /**
     *   门店编码
     */
    private String storeCode;

    /**
     *   门店名称
     */
    private String storeName;

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
