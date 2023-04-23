package com.itshop.web.dao.mysql;

import com.itshop.web.dao.po.TApplicationSpeedUpOrder;
import com.itshop.web.dto.request.ApplicationSpeedUpOrderQueryParam;

import java.util.List;

public interface TApplicationSpeedUpOrderDAO {
    int deleteByPrimaryKey(Integer orderId);

    int insert(TApplicationSpeedUpOrder record);

    int insertSelective(TApplicationSpeedUpOrder record);

    TApplicationSpeedUpOrder selectByPrimaryKey(Integer orderId);

    int updateByPrimaryKeySelective(TApplicationSpeedUpOrder record);

    int updateByPrimaryKey(TApplicationSpeedUpOrder record);

    List<TApplicationSpeedUpOrder> selectByQueryParam(ApplicationSpeedUpOrderQueryParam queryParam);
}