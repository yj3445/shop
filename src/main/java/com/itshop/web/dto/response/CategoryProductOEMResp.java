package com.itshop.web.dto.response;

import com.google.common.collect.Lists;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 品类(产品)信息
 */
@Data
public class CategoryProductOEMResp implements Serializable {
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
     * 品类(产品)OEM-ID
     */
    private Integer oemId;

    /**
     * 品类(产品)OEM名称
     */
    private String oemName;

    /**
     * 品类(产品)OEM大图
     */
    private String oemLargerImage;

    /**
     * 品类(产品)OEM摘要
     */
    private String oemSummary;

    /**
     * 品类(产品)OEM描述
     */
    private String oemDescribe;

    /**
     * 品类(产品)排序
     */
    private Integer orderNum;

    /**
     * 类型
     */
    private String type;

    /**
     * 是否启用
     */
    private Boolean enable;

    /**
     * url路径
     */
    private String path;

    /**
     * 代理产品关系id
     */
    private Integer agentRelationId;

    /**
     * 代理商代理产品是否代理
     */
    private Boolean isAgent;

    private List<CategoryProductOEMResp> children;

    public CategoryProductOEMResp() {
        children = Lists.newArrayList();
    }
}
