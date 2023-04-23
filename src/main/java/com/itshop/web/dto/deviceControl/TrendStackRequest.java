package com.itshop.web.dto.deviceControl;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liufenglong
 * @date 2022/8/5
 */
@Data
public class TrendStackRequest  implements Serializable {

    /**
     * 业务编号
     */
    private String business_id;

    /**
     * 起始时间 yyyy-MM-dd HH:mm:ss
     */
    private String tmstart;

    /**
     * 结束时间 yyyy-MM-dd HH:mm:ss
     */
    private String tmend;
}
