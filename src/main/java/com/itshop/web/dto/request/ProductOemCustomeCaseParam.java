package com.itshop.web.dto.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 产品OEM 应用场景
 *
 * @author liufenglong
 * @date 2022/8/19
 */
@Data
public class ProductOemCustomeCaseParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 图片地址
     */
    private String picUrl;

    /**
     * 案例名称
     */
    private String customeCaseName;

    /**
     * 案例描述
     */
    private String customeCaseDesc;
}
