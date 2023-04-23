package com.itshop.web.dto.auth;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * @author liufenglong
 * @date 2022/7/5
 */

@Data
public class PermissionVO implements Serializable {
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
     *   资源权限ID
     */
    private Integer permissionId;

    /**
     *   应用系统ID
     */
    private String appId;

    /**
     *   行为
     */
    private String action;
    /**
     *   行为名称
     */
    private String actionName;

    /**
     *   备注
     */
    private String comment;

    /**
     *   删除标识
     */
    private Integer isDelete;

    //    @ApiSkip(type = "")
    @JSONField(serialize = false)
    private PermissionTargetVO permissionTargetVO;
}
