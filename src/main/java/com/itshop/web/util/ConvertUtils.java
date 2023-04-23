package com.itshop.web.util;

import com.itshop.web.bo.UserInfoVO;
import com.itshop.web.dto.auth.LoginUserAuthVO;
import com.itshop.web.dto.auth.PermissionTargetVO;
import com.itshop.web.dto.auth.RoleVO;
import com.itshop.web.dto.auth.UserGroupVO;
import com.itshop.web.enums.OrgTypeEnum;
import com.itshop.web.manager.MenuManager;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Auther LiuFL
 * @Date 2023/3/5
 * @Description
 */
public class ConvertUtils {

    public static UserInfoVO convert2UserInfoVO(LoginUserAuthVO loginUserAuthVO) {
        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtils.copyProperties(loginUserAuthVO.getAppUserVO(), userInfoVO);
        BeanUtils.copyProperties(loginUserAuthVO.getOrganizationalVO(), userInfoVO);
        if (loginUserAuthVO.getUserVO() != null) {
            if (CollectionUtils.isNotEmpty(loginUserAuthVO.getUserVO().getUserGroups())) {
                userInfoVO.setUserGroups(loginUserAuthVO.getUserVO().getUserGroups().stream().map(UserGroupVO::getName).collect(Collectors.toList()));
            }
            if (CollectionUtils.isNotEmpty(loginUserAuthVO.getUserVO().getRoles())) {
                userInfoVO.setRoles(loginUserAuthVO.getUserVO().getRoles().stream().map(RoleVO::getName).collect(Collectors.toList()));
            }
            if (CollectionUtils.isNotEmpty(loginUserAuthVO.getUserVO().getPermissionTargets())) {
                userInfoVO.setUrls(loginUserAuthVO.getUserVO().getPermissionTargets().stream()
                        .filter(p -> MenuManager.isDelete.equals(p.getIsDelete()) && MenuManager.showConfig == p.getInAppUi())
                        .filter(p -> StringUtils.isNotBlank(p.getUrl()))
                        .map(PermissionTargetVO::getUrl).collect(Collectors.toList()));
            }
        }
        //
        userInfoVO.setOrgTier(new Integer[]{});
        if (StringUtils.isNotEmpty(userInfoVO.getOrgFullPath())) {
            String[] arr = StringUtils.split(userInfoVO.getOrgFullPath(), '/');
            if (ArrayUtils.isNotEmpty(arr)) {
                Integer[] arr1 = new Integer[arr.length];
                for (int i = 0; i < arr.length; i++) {
                    arr1[i] = Integer.parseInt(arr[i]);
                }
                userInfoVO.setOrgTier(arr1);
            }
        }
        userInfoVO.setCanCreateAgentPrice(false);
        userInfoVO.setServiceProviderOrgId(-1);
        userInfoVO.setAgentLevel1OrgId(-1);
        userInfoVO.setAgentLevel2OrgId(-1);
        userInfoVO.setAgentLevel3OrgId(-1);
        userInfoVO.setCustomerCompanyOrgId(-1);
        Integer[] orgTier = userInfoVO.getOrgTier();
        if (ArrayUtils.isNotEmpty(orgTier) && orgTier.length > 0) {
            userInfoVO.setServiceProviderOrgId(orgTier[0]);
        }
        if (Objects.equals(OrgTypeEnum.SERVICE_PROVIDER.getCode(), userInfoVO.getOrgType())) {
            userInfoVO.setCanCreateAgentPrice(true);
        }
        if (Objects.equals(OrgTypeEnum.CUSTOMER_COMPANY.getCode(), userInfoVO.getOrgType())) {
            userInfoVO.setCustomerCompanyOrgId(userInfoVO.getOrgId());
            if (ArrayUtils.isNotEmpty(orgTier)) {
                if (orgTier.length > 2) { //服务提供商/1级代理/客户公司
                    userInfoVO.setAgentLevel1OrgId(orgTier[1]);
                }
                if (orgTier.length > 3) {//服务提供商/1级代理/2级代理/客户公司
                    userInfoVO.setAgentLevel2OrgId(orgTier[2]);
                }
                if (orgTier.length > 4) {//服务提供商/1级代理/2级代理/三级代理/客户公司
                    userInfoVO.setAgentLevel3OrgId(orgTier[3]);
                }
            }
        }
        if (Objects.equals(OrgTypeEnum.AGENT_COMPANY.getCode(), userInfoVO.getOrgType())) {
            if (ArrayUtils.isNotEmpty(orgTier)) {
                if (orgTier.length > 1) {//服务提供商/1级代理
                    userInfoVO.setAgentLevel1OrgId(orgTier[1]);
                    userInfoVO.setCanCreateAgentPrice(true);
                }
                if (orgTier.length > 2) {//服务提供商/1级代理/2级代理
                    userInfoVO.setAgentLevel2OrgId(orgTier[2]);
                    userInfoVO.setCanCreateAgentPrice(true);
                }
                if (orgTier.length > 3) {//服务提供商/1级代理/2级代理/三级代理
                    userInfoVO.setAgentLevel3OrgId(orgTier[3]);
                }
            }
        }
        return userInfoVO;
    }
}
