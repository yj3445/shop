package com.itshop.web.dao.mysql;

import com.itshop.web.dao.po.TInternetAccessOrder;
import com.itshop.web.dto.request.InternetAccessOrderQueryParam;

import java.util.List;

/**
 * 互联网接入-自定义-订单 数据处理
 *
 * @author liufenglong
 * @date 2022/5/7
 */
public interface TInternetAccessOrderDAO {
    int deleteByPrimaryKey(Integer orderId);

    int insertSelective(TInternetAccessOrder record);

    TInternetAccessOrder selectByPrimaryKey(Integer orderId);

    int updateByPrimaryKeySelective(TInternetAccessOrder record);

    List<TInternetAccessOrder> selectByQueryParam(InternetAccessOrderQueryParam queryParam);
}