package com.itshop.web.dto.price;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ApplicationSpeedUpPriceItem  implements Serializable {
    /**
     * url加速唯一id
     */
    private Integer urlSpeedUpId;

    /**
     * 单价
     * <p>
     * price/speedUnit/priceUnit
     * 形如：50/MB/月
     */
    private BigDecimal price;

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
     * 速率单位(B,KB,MB,GB,TB,PB)
     */
    private String speedUnit;

    /**
     * 价格单位(MONTH-月,YEAR-年,HOUR-小时,DAY-天)
     */
    private String priceUnit;

    /**
     * 总价
     */
    private BigDecimal totalPrice;
}
