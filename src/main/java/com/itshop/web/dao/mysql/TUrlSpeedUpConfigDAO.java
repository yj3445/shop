package com.itshop.web.dao.mysql;

import com.itshop.web.constants.CacheKeyConstants;
import com.itshop.web.dao.po.TIdcFirewallOrder;
import com.itshop.web.dao.po.TUrlSpeedUpConfig;
import com.itshop.web.dto.request.UrlSpeedUpConfigQueryParam;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface TUrlSpeedUpConfigDAO {
    int deleteByPrimaryKey(Integer urlSpeedUpId);

    int insert(TUrlSpeedUpConfig record);

    int insertSelective(TUrlSpeedUpConfig record);

    TUrlSpeedUpConfig selectByPrimaryKey(Integer urlSpeedUpId);

    int updateByPrimaryKeySelective(TUrlSpeedUpConfig record);

    int updateByPrimaryKey(TUrlSpeedUpConfig record);

    List<TUrlSpeedUpConfig> selectByQueryParam(UrlSpeedUpConfigQueryParam queryParam);
}