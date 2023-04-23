package com.itshop.web.dto.auth;

import lombok.Data;

import java.io.Serializable;

@Data
public class ListUserInfoAndDomainVO extends ListUserVO implements Serializable {

    /**
     * 用户域
     */
    private String userDomainName;

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

