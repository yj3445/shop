package com.itshop.web.dao.mysql;

import com.itshop.web.dao.po.TApplicationGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 应用实例组 数据处理
 *
 * @author liufenglong
 * @date 2022/5/7
 */
public interface TApplicationGroupDAO {
    int deleteByPrimaryKey(Integer applicationId);

    int insert(TApplicationGroup record);

    int insertSelective(TApplicationGroup record);

    TApplicationGroup selectByPrimaryKey(Integer applicationId);

    int updateByPrimaryKeySelective(TApplicationGroup record);

    int updateByPrimaryKey(TApplicationGroup record);

    List<TApplicationGroup> selectTApplicationGroups(@Param("applicationGroup") String applicationGroup );
}