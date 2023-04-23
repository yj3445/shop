package com.itshop.web.dto.auth;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liufenglong
 * @date 2022/7/5
 */

@Data
public class AppPermissionTargetVO implements Serializable {

    /**
     *   资源权限实体ID
     */
    private Integer permissionTargetId;

    /**
     *   应用系统ID
     */
    private String appId;

    /**
     *   父权限实体ID
     */
    private Integer parentPermissionTargetId;

    /**
     *   层级
     */
    private Integer level;

    /**
     *   识别码
     */
    private String targetCode;

    /**
     *   实体名
     */
    private String name;

    /**
     *   URL路径
     */
    private String url;

    /**
     *   目录序号
     */
    private Integer listNo;

    /**
     *   是否显示
     */
    private Byte inAppUi;

    /**
     *   备注
     */
    private String comment;

    /**
     *   删除标识
     */
    private Integer isDelete;

    /**
     *   是否显示
     */
    private Byte isApply;

    /**
     *   创建时间
     */
    private Date createAt;

}
