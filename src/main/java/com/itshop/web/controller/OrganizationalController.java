package com.itshop.web.controller;

import com.itshop.web.annotation.PrintReqResp;
import com.itshop.web.bo.UserInfoVO;
import com.itshop.web.constants.SnowConstants;
import com.itshop.web.dao.auth.AuthorizationRepository;
import com.itshop.web.dto.RetResult;
import com.itshop.web.dto.RetWrapper;
import com.itshop.web.dto.auth.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/organizational")
@Api(value = "OrganizationalController", description = "组织-控制器")
public class OrganizationalController   extends BaseController {

    @Autowired
    AuthorizationRepository authorizationRepository;

    /**
     * 组织列表
     *
     * @param queryOffspringOrgesResource
     * @return
     */
    @PostMapping("/list")
    @ApiOperation(value = "组织列表")
    public RetResult<PageSerializable<ListOrganizationalVO>> list(@RequestBody QueryOffspringOrgesResource queryOffspringOrgesResource) {
        UserInfoVO userInfoVO = getUserInfoVO();
        queryOffspringOrgesResource.setOrgId(userInfoVO.getOrgId());
        queryOffspringOrgesResource.setOrgFullPath(userInfoVO.getOrgFullPath());
        queryOffspringOrgesResource.setReturnAllOffspring(true);
        queryOffspringOrgesResource.setUserId(userInfoVO.getUserId());
        queryOffspringOrgesResource.setUserDomain(SnowConstants.USER_DOMAIN);

        Result<PageSerializable<ListOrganizationalVO>, String> authResult = authorizationRepository.queryOffspringOrges(queryOffspringOrgesResource);
        if (Result.OK.equalsIgnoreCase(authResult.getCode())) {
            return RetWrapper.success(authResult.getSobj());
        } else {
            return RetWrapper.failure("获取组织列表失败!");
        }
    }

    /**
     * 组织详情
     *
     * @param queryOrgDetailResource
     * @return
     */
    @PostMapping("/get")
    @ApiOperation(value = "组织详情")
    public RetResult<OrganizationalDetailVO> getDetail(@RequestBody QueryOrgDetailResource queryOrgDetailResource) {
        UserInfoVO userInfoVO = getUserInfoVO();
        queryOrgDetailResource.setUserId(userInfoVO.getUserId());
        queryOrgDetailResource.setUserDomain(SnowConstants.USER_DOMAIN);
        queryOrgDetailResource.setAppId(userInfoVO.getAppId());

        Result<OrganizationalDetailVO, String> authResult = authorizationRepository.queryOrgDetail(queryOrgDetailResource);
        if (Result.OK.equalsIgnoreCase(authResult.getCode())) {
            return RetWrapper.success(authResult.getSobj());
        } else {
            return RetWrapper.failure("获取组织详情失败!");
        }
    }

    /**
     * 新增组织
     *
     * @param organizationalAddResource
     * @return
     */
    @PrintReqResp
    @PostMapping("/add")
    @ApiOperation(value = "新增组织")
    public RetResult<String> addOrganizational(@RequestBody OrganizationalAddResource organizationalAddResource) {
        UserInfoVO userInfoVO = getUserInfoVO();
        organizationalAddResource.setUserId(userInfoVO.getUserId());
        organizationalAddResource.setUserDomain(SnowConstants.USER_DOMAIN);
        organizationalAddResource.setAppId(userInfoVO.getAppId());
        organizationalAddResource.setParentOrgId(userInfoVO.getOrgId());

        Result<String, String> authResult = authorizationRepository.addOrganizational(organizationalAddResource);
        if (Result.OK.equalsIgnoreCase(authResult.getCode())) {
            return RetWrapper.success(authResult.getSobj());
        } else {
            return RetWrapper.failure("新增组织失败!" + authResult.getFobj());
        }
    }

    /**
     * 更新组织
     *
     * @param updateResourceResource
     * @return
     */
    @PrintReqResp
    @PostMapping("/update")
    @ApiOperation(value = "更新组织")
    public RetResult<String> updateOrganizational(@RequestBody OrganizationalUpdateResource updateResourceResource) {
        UserInfoVO userInfoVO = getUserInfoVO();
        updateResourceResource.setUserId(userInfoVO.getUserId());
        updateResourceResource.setUserDomain(SnowConstants.USER_DOMAIN);

        Result<String, String> authResult = authorizationRepository.updateOrganizational(updateResourceResource);
        if (Result.OK.equalsIgnoreCase(authResult.getCode())) {
            return RetWrapper.success(authResult.getSobj());
        } else {
            return RetWrapper.failure("更新组织失败!"+authResult.getFobj());
        }
    }

    /**
     * 启用(禁用)组织
     *
     * @param organizationalEnableResource
     * @return
     */
    @PrintReqResp
    @PostMapping("/enableOrDisable")
    @ApiOperation(value = "启用(禁用)组织")
    public RetResult<String> enableOrDisableOrg(@RequestBody OrganizationalEnableResource organizationalEnableResource) {
        UserInfoVO userInfoVO = getUserInfoVO();
        organizationalEnableResource.setUserId(userInfoVO.getUserId());
        organizationalEnableResource.setUserDomain(SnowConstants.USER_DOMAIN);
        Result<String, String> authResult = authorizationRepository.enableOrDisableOrg(organizationalEnableResource);
        if (Result.OK.equalsIgnoreCase(authResult.getCode())) {
            return RetWrapper.success(authResult.getSobj());
        } else {
            return RetWrapper.failure("启用(禁用)组织失败!");
        }
    }
}
