package com.itshop.web.bo;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 用户信息
 *
 * @author liufenglong
 * @date 2022/7/12
 */
@Data
@ToString
public class UserInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    //---------------------------------------------应用信息---------------------------------------------

    /**
     * 应用系统ID
     */
    private String appId;

    /**
     * 应用名称
     */
    private String appName;

    //---------------------------------------------组织信息---------------------------------------------
    /**
     * 公司-名称
     */
    private String companyName;

    /**
     * 组织id
     */
    private Integer orgId;

    /**
     * 组织类型(服务提供商、代理商、代理商客户公司）
     */
    private Integer orgType;


    /**
     * 组织类型(服务提供商、代理商、代理商客户公司）
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

    /**
     * 父组织id
     */
    private Integer parentOrgId;

    /**
     * 父组织编码
     */
    private String parentOrgCode;

    /**
     * 父组织名称
     */
    private String parentOrgName;

    /**
     * 父组织类型
     */
    private Integer parentOrgType;

    /**
     * 父组织类型名称
     */
    private String parentOrgTypeName;


    //---------------------------------------------用户信息---------------------------------------------

    /**
     * 应用成员信息标识
     */
    private Integer appUserInfoId;


    /**
     * 用户标识
     */
    private String userId;

    /**
     * 用户域编码
     */
    private String userDomainCode;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户邮箱
     */
    private String userEmail;

    /**
     * 手机号码(11位)
     */
    private String cellPhoneNumber;

    /**
     * 电话号码(区号-电话组成)
     */
    private String telePhoneNumber;

    //---------------------------------------------权限信息---------------------------------------------

    /**
     * 用户组
     */
    private List<String> userGroups;

    /**
     * 角色
     */
    private List<String> roles;

    /**
     * 地址列表
     */
    private List<String> urls;

    /**
     * 全路径
     */
    private String orgFullPath;

    private Integer[] orgTier;

    /**
     * 服务提供商-组织ID
     */
    private Integer serviceProviderOrgId;

    /**
     * 一级代理商-组织ID
     */
    private Integer agentLevel1OrgId;

    /**
     * 二级代理商-组织ID
     */
    private Integer agentLevel2OrgId;

    /**
     * 三级代理商-组织ID
     */
    private Integer agentLevel3OrgId;

    /**
     * 客户公司-组织ID
     */
    private Integer customerCompanyOrgId;

    /**
     * 是否可以创建代理价格
     */
    private Boolean canCreateAgentPrice;
}
