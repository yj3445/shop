package com.itshop.web.manager;

import com.itshop.web.dao.mysql.TOrganizationalDAO;
import com.itshop.web.dao.po.TOrganizational;
import com.itshop.web.enums.OrgTypeEnum;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 权限系统
 *
 * @author liufenglong
 * @date 2022/7/5
 */
@Service
public class AuthorizationManager {

    @Autowired
    TOrganizationalDAO tOrganizationalDAO;

    public TOrganizational selectByOrgId(Integer orgId) {
        TOrganizational organizational = tOrganizationalDAO.selectByPrimaryKey(orgId);
        if (organizational != null) {
            settingLevelOrgId(organizational);
        }
        return organizational;
    }
    public List<TOrganizational> selectAllOrganizational() {
        List<TOrganizational> organizationals = tOrganizationalDAO.selectAllOrganizational();
        if (CollectionUtils.isNotEmpty(organizationals)) {
            organizationals.forEach(this::settingLevelOrgId);
        }
        return organizationals;
    }

    /**
     * 设置
     *
     * @param tOrganizational
     */
    private void settingLevelOrgId(TOrganizational tOrganizational) {
        tOrganizational.setServiceProviderOrgId(-1);
        tOrganizational.setAgentLevel1OrgId(-1);
        tOrganizational.setAgentLevel2OrgId(-1);
        tOrganizational.setAgentLevel3OrgId(-1);
        tOrganizational.setCustomerCompanyOrgId(-1);

        if (StringUtils.isNotEmpty(tOrganizational.getOrgFullPath())) {
            String[] arr = StringUtils.split(tOrganizational.getOrgFullPath(), '/');
            if (ArrayUtils.isNotEmpty(arr)) {
                Integer[] arr1 = new Integer[arr.length];
                for (int i = 0; i < arr.length; i++) {
                    arr1[i] = Integer.parseInt(arr[i]);
                }
                tOrganizational.setOrgTier(arr1);
            }
        }
        if (ArrayUtils.isNotEmpty(tOrganizational.getOrgTier()) && tOrganizational.getOrgTier().length > 0) {
            tOrganizational.setServiceProviderOrgId(tOrganizational.getOrgTier()[0]);
        }
        if (Objects.equals(OrgTypeEnum.CUSTOMER_COMPANY.getCode(), tOrganizational.getOrgType()) && ArrayUtils.isNotEmpty(tOrganizational.getOrgTier())) {
            if (tOrganizational.getOrgTier().length > 1) {
                tOrganizational.setCustomerCompanyOrgId(tOrganizational.getOrgTier()[tOrganizational.getOrgTier().length - 1]);
            }
            if (tOrganizational.getOrgTier().length > 2) {
                tOrganizational.setAgentLevel1OrgId(tOrganizational.getOrgTier()[1]);
            }
            if (tOrganizational.getOrgTier().length > 3) {
                tOrganizational.setAgentLevel2OrgId(tOrganizational.getOrgTier()[2]);
            }
            if (tOrganizational.getOrgTier().length > 4) {
                tOrganizational.setAgentLevel3OrgId(tOrganizational.getOrgTier()[3]);
            }
        }
        if (Objects.equals(OrgTypeEnum.AGENT_COMPANY.getCode(), tOrganizational.getOrgType()) && ArrayUtils.isNotEmpty(tOrganizational.getOrgTier())) {
            if (tOrganizational.getOrgTier().length > 1) {
                tOrganizational.setAgentLevel1OrgId(tOrganizational.getOrgTier()[1]);
            }
            if (tOrganizational.getOrgTier().length > 2) {
                tOrganizational.setAgentLevel2OrgId(tOrganizational.getOrgTier()[2]);
            }
            if (tOrganizational.getOrgTier().length > 3) {
                tOrganizational.setAgentLevel3OrgId(tOrganizational.getOrgTier()[3]);
            }
        }
    }
}
