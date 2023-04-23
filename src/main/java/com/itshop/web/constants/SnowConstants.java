package com.itshop.web.constants;

import java.time.format.DateTimeFormatter;

/**
 * 系统常量配置
 *
 * @author liufenglong
 * @date 2022/5/7
 */
public class SnowConstants {
    public static final String COLON = ":";

    /**
     * 每日自增id哈希key
     */
    public static final String INCREMENT_ID_HASH_KEY = "daily.auto.increment.id.";
    /**
     * 每日自增id哈希key-到期秒数
     */
    public static final long INCREMENT_ID_HASH_KEY_EXPIRE_SECONDS = 24 * 60 * 60;

    /**
     * 业务编号-哈希字段
     **/
    public static final String HASH_FIELD_BUSINESS_ID = "BUSINESS_ID";

    /**
     * 互联网接入订单-哈希字段
     **/
    public static final String HASH_FIELD_INTERNET_ACCESS_ORDER = "INTERNET_ACCESS_ORDER";
    /**
     * idc防火墙-订单-哈希字段
     **/
    public static final String HASH_FIELD_IDC_FIREWALL_ORDER = "IDC_FIREWALL_ORDER";

    /**
     * 应用加速-订单-哈希字段
     **/
    public static final String HASH_FIELD_APPLICATION_SPEED_UP_ORDER = "APPLICATION_SPEED_UP_ORDER";

    /**
     * 每日自增id最大长度
     */
    public static final int INCREMENT_ID_MAX_LENGTH = 4;

    /**
     * yyyy-MM-dd 格式
     */
    public static final DateTimeFormatter DATE_WITH_HYPHEN_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * yyyy-MM-dd HH:mm:ss 格式
     */
    public static final DateTimeFormatter DATE_HOUR_MiN_SEC_WITH_HYPHEN_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 不带横杠的日期格式
     */
    public static final DateTimeFormatter DATE_ONLY_NUMBER_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");


    /**
     * 带斜线的日期格式
     */
    public static final DateTimeFormatter DATE_WITH_SLASH_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    public static final DateTimeFormatter YYYYMM = DateTimeFormatter.ofPattern("yyyyMM");

    public static final String  USER_DOMAIN ="AUC";
}
