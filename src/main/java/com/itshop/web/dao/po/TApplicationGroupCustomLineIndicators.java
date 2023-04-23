package com.itshop.web.dao.po;

import java.io.Serializable;

/**
 * t_application_group_custom_line_indicators
 *
 * @author liufenglong
 * @date 2022/5/7
 */
public class TApplicationGroupCustomLineIndicators implements Serializable {
    private Integer applicationLineIndicatorsId;

    /**
     * 实例id
     */
    private Integer applicationId;

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

    public Integer getApplicationLineIndicatorsId() {
        return applicationLineIndicatorsId;
    }

    public void setApplicationLineIndicatorsId(Integer applicationLineIndicatorsId) {
        this.applicationLineIndicatorsId = applicationLineIndicatorsId;
    }

    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public Integer getReferencePing() {
        return referencePing;
    }

    public void setReferencePing(Integer referencePing) {
        this.referencePing = referencePing;
    }

    public Integer getReferenceLost() {
        return referenceLost;
    }

    public void setReferenceLost(Integer referenceLost) {
        this.referenceLost = referenceLost;
    }

    public Integer getReferenceTrace() {
        return referenceTrace;
    }

    public void setReferenceTrace(Integer referenceTrace) {
        this.referenceTrace = referenceTrace;
    }
}