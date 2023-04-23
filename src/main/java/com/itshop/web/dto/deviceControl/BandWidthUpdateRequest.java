package com.itshop.web.dto.deviceControl;

import lombok.Data;

import java.io.Serializable;

/**
 * 修改带宽响应
 *
 * @author liufenglong
 * @date 2022/5/7
 */
@Data
public class BandWidthUpdateRequest implements Serializable {
    /**
     * 上网业务编号
     * */
    private String business_id;
    /**
     *修改后的带宽，单位为兆
     * */
    private Integer bandwidth;
}
