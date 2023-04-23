package com.itshop.web.dto.deviceControl;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 防火墙添加端口请求
 *
 * @author liufenglong
 * @date 2022/5/7
 */
@Data
public class FireWallAddPortRequest  implements Serializable {
    /**
     * 上网业务编号
     */
    private String business_id;
    /**
     * 端口列表
     */
    private List<Integer> portlist;
    /**
     *
     * 协议类型，默认是“any”
     * “Any”、“TCP”、“UDP”、“PPP”
     */
    private String protocol;
}
