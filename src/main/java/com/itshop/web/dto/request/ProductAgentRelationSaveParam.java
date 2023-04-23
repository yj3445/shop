package com.itshop.web.dto.request;

import lombok.Data;

import java.io.Serializable;

/**
 *
 * 代理商-代理产品关系表
 *
 * @author liufenglong
 * @date 2022/8/8
 */
@Data
public class ProductAgentRelationSaveParam   implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 产品id
     */
    private Integer productId;


    /**
     * 是否代理
     */
    private Boolean isAgent;
}
