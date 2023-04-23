package com.itshop.web.manager;

import com.google.common.collect.Lists;
import com.itshop.web.bo.Action;
import com.itshop.web.bo.Menu;
import com.itshop.web.dto.auth.UserVO;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MenuManager {

    //权限平台-资源-显示配置(1代表显示,0代表隐藏)
    public static final byte showConfig = 1;

    public static final Integer isDelete = 0;

    /**
     * 得到用户菜单信息
     *
     * @return List<Menu>
     */
    public List<Menu> getLoginUserMenuList(UserVO loginUserVO) {
        List<Menu> menuList = Lists.newArrayList();
        if (loginUserVO == null || CollectionUtils.isEmpty(loginUserVO.getPermissionTargets())) {
            return menuList;
        }
        loginUserVO.getPermissionTargets().stream()
                .filter(p -> isDelete.equals(p.getIsDelete()) && showConfig == p.getInAppUi()).forEach(c -> {
                    Menu menu = new Menu();
                    menu.setId(c.getPermissionTargetId());
                    menu.setParentId(c.getParentPermissionTargetId());
                    menu.setLevel(c.getLevel());
                    menu.setCode(c.getTargetCode());

                    menu.setText(c.getName());
                    menu.setLabel(c.getName());
                    menu.setUrl(c.getUrl());
                    menu.setOrderNum(c.getListNo());
                    if (!CollectionUtils.isEmpty(c.getPermissions())) {
                        Map<String, Action> actionMap = new HashMap<>(8);
                        c.getPermissions().stream().filter(p -> Integer.valueOf(0).equals(p.getIsDelete())).forEach(vo -> {
                            Action action = new Action();
                            action.setCode(vo.getAction());
                            action.setName(vo.getActionName());
                            actionMap.put(vo.getAction(), action);
                        });
                        menu.setActions(new ArrayList(actionMap.values()));
                    }
                    menuList.add(menu);
                });
        return new Menu.MenuBuilder(menuList).builder();
    }
}
