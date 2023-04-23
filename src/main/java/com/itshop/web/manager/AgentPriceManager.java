package com.itshop.web.manager;

import com.google.common.collect.Lists;
import com.itshop.web.bo.UserInfoVO;
import com.itshop.web.dao.auth.AuthorizationRepository;
import com.itshop.web.dao.mysql.TAgentPriceDAO;
import com.itshop.web.dao.mysql.TAgentPriceOrgDAO;
import com.itshop.web.dao.mysql.TOrganizationalDAO;
import com.itshop.web.dao.mysql.TProductOemItemPriceDAO;
import com.itshop.web.dao.po.TAgentPrice;
import com.itshop.web.dao.po.TAgentPriceOrg;
import com.itshop.web.dao.po.TOrganizational;
import com.itshop.web.dto.auth.OrganizationalDetailVO;
import com.itshop.web.dto.auth.Result;
import com.itshop.web.dto.request.AgentPriceInsertParam;
import com.itshop.web.dto.request.AgentPriceOrgSaveParam;
import com.itshop.web.dto.request.AgentPriceUpdateParam;
import com.itshop.web.dto.response.AgentPriceOrgResp;
import com.itshop.web.dto.response.AgentPriceResp;
import com.itshop.web.enums.AgentPriceType;
import com.itshop.web.enums.OrgTypeEnum;
import com.itshop.web.exception.BusinessException;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 价格序列设置管理器
 *
 * @author liufenglong
 * @date 2022/7/23
 */
@Service
public class AgentPriceManager {
    @Autowired
    TAgentPriceDAO agentPriceDAO;

    @Autowired
    TAgentPriceOrgDAO agentPriceOrgDAO;

    @Autowired
    TProductOemItemPriceDAO productOemItemPriceDAO;

    @Autowired
    AuthorizationRepository authorizationRepository;

    @Autowired
    TOrganizationalDAO tOrganizationalDAO;

    /**
     * 新增定价序列
     *
     * @param insertParam 新增定价序列
     * @param userInfoVO 当前登录用户
     * @return
     */
    @Transactional(value = "mysqlTransactionManager", rollbackFor = Exception.class)
    public Integer insertAgentPrice(AgentPriceInsertParam insertParam, UserInfoVO userInfoVO) {
        if (agentPriceDAO.countByOrgIdAndPriceName(userInfoVO.getOrgId(), insertParam.getPriceName()) > 0) {
            throw new BusinessException("价格序列名称已存在!");
        }
        Date now = new Date();
        TAgentPrice agentPrice = new TAgentPrice();
        agentPrice.setOrgId(userInfoVO.getOrgId());
        agentPrice.setPriceName(insertParam.getPriceName());
        agentPrice.setPriceDesc(insertParam.getPriceDesc());
        agentPrice.setPriceType(insertParam.getPriceType());
        agentPrice.setCreaterBy(userInfoVO.getAppUserInfoId());
        agentPrice.setCreateTime(now);
        agentPrice.setModifiedBy(userInfoVO.getAppUserInfoId());
        agentPrice.setModifiedTime(now);
        agentPrice.setIsDeleted(false);
        return agentPriceDAO.insertSelective(agentPrice);
    }

    /**
     * 更新定价序列
     *
     * @param updateParam 更新定价序列
     * @param userInfoVO 当前登录用户
     * @return
     */
    @Transactional(value = "mysqlTransactionManager", rollbackFor = Exception.class)
    public Integer updateAgentPrice(AgentPriceUpdateParam updateParam, UserInfoVO userInfoVO) {
        TAgentPrice old = agentPriceDAO.selectByPrimaryKey(updateParam.getAgentPriceId());
        if (!Objects.equals(old.getOrgId(), userInfoVO.getOrgId())) {
            throw new BusinessException("您无权操作该价格序列!");
        }
        if (agentPriceDAO.countByOrgIdAndPriceNameAndAgentPriceId(userInfoVO.getOrgId(), updateParam.getPriceName(), updateParam.getAgentPriceId()) > 0) {
            throw new BusinessException("价格序列名称已存在!");
        }
        Date now = new Date();
        TAgentPrice agentPrice = new TAgentPrice();
        agentPrice.setAgentPriceId(updateParam.getAgentPriceId());
        agentPrice.setPriceName(updateParam.getPriceName());
        agentPrice.setPriceDesc(updateParam.getPriceDesc());
        agentPrice.setPriceType(updateParam.getPriceType());
        agentPrice.setModifiedBy(userInfoVO.getAppUserInfoId());
        agentPrice.setModifiedTime(now);
        return agentPriceDAO.updateByPrimaryKeySelective(agentPrice);
    }

