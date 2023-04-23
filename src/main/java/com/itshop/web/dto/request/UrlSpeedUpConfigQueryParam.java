package com.itshop.web.dto.request;

import lombok.Data;

import java.io.Serializable;

/**
 * url加速列表查询参数
 */
@Data
public class UrlSpeedUpConfigQueryParam  implements Serializable {
    /**
     * url或ip
     */
    private String url;

    /**
     * URL产品ID
     */
    private Integer productId;
}
