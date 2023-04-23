package com.itshop.web.constants;

import com.google.common.collect.Maps;
import org.apache.logging.log4j.util.Strings;

import java.util.Map;

public class ProductItemConstants {

    private static Map<String, Map<Integer,String>> CONFIG = Maps.newHashMap();

    private static String PAYMENT_CYCLE="PAYMENT_CYCLE";
    private static String EXPORT="EXPORT";
    private static String PAYMENT_METHOD="PAYMENT_METHOD";

    private static String IP_NUM="IP_NUM";

    private static String APPLICATION="APPLICATION";

    static {
        Map<Integer,String> map = Maps.newHashMap();
        map.put(1,"智选");
        map.put(2,"联通");
        map.put(3,"电信");
        map.put(4,"移动");
        map.put(5,"教育网");
        map.put(6,"科技网");
        CONFIG.put(EXPORT,map);

        map = Maps.newHashMap();
        map.put(1,"月");
        map.put(2,"季");
        map.put(3,"年");
        CONFIG.put(PAYMENT_CYCLE,map);

        map = Maps.newHashMap();
        map.put(1,"预付");
        map.put(2,"后付");
        CONFIG.put(PAYMENT_METHOD,map);

        map = Maps.newHashMap();
        map.put(1,"智选");
        map.put(2,"自选");
        CONFIG.put(IP_NUM,map);

        map = Maps.newHashMap();
        map.put(1,"实例组A");
        map.put(2,"实例组B");
        map.put(3,"实例组C");
        map.put(4,"自定义");
        CONFIG.put(APPLICATION,map);
    }

    public static String getApplicationDesc(Integer value){
        return GetByValue(APPLICATION,value);
    }

    public static String getPaymentCycleDesc(Integer value){
        return GetByValue(PAYMENT_CYCLE,value);
    }

    public static String getExportDesc(Integer value){
        return GetByValue(EXPORT,value);
    }

    public static String getPaymentMethodDesc(Integer value){
        return GetByValue(PAYMENT_METHOD,value);
    }

    public static String getIpNum(Integer value){
        return GetByValue(IP_NUM,value);
    }

    private static String GetByValue(String key,Integer value) {
        Map<Integer, String> map = CONFIG.get(key);
        if (map != null) {
            return map.getOrDefault(value, Strings.EMPTY);
        }
        return Strings.EMPTY;
    }
}
