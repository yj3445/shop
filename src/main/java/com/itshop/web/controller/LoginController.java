package com.itshop.web.controller;

import com.itshop.web.annotation.AuthenticationIgnore;
import com.itshop.web.bo.UserInfoVO;
import com.itshop.web.constants.SessionConstants;
import com.itshop.web.dao.auth.AuthorizationRepository;
import com.itshop.web.dto.RetResult;
import com.itshop.web.dto.RetWrapper;
import com.itshop.web.dto.auth.*;
import com.itshop.web.dto.request.UserLoginParam;
import com.itshop.web.enums.OrgTypeEnum;
import com.itshop.web.enums.RetCode;
import com.itshop.web.manager.MenuManager;
import com.itshop.web.util.ConvertUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 登录/退出-控制器
 */
@Slf4j
@AuthenticationIgnore
@RestController
@RequestMapping("/login")
@Api(value = "LoginController", description = "登录/退出-控制器")
public class LoginController {

    @Autowired
    AuthorizationRepository authorizationRepository;

    @Autowired
    MenuManager menuManager;


    @Value("${spring.profiles.active}")
    private String active;

    /**
     * 登录接口
     * @param loginParam 登录参数
     * @return
     */
    @PostMapping("/in")
    @ApiOperation(value = "登录接口")
    public RetResult<String> in(@RequestBody UserLoginParam loginParam, HttpServletRequest request) {
        Result<LoginUserAuthVO, String> authResult = authorizationRepository.login(loginParam.getUserId(), loginParam.getPassWord());
        if (Result.USER_NAME_OR_PASS_WORD_ERROR.equalsIgnoreCase(authResult.getCode())) {
            return RetWrapper.userNameOrPassWordError();
        } else if (Result.OK.equalsIgnoreCase(authResult.getCode())) {
            if (authResult.getSobj() != null) {
                request.getSession().setAttribute(SessionConstants.USER_ID_SESSION, loginParam.getUserId());
                request.getSession().setAttribute(SessionConstants.USER_MENU_SESSION, menuManager.getLoginUserMenuList(authResult.getSobj().getUserVO()));
                request.getSession().setAttribute(SessionConstants.USER_INFO_SESSION, ConvertUtils.convert2UserInfoVO(authResult.getSobj()));
            }
            return RetWrapper.success();
        } else {
            return RetWrapper.internalServerError("系统内部异常");
        }
    }


    /**
     * 退出接口
     *
     * @return
     */
    @GetMapping("/out")
    @ApiOperation(value = "退出接口")
    public RetResult<String> out(HttpServletRequest request,HttpServletResponse response) {
        request.getSession().removeAttribute(SessionConstants.USER_ID_SESSION);
        request.getSession().removeAttribute(SessionConstants.USER_MENU_SESSION);
        request.getSession().removeAttribute(SessionConstants.USER_INFO_SESSION);
        request.getSession().invalidate();

        Cookie cookie = new Cookie("SESSION", null);
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);

//        cookie = new Cookie("authelia_session", null);
//        cookie.setMaxAge(0);
//        cookie.setDomain(".cbdtelecom.cn");
//        cookie.setHttpOnly(true);
//        cookie.setSecure(true);
//        cookie.setPath("/");
//        response.addCookie(cookie);

        return RetWrapper.success();
    }

    @GetMapping("/debug")
    @ApiOperation(value = "本地调试")
    public void debug(HttpServletRequest request,HttpServletResponse response) throws IOException {
        if (("dev".equalsIgnoreCase(active) || "uat".equalsIgnoreCase(active))) {
            UserLoginParam loginParam = new UserLoginParam();
            //服务提供商
            loginParam.setUserId("yang.jun@cbdtelecom.cn");
            //代理商
            loginParam.setUserId("xinzhiqiang@myit.com.cn");
            //客户公司
            loginParam.setUserId("zhang.haoran@cbdtelecom.cn");
            loginParam.setPassWord("123.cbd");
            RetResult<String> result= in(loginParam,request);
            if(RetCode.SUCCESS.getCode() == result.getCode()){
                if ("dev".equalsIgnoreCase(active)) {
                    response.sendRedirect("http://localhost:8008/swagger-ui.html");
                }
                if ("uat".equalsIgnoreCase(active)) {
                    response.sendRedirect("http://150.242.93.34/swagger-ui.html");
                }
            }
        }
    }
}
