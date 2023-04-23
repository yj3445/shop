package com.itshop.web.controller;

import com.itshop.web.annotation.PrintReqResp;
import com.itshop.web.bo.UserInfoVO;
import com.itshop.web.dao.po.TAgentOem;
import com.itshop.web.dto.RetResult;
import com.itshop.web.dto.RetWrapper;
import com.itshop.web.dto.request.AgentOemParam;
import com.itshop.web.dto.response.AgentOemResp;
import com.itshop.web.manager.AgentOemManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * 代理设置-控制器
 *
 * @author liufenglong
 * @date 2022/7/20
 */
@Slf4j
@RestController
@RequestMapping("/AgentOem")
@Api(value = "AgentOemController", description = "代理设置-控制器")
public class AgentOemController  extends BaseController {

    @Autowired
    AgentOemManager agentOemManager;

    /**
     * 根据组织id得到代理设置信息
     *
     * @return RetResult<AgentOemResp>
     */
    @GetMapping("/ByOrgId")
    @ApiOperation(value = "根据组织id得到代理设置信息")
    public RetResult<AgentOemResp> selectByOrgId () {
        AgentOemResp resp = agentOemManager.selectByOrgId(getAgentOrgId());
        return RetWrapper.success(resp);
    }

    /**
     * 保存代理设置
     *
     * @param oemParam 代理设置
     * @return RetResult<Integer>
     */
    @PrintReqResp
    @PostMapping("/save")
    @ApiOperation(value = "保存代理设置")
    public RetResult<Integer> save(@RequestBody AgentOemParam oemParam) throws Exception {
        UserInfoVO userInfoVO = getUserInfoVO();
        Date now = new Date();
        TAgentOem record = new TAgentOem();
        record.setIsDeleted(false);
        record.setOrgId(userInfoVO.getOrgId());
        record.setCreaterBy(userInfoVO.getAppUserInfoId());
        record.setCreateTime(now);
        record.setModifiedBy(userInfoVO.getAppUserInfoId());
        record.setModifiedTime(now);
        record.setSiteName(oemParam.getSiteName());
        record.setCustomDomainName(oemParam.getCustomDomainName());
        record.setCustomDomainEnable(oemParam.getCustomDomainEnable());
        record.setHomePageBackgroundImage(oemParam.getHomePageBackgroundImage());
        record.setMainTitle(oemParam.getMainTitle());
        record.setSubTitle(oemParam.getSubTitle());
        record.setBackGroundColor(oemParam.getBackGroundColor());
        return RetWrapper.success(agentOemManager.save(record));
    }
}
