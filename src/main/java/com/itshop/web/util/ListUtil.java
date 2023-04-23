package com.itshop.web.util;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * List帮助类
 *
 * @author liufenglong
 * @date 2022/5/7
 */
public class ListUtil {
    /**
     * 使用list自带的sort方法先进性排序，然后转成toString去判断两个集合是否相等
     * 方法6
     */
    public static boolean equalsStr(List<String> list, List<String> list1) {
        if (list != null && list1 != null) {
            long st = System.currentTimeMillis();
            list.sort(Comparator.comparing(String::hashCode));
            list1.sort(Comparator.comparing(String::hashCode));
            System.out.println("消耗时间为(毫秒)： " + (System.currentTimeMillis() - st));
            return list.toString().equals(list1.toString());
        } else {
            return list == null && list1 == null;
        }
    }

    /**
     * 使用list自带的sort方法先进性排序，然后转成toString去判断两个集合是否相等
     * 方法6
     */
    public static boolean equalsInt(List<Integer> list, List<Integer> list1) {
        if (list != null && list1 != null) {
            long st = System.currentTimeMillis();
            list.sort(Comparator.comparingInt(Integer::intValue));
            list1.sort(Comparator.comparingInt(Integer::intValue));
            System.out.println("消耗时间为(毫秒)： " + (System.currentTimeMillis() - st));
            return list.toString().equals(list1.toString());
        } else {
            return list == null && list1 == null;
        }
    }

    public static <T> boolean equals(Collection<T> lhs, Collection<T> rhs) {
        if (lhs != null && rhs != null) {
            return lhs.size() == rhs.size() && lhs.containsAll(rhs) && rhs.containsAll(lhs);
        } else {
            return lhs == null && rhs == null;
        }
    }
}
