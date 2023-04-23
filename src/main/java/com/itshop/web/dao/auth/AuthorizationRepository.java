package com.itshop.web.dao.auth;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.itshop.web.dto.auth.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.List;

/**
 *
 * 权限系统
 *
 * @author liufenglong
 * @date 2022/7/5
 */
@Repository
public class AuthorizationRepository {
    @Value("${authorization.apiToken}")
    private String apiToken;

    @Value("${authorization.apiUrl}")
    private String apiUrl;

    @Value("${authorization.userDomain}")
    private String userDomain;

    @Autowired
    RestTemplate restTemplate;

    /**
     * 查询用户所有授权(扁平化表示)
     *
     * @param userId     用户id
     * @apiNote 最大重试3次,重试延迟时间为3s,延时倍数为1（第一次：3s,第二次:3s,第三次3s）
     * @return
     */
//    @Retryable(value= {RestClientException.class},maxAttempts = 3,backoff = @Backoff(delay = 3000L,multiplier = 1))
    public Result<UserVO,String> queryUserAuthorizations(String userId) {
        String url = String.format("%s/auth/out/user/auths.do", apiUrl);
        ParameterizedTypeReference<Result<UserVO,String>> responseBodyType = new ParameterizedTypeReference<Result<UserVO,String>>() {
        };
        UserAuthRequestVO requestVO = UserAuthRequestVO.builder().apiToken(apiToken).userDomain(userDomain).userId(userId).build();
        Result<UserVO,String> result = exchange(url, HttpMethod.POST, responseBodyType, JSONObject.toJSONString(requestVO));
        return result;
    }

    /**
     * 查询应用在权限平台所有的资源信息
     *
     * @apiNote 最大重试3次,重试延迟时间为3s,延时倍数为1（第一次：3s,第二次:3s,第三次3s）
     * @return Result<AppPermissionTargetVO>
     */
//    @Retryable(value= {RestClientException.class},maxAttempts = 3,backoff = @Backoff(delay = 3000L,multiplier = 1))
    public Result<List<AppPermissionConfigTargetVO>,String> queryAppResources() {
        String url = String.format("%s/auth/out/user/app/resources.do", apiUrl);
        ParameterizedTypeReference<Result<List<AppPermissionConfigTargetVO>, String>> responseBodyType = new ParameterizedTypeReference<Result<List<AppPermissionConfigTargetVO>, String>>() {
        };
        UserAuthRequestVO requestVO = UserAuthRequestVO.builder().apiToken(apiToken).build();
        Result<List<AppPermissionConfigTargetVO>, String> result = exchange(url, HttpMethod.POST, responseBodyType, JSONObject.toJSONString(requestVO));
        return result;
    }

    /**
     * 查询用户是否存在
     *
     * @param userId     用户id
     * @apiNote 最大重试3次,重试延迟时间为3s,延时倍数为1（第一次：3s,第二次:3s,第三次3s）
     * @return
     */
//    @Retryable(value= {RestClientException.class},maxAttempts = 3,backoff = @Backoff(delay = 3000L,multiplier = 1))
    public Result<Boolean,Object> checkUser(String userId) {
        String url = String.format("%s/auth/out/user/checkUser.do", apiUrl);
        ParameterizedTypeReference<Result<Boolean,Object>> responseBodyType = new ParameterizedTypeReference<Result<Boolean,Object>>() {
        };
        UserAuthRequestVO requestVO = UserAuthRequestVO.builder().apiToken(apiToken).userDomain(userDomain).userId(userId).build();
        Result<Boolean,Object> result = exchange(url, HttpMethod.POST, responseBodyType, JSONObject.toJSONString(requestVO));
        return result;
    }

    /**
     * 登录
     *
     * @param userId     用户id
     * @apiNote 最大重试3次,重试延迟时间为3s,延时倍数为1（第一次：3s,第二次:3s,第三次3s）
     * @return
     */
    public Result<LoginUserAuthVO,String> login(String userId,String passWord) {
        String url = String.format("%s/auth/out/user/login.do", apiUrl);
        ParameterizedTypeReference<Result<LoginUserAuthVO,String>> responseBodyType = new ParameterizedTypeReference<Result<LoginUserAuthVO,String>>() {
        };
        LoginUserAuthResource requestVO = new LoginUserAuthResource();
        requestVO.setApiToken(apiToken);
        requestVO.setUserDomain(userDomain);
        requestVO.setUserId(userId);
        requestVO.setPassWord(passWord);
        Result<LoginUserAuthVO,String> result = exchange(url, HttpMethod.POST, responseBodyType, JSONObject.toJSONString(requestVO));
        return result;
    }

