package com.itshop.web.dto.deviceControl;

import lombok.Data;

import java.io.Serializable;

/**
 * 查询带宽请求
 *
 * @author liufenglong
 * @date 2022/5/7
 */
@Data
public class BandWidthQueryRequest implements Serializable {
    /**
     * 上网业务编号
     */
    private String business_id;
}

