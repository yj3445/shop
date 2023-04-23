package com.itshop.web.dto.response;

import com.google.common.collect.Lists;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 品类(产品)代理关系树
 *
 * @author liufenglong
 * @date 2022/7/20
 */
@Data
public class CategoryProductAgentResp implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 品类(产品)id
     */
    private Integer id;

    /**
     * 品类(产品)编码
     */
    private String code;

    /**
     * 品类(产品)名称
     */
    private String name;

    /**
     * 品类(产品)排序
     */
    private Integer orderNum;

    /**
     * 类型
     */
    private String type;

    /**
     * 代理关系ID
     */
    private Integer agentRelationId;

    /**
     * 是否代理
     */
    private Boolean isAgent;

    /**
     *
     */
    private List<CategoryProductAgentResp> children;

    public CategoryProductAgentResp() {
        children = Lists.newArrayList();
    }
}
