package com.itshop.web.dto.request;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 应用加速-订单-保存-请求
 *
 * @author liufenglong
 * @date 2022/5/7
 */
@Data
public class ApplicationSpeedUpOrderSaveParam  implements Serializable {

    @Data
    public static class UrlConfig  implements Serializable {
        /**
         * url加速唯一id
         */
        private Integer urlSpeedUpId;

        /**
         * 速率单位(B,KB,MB,GB,TB,PB)
         */
        private String speedUnit;

        /**
         * 单价
         */
        private BigDecimal price;

        /**
         * url
         */
        private String url;

        /**
         * 速率
         */
        private Integer speed;

        /**
         * 时间计量单位(1-月,2-季,3-年,4-日,5-小时)
         */
        private Integer unit;

        /**
         * 使用时长
         */
        private Integer duration;

        /**
         * 开始时间
         */
        private Date startTime;

        /**
         * 结束时间
         */
        private Date endTime;

        /**
         * 价格单位(MONTH-月,YEAR-年,HOUR-小时,DAY-天)
         */
        private String priceUnit;
    }

    /**
     * 应用加速订单id
     */
    private Integer orderId;

    /**
     * 缴费周期(1月,2季,3年)
     */
    private Integer paymentCycle;

    /**
     * 付费方式(1预付,2后付)
     */
    private Integer paymentMethod;

    /**
     * 互联网接入-订单id
     */
    private Integer internetAccessOrderId;

    /**
     * 创建人
     */
    private Integer createrBy;

    /**
     * url配置列表集合
     */
    private List<UrlConfig> urlConfigs;

    /**
     * 互联网接入-订单类型（本地固定传 “CUSTOM”）
     */
    private String internetAccessOrderType;

    /**
     * 商品id
     */
    private Integer productId;
}
