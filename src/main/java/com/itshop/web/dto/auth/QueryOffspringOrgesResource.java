package com.itshop.web.dto.auth;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class QueryOffspringOrgesResource extends BaseUserAuth implements Serializable {
    /**
     * 组织id
     */
    private Integer orgId;

    /**
     * 全路径
     */
    private String orgFullPath;

    /**
     * 返回所有的后代
     */
    private Boolean returnAllOffspring;

    /**
     * 页号(从1计数)
     */
    protected Integer pageNum;//,

    /**
     * 每页记录数
     */
    protected Integer pageSize;//建议大于5
}