    /**
     * 删除定价序列
     *
     * @param agentPriceId
     * @param userInfoVO
     * @return
     */
    @Transactional(value = "mysqlTransactionManager", rollbackFor = Exception.class)
    public Integer deleteAgentPrice(Integer agentPriceId, UserInfoVO userInfoVO) {
        TAgentPrice old = agentPriceDAO.selectByPrimaryKey(agentPriceId);
        if (!Objects.equals(old.getOrgId(), userInfoVO.getOrgId())) {
            throw new BusinessException("您无权操作该价格序列!");
        }
        List<TAgentPriceOrg> currentPriceTargetOrgIds = agentPriceOrgDAO.selectByAgentPriceId(agentPriceId);
        if(CollectionUtils.isNotEmpty(currentPriceTargetOrgIds)){
            throw new BusinessException("已经有代理商分配了该价格序列,无法删除!");
        }
        agentPriceDAO.deleteByPrimaryKey(agentPriceId, userInfoVO.getAppUserInfoId());
        return productOemItemPriceDAO.deleteByAgentPriceId(agentPriceId, userInfoVO.getAppUserInfoId());
    }

    /**
     * 根据组织id得到所有的定价序列
     *
     * @param orgId 组织id
     * @return
     */
    public List<AgentPriceResp> selectAgentPriceByOrgId(Integer orgId) {
        List<AgentPriceResp> result = Lists.newArrayList();
        List<TAgentPrice> agentPriceList = agentPriceDAO.selectByOrgId(orgId);
        if (CollectionUtils.isNotEmpty(agentPriceList)) {
            agentPriceList.forEach(p -> {
                AgentPriceResp agentPriceResp = new AgentPriceResp();
                BeanUtils.copyProperties(p, agentPriceResp);
                result.add(agentPriceResp);
            });
        }
        return result;
    }

