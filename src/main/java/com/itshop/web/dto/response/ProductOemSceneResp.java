package com.itshop.web.dto.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liufenglong
 * @date 2022/8/25
 */
@Data
public class ProductOemSceneResp  implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer productOemSceneId;

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
