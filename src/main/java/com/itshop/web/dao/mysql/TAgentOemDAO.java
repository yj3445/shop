package com.itshop.web.dao.mysql;

import com.itshop.web.dao.po.TAgentOem;
import org.apache.ibatis.annotations.Param;

public interface TAgentOemDAO {

    int deleteByPrimaryKey(Integer agentOemId, @Param("modifiedBy") Integer modifiedBy);

    int insertSelective(TAgentOem record);

    TAgentOem selectByPrimaryKey(Integer agentOemId);

    TAgentOem selectByOrgId(@Param("orgId") Integer orgId);

    int updateByPrimaryKeySelective(TAgentOem record);
}