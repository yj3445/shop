package com.itshop.web.manager;

import com.itshop.web.dao.mysql.TAgentOemDAO;
import com.itshop.web.dao.po.TAgentOem;
import com.itshop.web.dto.response.AgentOemResp;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author liufenglong
 * @date 2022/7/19
 */
@Service
public class AgentOemManager {

    @Autowired
    TAgentOemDAO tAgentOemDAO;

    /**
     * 保存代理设置
     *
     * @param record
     * @return
     */
    @Transactional(value = "mysqlTransactionManager",rollbackFor = Exception.class)
    public Integer save(TAgentOem record) {
        TAgentOem oldAgent = tAgentOemDAO.selectByOrgId(record.getOrgId());
        if (oldAgent == null) {
            return tAgentOemDAO.insertSelective(record);
        } else {
            oldAgent.setSiteName(record.getSiteName());
            oldAgent.setCustomDomainName(record.getCustomDomainName());
            oldAgent.setCustomDomainEnable(record.getCustomDomainEnable());
            oldAgent.setHomePageBackgroundImage(record.getHomePageBackgroundImage());
            oldAgent.setModifiedTime(new Date());
            oldAgent.setModifiedBy(record.getModifiedBy());
            oldAgent.setMainTitle(record.getMainTitle());
            oldAgent.setSubTitle(record.getSubTitle());
            oldAgent.setBackGroundColor(record.getBackGroundColor());
            return tAgentOemDAO.updateByPrimaryKeySelective(oldAgent);
        }
    }

    /**
     * 根据组织id得到代理设置信息
     *
     * @param orgId
     * @return
     */
    public AgentOemResp selectByOrgId(Integer orgId) {
        AgentOemResp resp = new AgentOemResp();
        resp.setSiteName("ITSHOP");
        resp.setMainTitle("助力企业复工复产");
        resp.setSubTitle("一站式企业IT服务");
        resp.setBackGroundColor("#030710");
        resp.setHomePageBackgroundImage("http://150.242.93.34/fs/itshop/itshop_1659234344183_2022-07-31_991.jpg");
        TAgentOem oldAgent = tAgentOemDAO.selectByOrgId(orgId);
        if (oldAgent != null) {
            BeanUtils.copyProperties(oldAgent, resp);
        }
        return resp;
    }
}
