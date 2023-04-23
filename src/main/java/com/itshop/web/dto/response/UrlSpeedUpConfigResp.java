package com.itshop.web.dto.response;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * url加速列表
 *
 * @author liufenglong
 * @date 2022/5/10
 */
@Data
public class UrlSpeedUpConfigResp  implements Serializable {
    /**
     * url加速唯一id
     */
    private Integer urlSpeedUpId;

    /**
     * url或ip
     */
    private String url;

    /**
     * ping指标参考值
     */
    private Integer referencePing;

    /**
     * lost指标参考值
     */
    private Integer referenceLost;

    /**
     * trace性能参考值
     */
    private Integer referenceTrace;

    /**
     * 速率单位(b,kb,mb,gb,tb,pb)
     */
    private String speedUnit;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 排序
     */
    private Integer orderNum;

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

    /**
     * 单价单位(MONTH-月,YEAR-年)
     */
    private String priceUnit;

    /**
     * 网站英文名称
     */
    private String urlEnName;

    /**
     * 网站中文名称
     */
    private String urlCnName;

    /**
     * 网站分类
     */
    private String urlGroup;
}
