package com.itshop.web.dto.response;

import lombok.Data;

import java.io.Serializable;

/**
 * 应用实例组-响应
 *
 * @author liufenglong
 * @date 2022/5/7
 */
@Data
public class ApplicationGroupResp implements Serializable {

    /**
     * 应用实例id
     */
    private Integer applicationId;

    /**
     * 应用实例英文名
     */
    private String applicationEnName;

    /**
     * 应用实例中文名
     */
    private String applicationCnName;

    /**
     * 应用实例地址(url或ip)
     */
    private String applicationUrl;

    /**
     * 应用实例icon
     */
    private String applicationIcon;

    /**
     * 应用实例组(实例A、实例B、实例C)
     */
    private String applicationGroup;

    /**
     * ping性能指标参考值
     */
    private Integer referencePing;

    /**
     * lost性能指标参考值
     */
    private Integer referenceLost;

    /**
     * trace性能指标参考值
     */
    private Integer referenceTrace;


    private static final long serialVersionUID = 1L;

}
