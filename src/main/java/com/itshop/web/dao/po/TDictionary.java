package com.itshop.web.dao.po;

import java.io.Serializable;

/**
 * t_dictionary
 * @author 
 */
public class TDictionary implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 字典ID
     */
    private Integer dictionaryId;

    /**
     * 字典类型id
     */
    private Integer dictionaryTypeId;

    /**
     * 字典值编码
     */
    private String dictionaryCode;

    /**
     * 字典值名称
     */
    private String dictionaryName;

    /**
     * 顺序
     */
    private Integer orderNum;

    /**
     * 备注
     */
    private String remark;

    public Integer getDictionaryId() {
        return dictionaryId;
    }

    public void setDictionaryId(Integer dictionaryId) {
        this.dictionaryId = dictionaryId;
    }

    public Integer getDictionaryTypeId() {
        return dictionaryTypeId;
    }

    public void setDictionaryTypeId(Integer dictionaryTypeId) {
        this.dictionaryTypeId = dictionaryTypeId;
    }

    public String getDictionaryCode() {
        return dictionaryCode;
    }

    public void setDictionaryCode(String dictionaryCode) {
        this.dictionaryCode = dictionaryCode;
    }

    public String getDictionaryName() {
        return dictionaryName;
    }

    public void setDictionaryName(String dictionaryName) {
        this.dictionaryName = dictionaryName;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}