package com.itshop.web.dao.po;

import java.io.Serializable;
import java.util.Date;

/**
 * t_internet_access_export_config
 * @author 
 */
public class TInternetAccessExportConfig implements Serializable {
    /**
     * 出口ID
     */
    private Integer exportId;

    /**
     * 出口名称
     */
    private String name;

    /**
     * 排序
     */
    private Integer orderNum;

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

    public Integer getExportId() {
        return exportId;
    }

    public void setExportId(Integer exportId) {
        this.exportId = exportId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
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