package com.itshop.web.dao.po;

import lombok.Data;

import java.io.Serializable;

/**
 * t_organizational
 *
 * @author liufenglong
 * @date 2022/5/7
 */
@Data
public class TOrganizational implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 组织id
     */
    private Integer orgId;

    /**
     * 组织类型(服务提供商、代理商、代理商客户公司）
     */
    private Integer orgType;


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
     * 全路径
     */
    private String orgFullPath;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 组织树形结构
     */
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
}