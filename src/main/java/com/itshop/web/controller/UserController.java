package com.itshop.web.controller;

import com.github.pagehelper.PageInfo;
import com.itshop.web.annotation.PrintReqResp;
import com.itshop.web.bo.UserInfoVO;
import com.itshop.web.constants.SnowConstants;
import com.itshop.web.dao.auth.AuthorizationRepository;
import com.itshop.web.dto.RetResult;
import com.itshop.web.dto.RetWrapper;
import com.itshop.web.dto.auth.*;
import com.itshop.web.dto.request.UserUpdatePasswordParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户控制器
 *
 * @author liufenglong
 * @date 2022/8/3
 */
@Slf4j
@RestController
@RequestMapping("/user")
@Api(value = "UserController", description = "用户-控制器")
public class UserController  extends BaseController {
    @Autowired
    AuthorizationRepository authorizationRepository;

    /**
     * 修改密码
     *
     * @param passwordParam 用户更新密码
     * @return
     */
    @PrintReqResp
    @PostMapping("/changePassword")
    @ApiOperation(value = "修改密码")
    public RetResult<String> ChangePassword(@RequestBody UserUpdatePasswordParam passwordParam) {
        if (StringUtils.isNotBlank(passwordParam.getNewPassword())) {
            if (!passwordParam.getNewPassword().equalsIgnoreCase(passwordParam.getConfirmPassword())) {
                return RetWrapper.failure("新密码跟确认密码不一致!");
            }
        }
        UserInfoVO userInfoVO = getUserInfoVO();
        UserUpdatePwdResource updatePwdResource = new UserUpdatePwdResource();
        updatePwdResource.setUserId(userInfoVO.getUserId());
        updatePwdResource.setAppUserInfoId(userInfoVO.getAppUserInfoId());
        updatePwdResource.setOldPassword(passwordParam.getOldPassword());
        updatePwdResource.setNewPassword(passwordParam.getNewPassword());
        Result<Boolean, String> authResult = authorizationRepository.updateUserPassword(updatePwdResource);
        if (Result.PASS_WORD_ERROR.equalsIgnoreCase(authResult.getCode())) {
            return RetWrapper.userPassWordError();
        } else if (Result.USER_DOES_NOT_EXIST.equalsIgnoreCase(authResult.getCode())) {
            return RetWrapper.userDoesNotExist();
        } else {
            return RetWrapper.success();
        }
    }

    /**
     * 用户列表
     * @param queryUserListResource
     * @return
     */
    @PostMapping("/listUsers")
    @ApiOperation(value = "用户列表")
    public RetResult<PageInfo<ListUserInfoAndDomainVO>> listUsers(@RequestBody QueryUserListResource queryUserListResource) {
        Result<PageInfo<ListUserInfoAndDomainVO>, String> authResult = authorizationRepository.listUsers(queryUserListResource);
        if (Result.OK.equalsIgnoreCase(authResult.getCode())) {
            return RetWrapper.success(authResult.getSobj());
        } else {
            return RetWrapper.failure("获取用户列表失败!");
        }
    }

    /**
     * 用户详情
     * @param queryUserInfoResource
     * @return
     */
    @PostMapping("/listUserInfo")
    @ApiOperation(value = "用户详情")
    public RetResult<ListUserInfoDetailVO> listUserInfo(@RequestBody QueryUserInfoResource queryUserInfoResource) {
        UserInfoVO userInfoVO = getUserInfoVO();
        queryUserInfoResource.setUserId(userInfoVO.getUserId());
        queryUserInfoResource.setUserDomain(SnowConstants.USER_DOMAIN);

        Result<ListUserInfoDetailVO, String> authResult = authorizationRepository.listUserInfo(queryUserInfoResource);
        if (Result.OK.equalsIgnoreCase(authResult.getCode())) {
            return RetWrapper.success(authResult.getSobj());
        } else {
            return RetWrapper.failure("获取用户详情失败!");
        }
    }

    /**
     * 添加用户
     * @param userAddResource
     * @return
     */
    @PrintReqResp
    @PostMapping("/addUser")
    @ApiOperation(value = "添加用户")
    public RetResult<ListUserVO> addUser(@RequestBody UserAddResource userAddResource) {
        UserInfoVO userInfoVO = getUserInfoVO();
        userAddResource.setAppId(userInfoVO.getAppId());
        userAddResource.setLoginUserId(userInfoVO.getUserId());

        Result<ListUserVO, String> authResult = authorizationRepository.addUser(userAddResource);
        if (Result.OK.equalsIgnoreCase(authResult.getCode())) {
            return RetWrapper.success(authResult.getSobj());
        } else {
            return RetWrapper.failure("添加用户失败!" + authResult.getFobj());
        }
    }

