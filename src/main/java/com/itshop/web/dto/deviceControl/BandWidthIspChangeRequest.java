package com.itshop.web.dto.deviceControl;

import lombok.Data;

import java.io.Serializable;

/**
 * 变更上网业务的出口（运营商）
 */
@Data
public class BandWidthIspChangeRequest implements Serializable {
    /**
     * 上网业务编号
     */
    private String business_id;

    /**
     * 修改后的isp编号
     * 0-未指定 1-联通 2-电信 3-移动 4-教育网 5-科技网
     */
    private Integer isp_id;
}
