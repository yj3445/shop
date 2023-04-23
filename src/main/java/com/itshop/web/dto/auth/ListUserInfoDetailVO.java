package com.itshop.web.dto.auth;

import lombok.Data;

import java.util.List;

@Data
public class ListUserInfoDetailVO extends  ListUserInfoVO {

    /**
     *   创建人名称
     */
    private String createUserName;

    /**
     *   修改人名称
     */
    private String  updateUserName;

    private String userDomainName;

    private List<ListNameOrCodeVO> roleList;

    private List<ListNameOrCodeVO>  userGroupList;

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
     * 组织类型(服务提供商、代理商、代理商客户公司）
     */
    private Integer orgType;

    /**
     * 组织类型名称
     */
    private String orgTypeName;


    /**
     * 组织编码
     */
    private String orgCode;

    /**
     * (公司/企业)组织名称
     */
    private String orgName;
}
