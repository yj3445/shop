package com.itshop.web.dto.response;

import com.google.common.collect.Lists;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class DictionaryTypeResp implements Serializable {
    /**
     * 字典类型ID
     */
    private Integer dictionaryTypeId;

    /**
     * 字典类型编码
     */
    private String dictionaryTypeCode;

    /**
     * 字典类型名称
     */
    private String dictionaryTypeName;

    /**
     * 是否启用(1-启用,0-禁用)
     */
    private Boolean enabled;

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
     * 字典项
     */
    private List<DictionaryResp> items;

    public DictionaryTypeResp(){
        items = Lists.newArrayList();
    }
}
