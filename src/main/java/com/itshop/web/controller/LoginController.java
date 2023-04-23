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
                request.getSession().setAttribute(SessionConstants.USER_LOGIN_FLAG_SESSION, true);
                request.getSession().setAttribute(SessionConstants.USER_MENU_SESSION, menuManager.getLoginUserMenuList(authResult.getSobj().getUserVO()));
                request.getSession().setAttribute(SessionConstants.USER_INFO_SESSION, convert2UserInfoVO(authResult.getSobj()));
            }
            return RetWrapper.success();
        } else {
            return RetWrapper.internalServerError("系统内部异常");
        }
    }

    private UserInfoVO convert2UserInfoVO(LoginUserAuthVO loginUserAuthVO) {
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

    /**
     * 退出接口
     *
     * @return
     */
    @GetMapping("/out")
    @ApiOperation(value = "退出接口")
    public RetResult<String> out(HttpServletRequest request) {
        request.getSession().removeAttribute(SessionConstants.USER_LOGIN_FLAG_SESSION);
        request.getSession().removeAttribute(SessionConstants.USER_MENU_SESSION);
        request.getSession().removeAttribute(SessionConstants.USER_INFO_SESSION);
        request.getSession().invalidate();
        return RetWrapper.unauthorized();
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
