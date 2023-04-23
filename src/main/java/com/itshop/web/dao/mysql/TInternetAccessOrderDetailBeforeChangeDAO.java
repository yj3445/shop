package com.itshop.web.dao.mysql;

import com.itshop.web.dao.po.TInternetAccessOrderDetailBeforeChange;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TInternetAccessOrderDetailBeforeChangeDAO {
    int deleteByPrimaryKey(Integer changeDetailId);

    int deleteByChangeId(Integer changeId);

    int insert(TInternetAccessOrderDetailBeforeChange record);

    int insertSelective(TInternetAccessOrderDetailBeforeChange record);

    TInternetAccessOrderDetailBeforeChange selectByPrimaryKey(Integer changeDetailId);

    int updateByPrimaryKeySelective(TInternetAccessOrderDetailBeforeChange record);

    int updateByPrimaryKey(TInternetAccessOrderDetailBeforeChange record);

    int insertBatch(@Param("list") List<TInternetAccessOrderDetailBeforeChange> list);

    List<TInternetAccessOrderDetailBeforeChange> selectByChangeId(@Param("changeId") Integer changeId);
}