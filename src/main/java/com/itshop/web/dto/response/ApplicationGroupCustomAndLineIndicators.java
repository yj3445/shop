package com.itshop.web.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * 应用实例组-自定义-线路指标-响应
 *
 * @author liufenglong
 * @date 2022/5/7
 */
@Data
@ApiModel(value = "应用实例组-自定义-线路指标-响应")
public class ApplicationGroupCustomAndLineIndicators implements Serializable {

    /**
     * 应用实例ID
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
     * 应用实例组(实例D)
     */
    private String applicationGroup;


    /**
     * 应用实例线路指标id
     */
    private Integer applicationLineIndicatorsId;

    /**
     * 线路名称
     */
    private String lineName;

    /**
     * 线路-ping指标参考值
     */
    private Integer referencePing;

    /**
     * 线路-lost指标参考值
     */
    private Integer referenceLost;

    /**
     * 线路-trace性能指标参考值
     */
    private Integer referenceTrace;

    private static final long serialVersionUID = 1L;
}
