package com.itshop.web.dto.auth;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liufenglong
 * @date 2022/7/5
 */

@Data
public class PermissionTargetVO implements Serializable {

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
     * 行为
     */
    List<PermissionVO> permissions = new ArrayList<>();


    /**
     * 添加对外接口，附加信息
     */
    private JSONObject config;

}

