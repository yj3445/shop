package com.itshop.web.dao.po;

import java.io.Serializable;
import java.util.Date;

/**
 * t_order_change_log
 * @author 
 */
public class TOrderChangeLog implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 变更记录id
     */
    private Integer changeLogId;

    /**
     * 变更类型(添加、变更、删除)
     */
    private String chageType;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 订单主表名称
     */
    private String orderTableName;

    /**
     * 订单id
     */
    private Integer orderId;

    /**
     * 订单明细表名称
     */
    private String orderDetailTableName;

    /**
     * 订单明细id
     */
    private Integer orderDetailId;

    /**
     * 订单明细子表名称
     */
    private String orderSubDetailTableName;

    /**
     * 订单明细子id
     */
    private Integer orderSubDetailId;

    /**
     * 字段名称
     */
    private String fieldName;

    /**
     * 旧值
     */
    private String oldVlaue;

    /**
     * 新值
     */
    private String newVlaue;

    /**
     * 值是否是字典值(0-否,1-是)
     */
    private Boolean valueIsDic;

    /**
     * 字典类型id
     */
    private Integer dicTypeId;

    /**
     * 创建人
     */
    private Integer createrBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 删除标识(0-未删除,1-删除)
     */
    private Boolean isDeleted;

    public Integer getChangeLogId() {
        return changeLogId;
    }

    public void setChangeLogId(Integer changeLogId) {
        this.changeLogId = changeLogId;
    }

    public String getChageType() {
        return chageType;
    }

    public void setChageType(String chageType) {
        this.chageType = chageType;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getOrderTableName() {
        return orderTableName;
    }

    public void setOrderTableName(String orderTableName) {
        this.orderTableName = orderTableName;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderDetailTableName() {
        return orderDetailTableName;
    }

    public void setOrderDetailTableName(String orderDetailTableName) {
        this.orderDetailTableName = orderDetailTableName;
    }

    public Integer getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Integer orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public String getOrderSubDetailTableName() {
        return orderSubDetailTableName;
    }

    public void setOrderSubDetailTableName(String orderSubDetailTableName) {
        this.orderSubDetailTableName = orderSubDetailTableName;
    }

    public Integer getOrderSubDetailId() {
        return orderSubDetailId;
    }

    public void setOrderSubDetailId(Integer orderSubDetailId) {
        this.orderSubDetailId = orderSubDetailId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getOldVlaue() {
        return oldVlaue;
    }

    public void setOldVlaue(String oldVlaue) {
        this.oldVlaue = oldVlaue;
    }

    public String getNewVlaue() {
        return newVlaue;
    }

    public void setNewVlaue(String newVlaue) {
        this.newVlaue = newVlaue;
    }

    public Boolean getValueIsDic() {
        return valueIsDic;
    }

    public void setValueIsDic(Boolean valueIsDic) {
        this.valueIsDic = valueIsDic;
    }

    public Integer getDicTypeId() {
        return dicTypeId;
    }

    public void setDicTypeId(Integer dicTypeId) {
        this.dicTypeId = dicTypeId;
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

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}