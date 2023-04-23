package com.itshop.web.dao.mysql;

import com.itshop.web.dao.po.TProductItem;

import java.util.List;

public interface TProductItemDAO {
    int deleteByPrimaryKey(Integer productItemId);

    int insert(TProductItem record);

    int insertSelective(TProductItem record);

    TProductItem selectByPrimaryKey(Integer productItemId);

    int updateByPrimaryKeySelective(TProductItem record);

    int updateByPrimaryKey(TProductItem record);

    List<TProductItem> selectByProductId(Integer productId);
}