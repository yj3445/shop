package com.itshop.web.dto.deviceControl;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author liufenglong
 * @date 2022/8/5
 */
@Data
public class TrendStackResponse   implements Serializable {
    /**
     * 上行速率最大值
     */
    private String maxupbps;
    /**
     * 下行速率最大值
     */
    private String maxdownbps;
    /**
     * 上行速率最小值
     */
    private String minupbps;
    /**
     * 下行速率最小值
     */
    private String mindownbps;
    /**
     * 上行速率平均值
     */
    private String avgupbps;
    /**
     * 下行速率平均值
     */
    private String avgdownbps;
    /**
     * 上行速率总值
     */
    private String allupbytes;
    /**
     * 下行速率总值
     */
    private String alldownbytes;

    /**
     * 上行
     */
    private List<TrendStackData> up;

    /**
     * 下行
     */
    private List<TrendStackData> down;

    /**
     * 连接数
     */
    private List<TrendStackData> flow;

    /**
     * 时间序列(时间戳)
     */
    private List<Long> xdate;

    /**
     *
     */
    @Data
    public static class TrendStackData implements Serializable {
        private List<Long> data;

        private String name;

        private String type;

        private String stack;
    }
}
