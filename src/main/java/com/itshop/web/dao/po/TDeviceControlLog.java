package com.itshop.web.dao.po;

import java.io.Serializable;
import java.util.Date;

/**
 * t_device_control_log
 * @author 
 */
public class TDeviceControlLog implements Serializable {
    /**
     * ID
     */
    private Integer logId;

    /**
     * 业务类型：宽带业务,防火墙,url加速
     */
    private String serviceType;

    /**
     * 操作类型：查询,新增,删除,修改
     */
    private String operationType;

    /**
     * 记录类型：0-操作记录；1-异常记录
     */
    private Boolean recordType;

    /**
     * 业务编号
     */
    private String businessId;

    /**
     * 请求时长(毫秒)
     */
    private Integer timeMillis;

    /**
     * 异常类型
     */
    private String exceptionType;

    /**
     * 上下文id
     */
    private String uuid;

    /**
     * 创建人
     */
    private Integer createrBy;

    /**
     * 创建时间
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public Boolean getRecordType() {
        return recordType;
    }

    public void setRecordType(Boolean recordType) {
        this.recordType = recordType;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public Integer getTimeMillis() {
        return timeMillis;
    }

    public void setTimeMillis(Integer timeMillis) {
        this.timeMillis = timeMillis;
    }

    public String getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(String exceptionType) {
        this.exceptionType = exceptionType;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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
}