    /**
     * 得到下层组织架构
     *
     * @param userId 用户账户
     * @param orgId 组织ID
     * @return Result<List<OrganizationalDetailVO>,String>
     */
//    @Retryable(value= {RestClientException.class},maxAttempts = 3,backoff = @Backoff(delay = 3000L,multiplier = 1))
    public Result<List<OrganizationalDetailVO>,String> queryChildOrgs(String userId,Integer orgId) {
        String url = String.format("%s/auth/out/user/queryChildOrgs.do", apiUrl);
        ParameterizedTypeReference<Result<List<OrganizationalDetailVO>, String>> responseBodyType = new ParameterizedTypeReference<Result<List<OrganizationalDetailVO>, String>>() {
        };
        QueryChildOrgsResource requestVO = new QueryChildOrgsResource();
        requestVO.setApiToken(apiToken);
        requestVO.setUserDomain(userDomain);
        requestVO.setUserId(userId);
        requestVO.setOrgId(orgId);
        Result<List<OrganizationalDetailVO>, String> result = exchange(url, HttpMethod.POST, responseBodyType, JSONObject.toJSONString(requestVO));
        return result;
    }

    /**
     * 更新用户密码
     *
     * @param userUpdatePwdResource
     * @return
     */
    public Result<Boolean,String> updateUserPassword(UserUpdatePwdResource userUpdatePwdResource) {
        String url = String.format("%s/auth/out/user/updatePassword.do", apiUrl);
        ParameterizedTypeReference<Result<Boolean, String>> responseBodyType = new ParameterizedTypeReference<Result<Boolean, String>>() {
        };
        userUpdatePwdResource.setApiToken(apiToken);
        Result<Boolean, String> result = exchange(url, HttpMethod.POST, responseBodyType, JSONObject.toJSONString(userUpdatePwdResource));
        return result;
    }

    /**-----------------------------------------------------------------[组织接口]--------------------------------------------------------------------------------------**/

    /**
     * 得到下层(后代)组织架构
     * @param queryOffspringOrgesResource
     * @return
     */
//    @Retryable(value= {RestClientException.class},maxAttempts = 3,backoff = @Backoff(delay = 3000L,multiplier = 1))
    public Result<PageSerializable<ListOrganizationalVO>,String> queryOffspringOrges(QueryOffspringOrgesResource queryOffspringOrgesResource) {
        String url = String.format("%s/auth/out/organizational/queryOffspringOrges.do", apiUrl);
        ParameterizedTypeReference<Result<PageSerializable<ListOrganizationalVO>,String>> responseBodyType = new ParameterizedTypeReference<Result<PageSerializable<ListOrganizationalVO>,String>>() {
        };
        queryOffspringOrgesResource.setApiToken(apiToken);
        Result<PageSerializable<ListOrganizationalVO>,String> result = exchange(url, HttpMethod.POST, responseBodyType, JSONObject.toJSONString(queryOffspringOrgesResource));
        return result;
    }

    /**
     * 组织详情
     * @param queryOrgDetailResource
     * @return
     */
//    @Retryable(value= {RestClientException.class},maxAttempts = 3,backoff = @Backoff(delay = 3000L,multiplier = 1))
    public Result<OrganizationalDetailVO,String> queryOrgDetail(QueryOrgDetailResource queryOrgDetailResource) {
        String url = String.format("%s/auth/out/organizational/get.do", apiUrl);
        ParameterizedTypeReference<Result<OrganizationalDetailVO, String>> responseBodyType = new ParameterizedTypeReference<Result<OrganizationalDetailVO, String>>() {
        };
        queryOrgDetailResource.setApiToken(apiToken);
        Result<OrganizationalDetailVO, String> result = exchange(url, HttpMethod.POST, responseBodyType, JSONObject.toJSONString(queryOrgDetailResource));
        return result;
    }

