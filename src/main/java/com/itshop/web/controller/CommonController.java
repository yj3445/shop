package com.itshop.web.controller;


import com.itshop.web.bo.Menu;
import com.itshop.web.bo.UserInfoVO;
import com.itshop.web.dto.RetResult;
import com.itshop.web.dto.RetWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 公共控制器
 *
 * @author liufenglong
 * @date 2022/7/12
 */
@Slf4j
@RestController
@RequestMapping("/common")
@Api(value = "commonController", description = "公共控制器")
public class CommonController extends BaseController {
    /**
     * 菜单信息
     *
     * @param navigationCode 导航编码
     */
    @GetMapping("/getMenuData")
    @ApiOperation(value = "菜单信息")
    public RetResult<List<Menu>> getMenuData(@ApiParam(name = "navigationCode")
                                             @RequestParam(required = false,value = "controlPanal")
                                                     String navigationCode) {
        List<Menu> menuList = getSubMenus(getMenuList(), navigationCode);
        return RetWrapper.success(menuList);
    }

    /**
     * 当前登录用户信息
     */
    @GetMapping("/getUserInfo")
    @ApiOperation(value = "当前登录用户信息")
    public RetResult<UserInfoVO> getUserInfo() {
        return RetWrapper.success(getUserInfoVO());
    }

}
