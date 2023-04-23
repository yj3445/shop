package com.itshop.web.dao.mysql;

import com.itshop.web.dao.po.TInternetAccessOrder;
import com.itshop.web.dao.po.TInternetAccessUpdateServiceOrder;
import com.itshop.web.dto.request.InternetAccessOrderQueryParam;
import com.itshop.web.dto.request.InternetAccessUpdateServiceOrderQueryParam;

import java.util.List;

public interface TInternetAccessUpdateServiceOrderDAO {
    int deleteByPrimaryKey(Integer orderId);

    int insert(TInternetAccessUpdateServiceOrder record);

    int insertSelective(TInternetAccessUpdateServiceOrder record);

    TInternetAccessUpdateServiceOrder selectByPrimaryKey(Integer orderId);

    int updateByPrimaryKeySelective(TInternetAccessUpdateServiceOrder record);

    int updateByPrimaryKey(TInternetAccessUpdateServiceOrder record);

    List<TInternetAccessUpdateServiceOrder> selectByQueryParam(InternetAccessUpdateServiceOrderQueryParam queryParam);
}