    /**
     * 新增组织
     * @param addResourceResource
     * @return
     */
    public Result<String,String> addOrganizational(OrganizationalAddResource addResourceResource) {
        String url = String.format("%s/auth/out/organizational/add.do", apiUrl);
        ParameterizedTypeReference<Result<String, String>> responseBodyType = new ParameterizedTypeReference<Result<String, String>>() {
        };
        addResourceResource.setApiToken(apiToken);
        Result<String, String> result = exchange(url, HttpMethod.POST, responseBodyType, JSONObject.toJSONString(addResourceResource));
        return result;
    }

    /**
     * 更新组织
     * @param updateResourceResource
     * @return
     */
    public Result<String,String> updateOrganizational(OrganizationalUpdateResource updateResourceResource){
        String url = String.format("%s/auth/out/organizational/update.do", apiUrl);
        ParameterizedTypeReference<Result<String, String>> responseBodyType = new ParameterizedTypeReference<Result<String, String>>() {
        };
        updateResourceResource.setApiToken(apiToken);
        Result<String, String> result = exchange(url, HttpMethod.POST, responseBodyType, JSONObject.toJSONString(updateResourceResource));
        return result;
    }

    /**
     * 启用或禁用 组织
     *
     * @param organizationalStatusResource
     * @return
     */
    public Result<String,String> enableOrDisableOrg(OrganizationalEnableResource organizationalStatusResource) {
        String url = String.format("%s/auth/out/organizational/enableOrDisable.do", apiUrl);
        ParameterizedTypeReference<Result<String, String>> responseBodyType = new ParameterizedTypeReference<Result<String, String>>() {
        };
        organizationalStatusResource.setApiToken(apiToken);
        Result<String, String> result = exchange(url, HttpMethod.POST, responseBodyType, JSONObject.toJSONString(organizationalStatusResource));
        return result;
    }

    /**-----------------------------------------------------------------[用户接口]--------------------------------------------------------------------------------------**/

    /**
     * 用户列表
     * @param queryUserListResource
     * @return
     */
//    @Retryable(value= {RestClientException.class},maxAttempts = 3,backoff = @Backoff(delay = 3000L,multiplier = 1))
    public Result<PageInfo<ListUserInfoAndDomainVO>,String> listUsers(QueryUserListResource queryUserListResource){
        String url = String.format("%s/auth/out/user/listUsers.do", apiUrl);
        ParameterizedTypeReference<Result<PageInfo<ListUserInfoAndDomainVO>,String>> responseBodyType = new ParameterizedTypeReference<Result<PageInfo<ListUserInfoAndDomainVO>,String>>() {
        };
        queryUserListResource.setApiToken(apiToken);
        Result<PageInfo<ListUserInfoAndDomainVO>,String> result = exchange(url, HttpMethod.POST, responseBodyType, JSONObject.toJSONString(queryUserListResource));
        return result;
    }

    /**
     * 用户详情
     * @param queryUserInfoResource
     * @return
     */
//    @Retryable(value= {RestClientException.class},maxAttempts = 3,backoff = @Backoff(delay = 3000L,multiplier = 1))
    public Result<ListUserInfoDetailVO,String> listUserInfo(QueryUserInfoResource queryUserInfoResource) {
        String url = String.format("%s/auth/out/user/listUserInfo.do", apiUrl);
        ParameterizedTypeReference<Result<ListUserInfoDetailVO,String>> responseBodyType = new ParameterizedTypeReference<Result<ListUserInfoDetailVO,String>>() {
        };
        queryUserInfoResource.setApiToken(apiToken);
        Result<ListUserInfoDetailVO,String> result = exchange(url, HttpMethod.POST, responseBodyType, JSONObject.toJSONString(queryUserInfoResource));
        return result;
    }

    /**
     * 新增用户
     *
     * @param userAddResource
     * @return
     */
    public Result<ListUserVO,String> addUser(UserAddResource userAddResource) {
        String url = String.format("%s/auth/out/user/addUser.do", apiUrl);
        ParameterizedTypeReference<Result<ListUserVO,String>> responseBodyType = new ParameterizedTypeReference<Result<ListUserVO,String>>() {
        };
        userAddResource.setApiToken(apiToken);
        Result<ListUserVO,String> result = exchange(url, HttpMethod.POST, responseBodyType, JSONObject.toJSONString(userAddResource));
        return result;
    }

