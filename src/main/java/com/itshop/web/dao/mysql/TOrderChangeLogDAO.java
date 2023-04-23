package com.itshop.web.dao.mysql;

import com.itshop.web.dao.po.TInternetAccessOrderDetail;
import com.itshop.web.dao.po.TOrderChangeLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TOrderChangeLogDAO {
    int deleteByPrimaryKey(Integer changeLogId);

    int insert(TOrderChangeLog record);

    int insertSelective(TOrderChangeLog record);

    TOrderChangeLog selectByPrimaryKey(Integer changeLogId);

    int updateByPrimaryKeySelective(TOrderChangeLog record);

    int updateByPrimaryKey(TOrderChangeLog record);

    int insertBatch(@Param("list") List<TOrderChangeLog> list);
}