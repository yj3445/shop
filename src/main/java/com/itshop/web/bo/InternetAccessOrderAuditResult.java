package com.itshop.web.bo;

import lombok.Data;

@Data
public class InternetAccessOrderAuditResult extends OrderSaveResult {
    /**
     * 带宽(单位:兆)
     */
    private Integer broadBand;

    /**
     * 出口(1智选,2联通,3电信,4移动)
     */
    private Integer export;
}
