package com.itshop.web.dto.auth;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liufenglong
 * @date 2022/7/5
 */
@Data
public class AuthorizationDataRangeVO implements Serializable {

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
     *   数据权限ID
     */
    private Integer authorizationDataRangeId;

    /**
     *   应用系统ID
     */
    private String appId;

    /**
     *   元数据ID
     */
    private Integer authorizationDataRangeMetaId;

    /**
     *   属性标识
     */
    private String dataPropId;

    /**
     *   权限值
     */
//    @ApiSkip(type = "详见:数据权限dataValue说明")
    private Object dataValue;

    /**
     *   删除标识
     */
    private Integer isDelete;

}
