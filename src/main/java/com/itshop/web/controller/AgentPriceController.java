package com.itshop.web.controller;

import com.itshop.web.annotation.PrintReqResp;
import com.itshop.web.dto.RetResult;
import com.itshop.web.dto.RetWrapper;
import com.itshop.web.dto.request.AgentPriceInsertParam;
import com.itshop.web.dto.request.AgentPriceOrgSaveParam;
import com.itshop.web.dto.request.AgentPriceUpdateParam;
import com.itshop.web.dto.response.AgentPriceOrgResp;
import com.itshop.web.dto.response.AgentPriceResp;
import com.itshop.web.enums.AgentPriceType;
import com.itshop.web.exception.BusinessException;
import com.itshop.web.manager.AgentPriceManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 价格序列设置-控制器
 *
 * @author liufenglong
 * @date 2022/7/20
 */
@Slf4j
@RestController
@RequestMapping("/AgentPrice")
@Api(value = "AgentPriceController", description = "价格序列设置-控制器")
public class AgentPriceController extends BaseController {

    @Autowired
    AgentPriceManager agentPriceManager;

    /**
     * 新增定价序列
     *
     * @param insertParam 新增定价序列
     * @return
     */
    @PrintReqResp
    @PostMapping("/insert")
    @ApiOperation(value = "新增定价序列")
    public RetResult<Integer> insertAgentPrice(@RequestBody AgentPriceInsertParam insertParam) {
        Integer result = agentPriceManager.insertAgentPrice(insertParam, getUserInfoVO());
        return RetWrapper.success(result);
    }

    /**
     * 更新定价序列
     *
     * @param updateParam 更新定价序列
     * @return
     */
    @PrintReqResp
    @PostMapping("/update")
    @ApiOperation(value = "更新定价序列")
    public RetResult<Integer> updateAgentPrice(@RequestBody AgentPriceUpdateParam updateParam) {
        Integer result = agentPriceManager.updateAgentPrice(updateParam, getUserInfoVO());
        return RetWrapper.success(result);
    }

    /**
     * 删除定价序列
     *
     * @param agentPriceId
     * @return
     */
    @PrintReqResp
    @GetMapping("/delete")
    @ApiOperation(value = "删除定价序列")
    public RetResult<Integer> deleteAgentPrice(@RequestParam("agentPriceId") Integer agentPriceId) {
        Integer result = agentPriceManager.deleteAgentPrice(agentPriceId, getUserInfoVO());
        return RetWrapper.success(result);
    }

    /**
     * 根据组织id得到所有的定价序列
     *
     * @return
     */
    @PrintReqResp
    @GetMapping("/selectByOrgId")
    @ApiOperation(value = "根据组织id得到所有的定价序列")
    public RetResult<List<AgentPriceResp>> selectAgentPriceByOrgId() {
        List<AgentPriceResp> result = agentPriceManager.selectAgentPriceByOrgId(getUserInfoVO().getOrgId());
        if(CollectionUtils.isNotEmpty(result)) {
            result = result.stream().filter(p -> Objects.equals(AgentPriceType.AGENT_PRICE.getCode(), p.getPriceType()))
                    .collect(Collectors.toList());
        }
        return RetWrapper.success(result);
    }

    /**
     * 保存定价序列的组织成员
     *
     * @param saveParams 定价序列的组织成员
     */
    @PrintReqResp
    @PostMapping("/priceOrg/save")
    @ApiOperation(value = "保存定价序列的组织成员")
    public RetResult<Void> saveAgentPriceOrg(@RequestParam("agentPriceId") Integer agentPriceId,
                                             @RequestBody List<AgentPriceOrgSaveParam> saveParams) {
        if (agentPriceId == null) {
            throw new BusinessException("[价格序列ID]参数不能为空!");
        }
        agentPriceManager.saveAgentPriceOrg(agentPriceId, saveParams, getUserInfoVO());
        return RetWrapper.success();
    }

    /**
     * 根据价格序列ID得到价格序列的组织成员
     *
     * @param agentPriceId 价格序列ID
     * @return AgentPriceOrgResp
     */
    @GetMapping("/priceOrg/selectByAgentPriceId")
    @ApiOperation(value = "根据价格序列ID得到价格序列的组织成员")
    public RetResult<AgentPriceOrgResp> selectByAgentPriceId(@RequestParam("agentPriceId") Integer agentPriceId) {
        AgentPriceOrgResp result = agentPriceManager.selectByAgentPriceId(agentPriceId, getUserInfoVO());
        return RetWrapper.success(result);
    }

    /**
     * 代理商新增默认价格序列（客户公司价格）
     * @return
     */
    @GetMapping("/agentCompany/insertDefaultPrice")
    @ApiOperation(value = "代理商新增默认价格序列（客户公司价格）")
    public RetResult<Integer> agentCompanyInsertDefaultPrice() {
        Integer result = agentPriceManager.agentCompanyInsertDefaultPrice(null, null);
        return RetWrapper.success(result);
    }
}
