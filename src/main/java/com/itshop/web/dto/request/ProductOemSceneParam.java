package com.itshop.web.dto.request;

import lombok.Data;

import java.io.Serializable;

/**
 * OEM产品 应用场景
 *
 * @author liufenglong
 * @date 2022/8/19
 */
@Data
public class ProductOemSceneParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 场景名称
     */
    private String sceneName;

    /**
     * 场景描述
     */
    private String sceneDesc;

    /**
     * 图片地址
     */
    private String picUrl;
}
