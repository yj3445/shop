package com.itshop.web.dto.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liufenglong
 * @date 2022/8/25
 */
@Data
public class ProductOemCustomeCaseResp implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer productOemCustomeCaseId;

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
