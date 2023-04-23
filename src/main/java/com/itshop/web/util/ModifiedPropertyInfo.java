package com.itshop.web.util;

import com.itshop.web.constants.SnowConstants;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * https://javers.org/documentation/getting-started/#create-javers-instance
 */
@Data
public class ModifiedPropertyInfo implements Serializable {

    /**
     * 发生变化的属性名称
     */
    private String propertyName;
    /**
     * 发生变化的属性注释
     */
    private String propertyComment;
    /**
     * 修改前的值
     */
    private Object oldValue;
    /**
     * 修改后的值
     */
    private Object newValue;

    public String getOldValueString(){
        return objectToString(this.oldValue);
    }

    public String getNewValueString(){
        return objectToString(this.newValue);
    }

    private String objectToString(Object obj) {
        if (obj != null) {
            if (obj instanceof BigDecimal) {
                return ((BigDecimal) obj).toPlainString();
            } else if (obj instanceof Date) {
                LocalDateTime localDateTime= DateUtil.date2LocalDateTime((Date) obj);
                if(localDateTime.getHour() == 0
                    && localDateTime.getMinute() ==0
                    && localDateTime.getSecond() ==0 ){
                    return localDateTime.format(SnowConstants.DATE_WITH_HYPHEN_FORMATTER);
                }
                else {
                    return localDateTime.format(SnowConstants.DATE_HOUR_MiN_SEC_WITH_HYPHEN_FORMATTER);
                }
            } else {
                return obj.toString();
            }
        }
        return null;
    }
}

