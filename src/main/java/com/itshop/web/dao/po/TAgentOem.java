package com.itshop.web.dao.po;

import java.io.Serializable;
import java.util.Date;

/**
 * t_agent_oem
 * @author 
 */
public class TAgentOem implements Serializable {
    /**
     * 代理设置
     */
    private Integer agentOemId;

    /**
     * 组织id
     */
    private Integer orgId;

    /**
     * 站点名称
     */
    private String siteName;

    /**
     * 自备域名
     */
    private String customDomainName;

    /**
     * 自备域名是否启用(0-未启用,1-启用)
     */
    private Boolean customDomainEnable;

    /**
     * 首页背景图
     */
    private String homePageBackgroundImage;

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

    /**
     * 主标题
     */
    private String mainTitle;
    /**
     * 副标题
     */
    private String subTitle;
    /**
     * 背景色
     */
    private String backGroundColor;

    private static final long serialVersionUID = 1L;

    public Integer getAgentOemId() {
        return agentOemId;
    }

    public void setAgentOemId(Integer agentOemId) {
        this.agentOemId = agentOemId;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getCustomDomainName() {
        return customDomainName;
    }

    public void setCustomDomainName(String customDomainName) {
        this.customDomainName = customDomainName;
    }

    public Boolean getCustomDomainEnable() {
        return customDomainEnable;
    }

    public void setCustomDomainEnable(Boolean customDomainEnable) {
        this.customDomainEnable = customDomainEnable;
    }

    public String getHomePageBackgroundImage() {
        return homePageBackgroundImage;
    }

    public void setHomePageBackgroundImage(String homePageBackgroundImage) {
        this.homePageBackgroundImage = homePageBackgroundImage;
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

    public String getMainTitle() {
        return mainTitle;
    }

    public void setMainTitle(String mainTitle) {
        this.mainTitle = mainTitle;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getBackGroundColor() {
        return backGroundColor;
    }

    public void setBackGroundColor(String backGroundColor) {
        this.backGroundColor = backGroundColor;
    }
}