    /**
     * 保存定价序列的组织成员
     *
     * @param saveParams 定价序列的组织成员
     * @param userInfoVO 当前登录用户
     */
    @Transactional(value = "mysqlTransactionManager", rollbackFor = Exception.class)
    public void saveAgentPriceOrg(@NotNull Integer agentPriceId,
                                  List<AgentPriceOrgSaveParam> saveParams,UserInfoVO userInfoVO) {
        if (CollectionUtils.isEmpty(saveParams)) {
            agentPriceOrgDAO.deleteByAgentPriceId(agentPriceId);
            return;
        }
        TAgentPrice old = agentPriceDAO.selectByPrimaryKey(agentPriceId);
        if (!Objects.equals(old.getOrgId(), userInfoVO.getOrgId())) {
            throw new BusinessException("您无权操作该价格序列!");
        }
        List<TOrganizational> organizationals= tOrganizationalDAO.selectAllOrganizational();
        List<Integer> targetOrgIds = saveParams.stream().map(AgentPriceOrgSaveParam::getTargetOrgId).collect(Collectors.toList());
        List<TAgentPriceOrg> existAgentPriceOrgs = agentPriceOrgDAO.selectByTargetOrgIdUnAgentPriceId(agentPriceId, targetOrgIds);
        if (CollectionUtils.isNotEmpty(existAgentPriceOrgs)) {
            List<Integer> agentPriceIds = existAgentPriceOrgs.stream().map(TAgentPriceOrg::getAgentPriceId).collect(Collectors.toList());
            List<TAgentPrice> agentPriceList = agentPriceDAO.selectByAgentPriceIds(agentPriceIds);
            StringBuilder message = new StringBuilder();
            existAgentPriceOrgs.forEach(p -> {
                String orgName = organizationals.stream().filter(t -> Objects.equals(t.getOrgId(), p.getTargetOrgId())).findFirst().get().getOrgName();
                String priceName = agentPriceList.stream().filter(t -> Objects.equals(t.getAgentPriceId(), p.getAgentPriceId())).findFirst().get().getPriceName();
                message.append(String.format(" %s已经存在于[%s]价格序列中; ", orgName, priceName));
            });
            message.append("请勿重复设置!");
            throw new BusinessException(message.toString());
        }
        List<TAgentPriceOrg> agentPriceOrgs = agentPriceOrgDAO.selectByAgentPriceId(agentPriceId);
        List<Integer> deleteId = Lists.newArrayList();
        List<TAgentPriceOrg> addList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(agentPriceOrgs)) {
            deleteId = agentPriceOrgs.stream()
                    .filter(p -> saveParams.stream().noneMatch(t -> Objects.equals(t.getTargetOrgId(), p.getTargetOrgId())))
                    .map(TAgentPriceOrg::getAgentPriceOrgId)
                    .collect(Collectors.toList());
            saveParams.forEach(p -> {
                if (agentPriceOrgs.stream().noneMatch(t -> Objects.equals(t.getTargetOrgId(), p.getTargetOrgId()))) {
                    TAgentPriceOrg agentPriceOrg = new TAgentPriceOrg();
                    agentPriceOrg.setAgentPriceId(agentPriceId);
                    agentPriceOrg.setTargetOrgId(p.getTargetOrgId());
                    addList.add(agentPriceOrg);
                }
            });
        }
        else {
            saveParams.forEach(p -> {
                TAgentPriceOrg agentPriceOrg = new TAgentPriceOrg();
                agentPriceOrg.setAgentPriceId(agentPriceId);
                agentPriceOrg.setTargetOrgId(p.getTargetOrgId());
                addList.add(agentPriceOrg);
            });
        }
        if (CollectionUtils.isNotEmpty(deleteId)) {
            agentPriceOrgDAO.delete(deleteId);
        }
        if (CollectionUtils.isNotEmpty(addList)) {
            agentPriceOrgDAO.insertBatch(addList);
        }
        Date now = new Date();
        TAgentPrice agentPrice = new TAgentPrice();
        agentPrice.setAgentPriceId(agentPriceId);
        agentPrice.setModifiedBy(userInfoVO.getAppUserInfoId());
        agentPrice.setModifiedTime(now);
        agentPriceDAO.updateByPrimaryKeySelective(agentPrice);
    }

    /**
     * 根据价格序列ID得到价格序列的组织成员
     *
     * @param agentPriceId 价格序列ID
     * @return AgentPriceOrgResp
     */
    public AgentPriceOrgResp selectByAgentPriceId(Integer agentPriceId,UserInfoVO userInfoVO) {
        AgentPriceOrgResp resp = new AgentPriceOrgResp();
        resp.setAgentPriceId(agentPriceId);
        TAgentPrice old = agentPriceDAO.selectByPrimaryKey(agentPriceId);
        if (!Objects.equals(old.getOrgId(), userInfoVO.getOrgId())) {
            throw new BusinessException("您无权查看该价格序列!");
        }
        Predicate<OrganizationalDetailVO> predicate = t -> Objects.equals(OrgTypeEnum.AGENT_COMPANY.getCode(), t.getOrgType());
        if (Objects.equals(AgentPriceType.CUSTOMER_PRICE.getCode(), old.getPriceType())) {
            predicate = t -> Objects.equals(OrgTypeEnum.CUSTOMER_COMPANY.getCode(), t.getOrgType());
        }
        List<TAgentPriceOrg> currentOrgTargetOrgIds = agentPriceOrgDAO.selectByOrgIdAndPriceType(old.getOrgId(), old.getPriceType());
        List<TAgentPriceOrg> currentPriceTargetOrgIds = agentPriceOrgDAO.selectByAgentPriceId(agentPriceId);
        Result<List<OrganizationalDetailVO>, String> authResult = authorizationRepository.queryChildOrgs(userInfoVO.getUserId(), old.getOrgId());
        if (Result.OK.equalsIgnoreCase(authResult.getCode())) {
            List<OrganizationalDetailVO> organizationalDetailVOS = authResult.getSobj();
            if (CollectionUtils.isNotEmpty(organizationalDetailVOS)) {
                if (CollectionUtils.isEmpty(currentOrgTargetOrgIds)) {
                    resp.setUnSelectList(organizationalDetailVOS.stream()
                            .filter(predicate)
                            .collect(Collectors.toList()));
                } else {
                    if (CollectionUtils.isNotEmpty(currentPriceTargetOrgIds)) {
                        resp.setSelectedList(
                                organizationalDetailVOS.stream()
                                        .filter(predicate)
                                        .filter(t -> currentPriceTargetOrgIds.stream().anyMatch(p -> Objects.equals(p.getTargetOrgId(), t.getOrgId())))
                                        .collect(Collectors.toList()));
                    }
                    resp.setUnSelectList(organizationalDetailVOS.stream()
                            .filter(predicate)
                            .filter(t -> currentOrgTargetOrgIds.stream().noneMatch(p -> Objects.equals(p.getTargetOrgId(), t.getOrgId())))
                            .collect(Collectors.toList()));
                }
            }
        }
        return resp;
    }

    /**
     * 代理商新增默认价格序列（客户公司价格）
     * @param StartCreateTime
     * @param endCreateTime
     * @return
     */
    public int agentCompanyInsertDefaultPrice(Date StartCreateTime, Date endCreateTime) {
        return agentPriceDAO.agentCompanyInsertDefaultPrice(StartCreateTime, endCreateTime);
    }

}
