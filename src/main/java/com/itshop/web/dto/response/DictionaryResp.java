package com.itshop.web.dto.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class DictionaryResp  implements Serializable {
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
}
