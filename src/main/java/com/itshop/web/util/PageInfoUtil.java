package com.itshop.web.util;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

/**
 * 分页帮助类
 *
 * @author liufenglong
 * @date 2022/5/7
 */
@Slf4j
public class PageInfoUtil {

    /**
     * 分页转化
     *
     * @param sPageInfo 原始分页对象
     * @param tClass  目标对象
     * @param <T> 目标类型
     * @param <S> 原始类型
     * @return PageInfo
     */
    public static <T, S> PageInfo<T> convert(PageInfo<S> sPageInfo, Class<T> tClass) {
        PageInfo<T> tPageInfo = new PageInfo<>();
        BeanUtils.copyProperties(sPageInfo, tPageInfo);
        tPageInfo.setList(Lists.newArrayList());
        if (!CollectionUtils.isEmpty(sPageInfo.getList())) {
            sPageInfo.getList().forEach(s -> {
                String errorMsg = String.format("%s newInstance error!", tClass.getName());
                T t = null;
                try {
                    t = tClass.newInstance();
                } catch (InstantiationException e) {
                    log.error(errorMsg, e);
                } catch (IllegalAccessException e) {
                    log.error(errorMsg, e);
                }
                Assert.notNull(t, errorMsg);
                BeanUtils.copyProperties(s, t);
                tPageInfo.getList().add(t);
            });
        }
        return tPageInfo;
    }
}
