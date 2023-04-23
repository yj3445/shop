package com.itshop.web.dao.mysql;

import com.itshop.web.dao.po.TDictionaryType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TDictionaryTypeDAO {
    int deleteByPrimaryKey(Integer dictionaryTypeId);

    int insert(TDictionaryType record);

    int insertSelective(TDictionaryType record);

    TDictionaryType selectByPrimaryKey(Integer dictionaryTypeId);

    int updateByPrimaryKeySelective(TDictionaryType record);

    int updateByPrimaryKey(TDictionaryType record);

    List<TDictionaryType> selectByQueryParam();

}