    /**
     * 更新用户
     * @param updateUserResource
     * @return
     */
    @PrintReqResp
    @PostMapping("/updateUser")
    @ApiOperation(value = "更新用户")
    public RetResult<ListUserVO> updateUser(@RequestBody UserUpdateResource updateUserResource) {
        UserInfoVO userInfoVO = getUserInfoVO();
        updateUserResource.setAppId(userInfoVO.getAppId());
        updateUserResource.setLoginUserId(userInfoVO.getUserId());

        Result<ListUserVO, String> authResult = authorizationRepository.updateUser(updateUserResource);
        if (Result.OK.equalsIgnoreCase(authResult.getCode())) {
            return RetWrapper.success(authResult.getSobj());
        } else {
            return RetWrapper.failure("更新用户失败!" + authResult.getFobj());
        }
    }

    /**
     * 启用(禁用)用户
     * @param userEnableResource
     * @return
     */
    @PrintReqResp
    @PostMapping("/stopAndUsingUsers")
    @ApiOperation(value = "启用(禁用)用户")
    public RetResult<String> stopAndUsingUsers(@RequestBody UserEnableResource userEnableResource) {
        UserInfoVO userInfoVO = getUserInfoVO();
        userEnableResource.setUserId(userInfoVO.getUserId());
        userEnableResource.setUserDomain(SnowConstants.USER_DOMAIN);

        Result<String, String> authResult = authorizationRepository.stopAndUsingUsers(userEnableResource);
        if (Result.OK.equalsIgnoreCase(authResult.getCode())) {
            return RetWrapper.success(authResult.getSobj());
        } else {
            return RetWrapper.failure("启用(禁用)用户失败!");
        }
    }

    /**
     * 查询用户资源权限
     * @param baseUserAuth
     * @return
     */
    @PostMapping("/userPermissions")
    @ApiOperation(value = "查询用户资源权限")
    public RetResult<List<UserPermissionVO>> userPermissions(@RequestBody BaseUserAuth baseUserAuth) {
        UserInfoVO userInfoVO = getUserInfoVO();
        baseUserAuth.setUserId(userInfoVO.getUserId());
        baseUserAuth.setUserDomain(SnowConstants.USER_DOMAIN);

        Result<List<UserPermissionVO>, String> authResult = authorizationRepository.userPermissions(baseUserAuth);
        if (Result.OK.equalsIgnoreCase(authResult.getCode())) {
            return RetWrapper.success(authResult.getSobj());
        } else {
            return RetWrapper.failure("查询用户资源权限失败!");
        }
    }

    /**
     * 查询用户资源权限
     * @param resource
     * @return
     */
    @PostMapping("/userPermissionActions")
    @ApiOperation(value = "查询用户资源权限行为")
   public RetResult<List<UserPermissionActionVO>> userPermissionActions(@RequestBody QueryUserPermissionActionsResource resource) {
        UserInfoVO userInfoVO = getUserInfoVO();
        resource.setUserId(userInfoVO.getUserId());
        resource.setUserDomain(SnowConstants.USER_DOMAIN);

        Result<List<UserPermissionActionVO>,String> authResult = authorizationRepository.userPermissionActions(resource);
        if (Result.OK.equalsIgnoreCase(authResult.getCode())) {
            return RetWrapper.success(authResult.getSobj());
        } else {
            return RetWrapper.failure("查询用户资源权限行为失败!");
        }
    }

    /**
     * 查询用户角色
     * @param baseUserAuth
     * @return
     */
    @PostMapping("/listUserRoles")
    @ApiOperation(value = "查询用户角色")
    public RetResult<ListRoleVO[]> listUserRoles(@RequestBody BaseUserAuth baseUserAuth) {
        UserInfoVO userInfoVO = getUserInfoVO();
        baseUserAuth.setUserId(userInfoVO.getUserId());
        baseUserAuth.setUserDomain(SnowConstants.USER_DOMAIN);

        Result<ListRoleVO[],String> authResult = authorizationRepository.listUserRoles(baseUserAuth);
        if (Result.OK.equalsIgnoreCase(authResult.getCode())) {
            return RetWrapper.success(authResult.getSobj());
        } else {
            return RetWrapper.failure("查询用户角色失败!");
        }
    }
}
