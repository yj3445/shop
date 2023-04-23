package com.itshop.web.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 互联网接入-出口配置
 *
 * @author liufenglong
 * @date 2022/5/13
 */
@Data
public class InternetAccessExportConfigResp  implements Serializable {
    /**
     * 出口ID
     */
    private Integer exportId;

    /**
     * 出口名称
     */
    private String name;

    /**
     * 创建人
     */
    private Integer createrBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 操作人
     */
    private Integer modifiedBy;

    /**
     * 更新时间
     */
    private Date modifiedTime;

}
