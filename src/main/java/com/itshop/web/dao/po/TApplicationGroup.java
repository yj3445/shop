package com.itshop.web.dao.po;

import java.io.Serializable;
import java.util.Date;

/**
 * t_application_group
 *
 * @author liufenglong
 * @date 2022/5/7
 */
public class TApplicationGroup implements Serializable {
    private Integer applicationId;

    /**
     * 应用实例英文名
     */
    private String applicationEnName;

    /**
     * 应用实例中文名
     */
    private String applicationCnName;

    /**
     * 应用实例地址(url或ip)
     */
    private String applicationUrl;

    /**
     * 应用实例icon
     */
    private String applicationIcon;

    /**
     * 应用实例组(实例A、实例B、实例C)
     */
    private String applicationGroup;

    /**
     * ping性能指标参考值
     */
    private Integer referencePing;

    /**
     * lost性能指标参考值
     */
    private Integer referenceLost;

    /**
     * trace性能指标参考值
     */
    private Integer referenceTrace;

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

    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }

    public String getApplicationEnName() {
        return applicationEnName;
    }

    public void setApplicationEnName(String applicationEnName) {
        this.applicationEnName = applicationEnName;
    }

    public String getApplicationCnName() {
        return applicationCnName;
    }

    public void setApplicationCnName(String applicationCnName) {
        this.applicationCnName = applicationCnName;
    }

    public String getApplicationUrl() {
        return applicationUrl;
    }

    public void setApplicationUrl(String applicationUrl) {
        this.applicationUrl = applicationUrl;
    }

    public String getApplicationIcon() {
        return applicationIcon;
    }

    public void setApplicationIcon(String applicationIcon) {
        this.applicationIcon = applicationIcon;
    }

    public String getApplicationGroup() {
        return applicationGroup;
    }

    public void setApplicationGroup(String applicationGroup) {
        this.applicationGroup = applicationGroup;
    }

    public Integer getReferencePing() {
        return referencePing;
    }

    public void setReferencePing(Integer referencePing) {
        this.referencePing = referencePing;
    }

    public Integer getReferenceLost() {
        return referenceLost;
    }

    public void setReferenceLost(Integer referenceLost) {
        this.referenceLost = referenceLost;
    }

    public Integer getReferenceTrace() {
        return referenceTrace;
    }

    public void setReferenceTrace(Integer referenceTrace) {
        this.referenceTrace = referenceTrace;
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