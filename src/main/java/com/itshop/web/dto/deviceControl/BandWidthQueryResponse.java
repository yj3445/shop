package com.itshop.web.dto.deviceControl;

import lombok.Data;

import java.io.Serializable;

/**
 * 查询带宽响应
 *
 * @author liufenglong
 * @date 2022/5/7
 */
@Data
public class BandWidthQueryResponse implements Serializable {
    /**
     * 带宽 单位：兆
     * */
    private Integer bandwidth;
}
