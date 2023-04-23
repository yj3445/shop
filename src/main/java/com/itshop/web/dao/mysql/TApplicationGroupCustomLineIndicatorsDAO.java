package com.itshop.web.dao.mysql;

import com.itshop.web.dao.po.TApplicationGroupCustomLineIndicators;

/**
 * 自定义应用组-线路指标 数据处理
 *
 * @author liufenglong
 * @date 2022/5/7
 */
public interface TApplicationGroupCustomLineIndicatorsDAO {
    int deleteByPrimaryKey(Integer applicationLineIndicatorsId);

    int insert(TApplicationGroupCustomLineIndicators record);

    int insertSelective(TApplicationGroupCustomLineIndicators record);

    TApplicationGroupCustomLineIndicators selectByPrimaryKey(Integer applicationLineIndicatorsId);

    int updateByPrimaryKeySelective(TApplicationGroupCustomLineIndicators record);

    int updateByPrimaryKey(TApplicationGroupCustomLineIndicators record);
}