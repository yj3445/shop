package com.itshop.web.dto.auth;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 组织详情响应
 */
@Data
public class OrganizationalDetailVO implements Serializable {

    /**
     * 组织id
     */
    private Integer orgId;

    /**
     * 应用系统ID
     */
    private String appId;

    /**
     *   应用名称
     */
    private String appName;


    /**
     * 组织类型(服务提供商、代理商、代理商客户公司）
     */
    private Integer orgType;


    /**
     * 组织类型(服务提供商、代理商、代理商客户公司）
     */
    private String orgTypeName;

    /**
     * 组织层级(由1递增,依次加1)
     */
    private Integer orgLevel;

    /**
     * 组织编码
     */
    private String orgCode;

    /**
     * (公司/企业)组织名称
     */
    private String orgName;

    /**
     * 统一社会信用代码
     */
    private String unifiedSocialCreditCode;

    /**
     * 纳税人识别号
     */
    private String taxpayerIdentificationNumber;

    /**
     * 所属行业
     */
    private String industry;

    /**
     * 注册地址
     */
    private String registeredAddress;

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

    /**
     * 创建人ID
     */
    private Integer createrBy;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建人
     */
    private String createUserName;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+08:00")
    private Date createTime;

    /**
     * 操作人ID
     */
    private Integer modifiedBy;

    /**
     * 操作人
     */
    private String modifier;

    /**
     * 操作人
     */
    private String updateUserName;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+08:00")
    private Date modifiedTime;

    /**
     * 删除标识(0-未删除,1-删除)
     */
    private Boolean isDeleted;

    /**
     * 序号
     */
    private Integer orderNum;

    /**
     * 备注
     */
    private String remark;

    /**
     * 状态 (0启用，1-禁用)
     */
    private Integer status;

    /**
     *公司-名称
     */
    private String companyName;
    /**
     * 公司-手机号码(11位)
     */
    private String companyCellPhone;
    /**
     * 公司-电话号码(区号-电话组成)
     */
    private String companyTelePhone;
    /**
     * 公司-开户银行
     */
    private String companyBankOfDeposit;
    /**
     * 公司-银行账户
     */
    private String companyBankAccount;
    /**
     * 联系人姓名
     */
    private String linkmanName;
    /**
     * 联系人-手机号码(11位)
     */
    private String linkmanCellPhone;
    /**
     * 联系人-电话号码(区号-电话组成)
     */
    private String linkmanTelePhone;
    /**
     * 联系人-用户邮箱
     */
    private String linkmanEmail;
    /**
     * 全路径
     */
    private String orgFullPath;

    private static final long serialVersionUID = 1L;
}