    /**
     * 更新用户信息
     * @param updateUserResource
     * @return
     */
    public Result<ListUserVO,String> updateUser(UserUpdateResource updateUserResource){
        String url = String.format("%s/auth/out/user/updateUser.do", apiUrl);
        ParameterizedTypeReference<Result<ListUserVO,String>> responseBodyType = new ParameterizedTypeReference<Result<ListUserVO,String>>() {
        };
        updateUserResource.setApiToken(apiToken);
        Result<ListUserVO,String> result = exchange(url, HttpMethod.POST, responseBodyType, JSONObject.toJSONString(updateUserResource));
        return result;
    }

    /**
     * 停用或者启用用户
     * @param userEnableResource
     * @return
     */
    public Result<String,String> stopAndUsingUsers(UserEnableResource userEnableResource) {
        String url = String.format("%s/auth/out/user/stopAndUsingUsers.do", apiUrl);
        ParameterizedTypeReference<Result<String,String>> responseBodyType = new ParameterizedTypeReference<Result<String,String>>() {
        };
        userEnableResource.setApiToken(apiToken);
        Result<String,String> result = exchange(url, HttpMethod.POST, responseBodyType, JSONObject.toJSONString(userEnableResource));
        return result;
    }

    /**
     * 查询用户资源权限
     *
     * @param baseUserAuth
     * @return
     */
//    @Retryable(value= {RestClientException.class},maxAttempts = 3,backoff = @Backoff(delay = 3000L,multiplier = 1))
    public Result<List<UserPermissionVO>,String> userPermissions(BaseUserAuth baseUserAuth){
        String url = String.format("%s/auth/out/user/permissions.do", apiUrl);
        ParameterizedTypeReference<Result<List<UserPermissionVO>,String>> responseBodyType = new ParameterizedTypeReference<Result<List<UserPermissionVO>,String>>() {
        };
        baseUserAuth.setApiToken(apiToken);
        Result<List<UserPermissionVO>,String> result = exchange(url, HttpMethod.POST, responseBodyType, JSONObject.toJSONString(baseUserAuth));
        return result;
    }

    /**
     * 查询用户资源权限行为
     * @param resource
     * @return
     */
//    @Retryable(value= {RestClientException.class},maxAttempts = 3,backoff = @Backoff(delay = 3000L,multiplier = 1))
    public Result<List<UserPermissionActionVO>,String> userPermissionActions(QueryUserPermissionActionsResource resource){
        String url = String.format("%s/auth/out/user/permissionActions.do", apiUrl);
        ParameterizedTypeReference<Result<List<UserPermissionActionVO>,String>> responseBodyType = new ParameterizedTypeReference<Result<List<UserPermissionActionVO>,String>>() {
        };
        resource.setApiToken(apiToken);
        Result<List<UserPermissionActionVO>,String> result = exchange(url, HttpMethod.POST, responseBodyType, JSONObject.toJSONString(resource));
        return result;
    }

    /**
     * 用户角色
     * @param baseUserAuth
     */
//    @Retryable(value= {RestClientException.class},maxAttempts = 3,backoff = @Backoff(delay = 3000L,multiplier = 1))
    public Result<ListRoleVO[],String> listUserRoles(BaseUserAuth baseUserAuth) {
        String url = String.format("%s/auth/out/user/listUserRoles.do", apiUrl);
        ParameterizedTypeReference<Result<ListRoleVO[], String>> responseBodyType = new ParameterizedTypeReference<Result<ListRoleVO[], String>>() {
        };
        baseUserAuth.setApiToken(apiToken);
        Result<ListRoleVO[], String> result = exchange(url, HttpMethod.POST, responseBodyType, JSONObject.toJSONString(baseUserAuth));
        return result;
    }

    private <T, A> T exchange(String url, HttpMethod method, ParameterizedTypeReference<T> responseBodyType, A requestBody) throws RestClientException {
        // 请求头
        HttpHeaders headers = new HttpHeaders();
        MimeType mimeType = MimeTypeUtils.parseMimeType("application/json");
        MediaType mediaType = new MediaType(mimeType.getType(), mimeType.getSubtype(), Charset.forName("UTF-8"));
        // 请求体
        headers.setContentType(mediaType);
        //不复用tcp socket
        //https://www.freesion.com/article/1119949556/ 修复[NoHttpResponseException]
//        headers.setConnection("false");
        // 发送请求
        HttpEntity<A> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<T> resultEntity = restTemplate.exchange(url, method, entity, responseBodyType);
        return resultEntity.getBody();
    }
}
