package com.itshop.web.controller;

import com.google.common.collect.Lists;
import com.itshop.web.bo.Menu;
import com.itshop.web.bo.UserInfoVO;
import com.itshop.web.constants.SessionConstants;
import com.itshop.web.enums.OrgTypeEnum;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BaseController {
    @Autowired
    HttpServletRequest request;

    /**
     * 当前登录账户ID
     *
     * @return
     */
    protected Integer getCurrentUserId() {
        return getUserInfoVO().getAppUserInfoId();
    }

    /**
     * 得到代理商组织ID
     *
     * @return
     */
    protected Integer getAgentOrgId() {
        UserInfoVO userInfoVO = getUserInfoVO();
        Integer orgId = userInfoVO.getOrgId();
        if (OrgTypeEnum.CUSTOMER_COMPANY.getCode().equals(userInfoVO.getOrgType())) {
            orgId = userInfoVO.getParentOrgId();
        }
        return orgId;
    }

    /**
     * 菜单信息
     *
     * @return
     */
    protected List<Menu> getMenuList() {
        return (List<Menu>) request.getSession().getAttribute(SessionConstants.USER_MENU_SESSION);
    }

    /**
     * 得到当前登录用户信息
     *
     * @return
     */
    protected UserInfoVO getUserInfoVO() {
        return (UserInfoVO) request.getSession().getAttribute(SessionConstants.USER_INFO_SESSION);
    }

    /**
     * 根据编码得到子菜单
     *
     * @param menus
     * @param navigationCode
     * @return
     */
    protected List<Menu> getSubMenus(List<Menu> menus, String navigationCode) {
        if(StringUtils.isBlank(navigationCode)) {
            return menus;
        }
        Queue<Menu> queue = new LinkedList<>();
        if (CollectionUtils.isNotEmpty(menus)) {
            menus.forEach(queue::offer);
        }
        while (!queue.isEmpty()) {
            Menu menu = queue.poll();
            if (navigationCode.equalsIgnoreCase(menu.getCode())) {
                return menu.getChildren();
            }
            if (CollectionUtils.isNotEmpty(menu.getChildren())) {
                menu.getChildren().forEach(queue::offer);
            }
        }
        return Lists.newArrayList();
    }
}
