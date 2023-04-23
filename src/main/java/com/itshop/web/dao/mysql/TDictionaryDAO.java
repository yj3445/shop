package com.itshop.web.dao.mysql;

import com.itshop.web.dao.po.TDictionary;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TDictionaryDAO {
    int deleteByPrimaryKey(Integer dictionaryId);

    int insert(TDictionary record);

    int insertSelective(TDictionary record);

    TDictionary selectByPrimaryKey(Integer dictionaryId);

    int updateByPrimaryKeySelective(TDictionary record);

    int updateByPrimaryKey(TDictionary record);

    List<TDictionary> selectByDictionaryTypeId(@Param("dictionaryTypeId") Integer dictionaryTypeId);
}