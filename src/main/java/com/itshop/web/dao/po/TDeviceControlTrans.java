package com.itshop.web.dao.po;

import java.io.Serializable;
import java.util.Date;

/**
 * t_device_control_trans
 * @author 
 */
public class TDeviceControlTrans implements Serializable {
    /**
     * 全局事务ID
     */
    private Integer transId;

    /**
     * 业务类型:互联网接入,防火墙,url加速
     */
    private String serviceType;

    /**
     * 变更前记录id
     */
    private Integer changeId;

    /**
     * 订单id
     */
    private Integer orderId;

    /**
     * 事务状态：0-运行中；1-成功；2-重试多次后依然失败
     */
    private Integer status;

    /**
     * 最终状态
     */
    private Integer end;

    /**
     * 当前状态
     */
    private Integer process;

    /**
     * 重试次数
     */
    private Integer retryCount;

    /**
     * 备注
     */
    private String remark;

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
     * 事务所有中间状态
     */
    private String middleStatus;

    private static final long serialVersionUID = 1L;

    public Integer getTransId() {
        return transId;
    }

    public void setTransId(Integer transId) {
        this.transId = transId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public Integer getChangeId() {
        return changeId;
    }

    public void setChangeId(Integer changeId) {
        this.changeId = changeId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public Integer getProcess() {
        return process;
    }

    public void setProcess(Integer process) {
        this.process = process;
    }

    public Integer getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getMiddleStatus() {
        return middleStatus;
    }

    public void setMiddleStatus(String middleStatus) {
        this.middleStatus = middleStatus;
    }
}