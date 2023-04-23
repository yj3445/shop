package com.itshop.web.util;

import org.apache.commons.lang3.ArrayUtils;

/**
 * 订单工具类
 *
 * @author liufenglong
 * @date 2022/8/9
 */
public class OrderTypeUtil {

    public static int[] INTERNET_ACCESS_PRODUCT = new int[]{1, 2, 3, 4, 5};
    public static int[] INTERNET_ACCESS_UPDATE_SERVICE_PRODUCT = new int[]{1, 2, 3, 4, 5};
    public static int[] URL_SPEED_UP_PRODUCT = new int[]{1, 2, 3, 4, 5};
    public static int[] OTHER_APPLICATION_SPEED_UP_PRODUCT = new int[]{1, 2, 3, 4, 5};
    public static int[] IDC_FIRE_WALL_PRODUCT = new int[]{1, 2, 3, 4, 5};

    /**
     * 根据产品id判断订单是否是【互联网接入】类型的订单
     *
     * @param productId
     * @return
     */
    public static boolean isInternetAccessOrder(Integer productId) {
        return ArrayUtils.contains(INTERNET_ACCESS_PRODUCT, productId);
    }

    /**
     * 根据产品id判断订单是否是【互联网接入(升级服务)】类型的订单
     *
     * @param productId
     * @return
     */
    public static boolean isInternetAccessUpdateServiceOrder(Integer productId) {
        return ArrayUtils.contains(INTERNET_ACCESS_UPDATE_SERVICE_PRODUCT, productId);
    }

    /**
     * 根据产品id判断订单是否是【URL加速】类型的订单
     *
     * @param productId
     * @return
     */
    public static boolean isUrlSpeedUpOrder(Integer productId) {
        return ArrayUtils.contains(URL_SPEED_UP_PRODUCT, productId);
    }

    /**
     * 根据产品id判断订单是否是【其他应用加速】类型的订单
     *
     * @param productId
     * @return
     */
    public static boolean isOtherApplicationSpeedUpOrder(Integer productId) {
        return ArrayUtils.contains(OTHER_APPLICATION_SPEED_UP_PRODUCT, productId);
    }

    /**
     * 根据产品id判断订单是否是【防火墙】类型的订单
     *
     * @param productId
     * @return
     */
    public static boolean isIdcFireWallOrder(Integer productId) {
        return ArrayUtils.contains(IDC_FIRE_WALL_PRODUCT, productId);
    }
}
