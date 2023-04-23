package com.itshop.web.dto.auth;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OrganizationalUpdateResource extends BaseUserAuth implements Serializable {
    /**
     * 应用系统ID
     */
    private Integer orgId;

    /**
     * 组织类型(10-服务提供商;20-代理商;30-代理商客户公司）
     */
    private Integer orgType;

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
     * 序号
     */
    private Integer orderNum;

    /**
     * 备注
     */
    private String remark;

    /**
     * 状态
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

    private static final long serialVersionUID = 1L;
}
