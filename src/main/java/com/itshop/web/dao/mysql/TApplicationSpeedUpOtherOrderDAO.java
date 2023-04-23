package com.itshop.web.dao.mysql;

import com.itshop.web.dao.po.TApplicationSpeedUpOtherOrder;
import com.itshop.web.dao.po.TInternetAccessUpdateServiceOrder;
import com.itshop.web.dto.request.ApplicationSpeedUpOtherOrderQueryParam;
import com.itshop.web.dto.request.InternetAccessUpdateServiceOrderQueryParam;

import java.util.List;

public interface TApplicationSpeedUpOtherOrderDAO {
    int deleteByPrimaryKey(Integer orderId);

    int insert(TApplicationSpeedUpOtherOrder record);

    int insertSelective(TApplicationSpeedUpOtherOrder record);

    TApplicationSpeedUpOtherOrder selectByPrimaryKey(Integer orderId);

    int updateByPrimaryKeySelective(TApplicationSpeedUpOtherOrder record);

    int updateByPrimaryKey(TApplicationSpeedUpOtherOrder record);

    List<TApplicationSpeedUpOtherOrder> selectByQueryParam(ApplicationSpeedUpOtherOrderQueryParam queryParam);
}