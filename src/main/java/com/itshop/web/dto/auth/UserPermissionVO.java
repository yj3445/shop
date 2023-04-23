package com.itshop.web.dto.auth;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserPermissionVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *   用户资源id
     */
    private Integer permissionTargetId;

    /**
     *   用户资源名称
     */
    private String name;

    /**
     *   用户子级资源列表
     */
    private List<UserPermissionVO> subPermissions;

}
