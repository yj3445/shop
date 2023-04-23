package com.itshop.web.dto.auth;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserUpdateResource  extends UserAddResource implements Serializable {

    private List<Integer> addRoleIds;//新增角色授权IDS
    private List<Integer> addUserGroupIds;//新增用户组授权IDS
    private List<Integer> delRoleIds;//逻辑删除角色授权
    private List<Integer> delUserGroupIds;//逻辑删除用户组授权
    private Integer status;
    private Integer appUserInfoId;
}
