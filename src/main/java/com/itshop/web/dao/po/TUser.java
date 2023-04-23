package com.itshop.web.dao.po;

import java.io.Serializable;
import java.util.Date;

/**
 * t_user
 *
 * @author liufenglong
 * @date 2022/5/7
 */
public class TUser implements Serializable {
    /**
     * 用户id(自增)
     */
    private Integer userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String passWord;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 性别(0-女,1-男)
     */
    private Boolean gender;

    /**
     * 手机号码(11位)
     */
    private String cellPhoneNumber;

    /**
     * 电话号码(区号-电话组成)
     */
    private String telePhoneNumber;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 所属组织架构id
     */
    private Integer orgId;

    /**
     * 启用(0-禁用,1-启用)
     */
    private Boolean enable;

    /**
     * 创建人
     */
    private Integer createrBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 操作人
     */
    private Integer modifiedBy;

    /**
     * 更新时间
     */
    private Date modifiedTime;

    /**
     * 删除标识(0-未删除,1-删除)
     */
    private Boolean isDeleted;

    private static final long serialVersionUID = 1L;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getCellPhoneNumber() {
        return cellPhoneNumber;
    }

    public void setCellPhoneNumber(String cellPhoneNumber) {
        this.cellPhoneNumber = cellPhoneNumber;
    }

    public String getTelePhoneNumber() {
        return telePhoneNumber;
    }

    public void setTelePhoneNumber(String telePhoneNumber) {
        this.telePhoneNumber = telePhoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Integer getCreaterBy() {
        return createrBy;
    }

    public void setCreaterBy(Integer createrBy) {
        this.createrBy = createrBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Integer modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}