package com.itshop.web.dao.mysql;

import com.itshop.web.dao.po.TCategory;

import java.util.List;

/**
 * 产品类别 数据处理
 *
 * @author liufenglong
 * @date 2022/5/7
 */
public interface TCategoryDAO {
    int deleteByPrimaryKey(Integer categoryId);

    int insertSelective(TCategory record);

    TCategory selectByPrimaryKey(Integer categoryId);

    int updateByPrimaryKeySelective(TCategory record);

    List<TCategory> selectHighLevelCategory();

    List<TCategory> selectLowLevelCategory();
}