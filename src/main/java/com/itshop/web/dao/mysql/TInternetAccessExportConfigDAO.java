package com.itshop.web.dao.mysql;

import com.itshop.web.dao.po.TInternetAccessExportConfig;
import com.itshop.web.dao.po.TUrlSpeedUpConfig;

import java.util.List;

public interface TInternetAccessExportConfigDAO {
    int deleteByPrimaryKey(Integer exportId);

    int insert(TInternetAccessExportConfig record);

    int insertSelective(TInternetAccessExportConfig record);

    TInternetAccessExportConfig selectByPrimaryKey(Integer exportId);

    int updateByPrimaryKeySelective(TInternetAccessExportConfig record);

    int updateByPrimaryKey(TInternetAccessExportConfig record);

    List<TInternetAccessExportConfig> selectByQueryParam();
}