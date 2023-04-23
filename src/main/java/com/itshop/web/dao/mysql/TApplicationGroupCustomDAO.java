package com.itshop.web.dao.mysql;

import com.itshop.web.dao.po.TApplicationGroupCustom;
import com.itshop.web.dto.response.ApplicationGroupCustomAndLineIndicators;

import java.util.List;

/**
 * 自定义应用组 数据处理
 *
 * @author liufenglong
 * @date 2022/5/7
 */
public interface TApplicationGroupCustomDAO {
    int deleteByPrimaryKey(Integer applicationId);

    int insert(TApplicationGroupCustom record);

    int insertSelective(TApplicationGroupCustom record);

    TApplicationGroupCustom selectByPrimaryKey(Integer applicationId);

    int updateByPrimaryKeySelective(TApplicationGroupCustom record);

    int updateByPrimaryKey(TApplicationGroupCustom record);

    List<ApplicationGroupCustomAndLineIndicators> selectTApplicationGroupCustomAndLineIndicators();
}