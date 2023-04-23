package com.itshop.web.dao.mysql;

import com.itshop.web.dao.po.TCategoryProduct;
import com.itshop.web.dao.po.TProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TProductDAO {
    int deleteByPrimaryKey(Integer productId);

    int insertSelective(TProduct record);

    TProduct selectByPrimaryKey(Integer productId);

    int updateByPrimaryKeySelective(TProduct record);

    List<TProduct> selectByCategoryId(@Param("list") List<Integer> categoryIds);

    List<TCategoryProduct> selectCategoryProduct();
}