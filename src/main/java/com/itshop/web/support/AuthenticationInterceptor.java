package com.itshop.web.support;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.itshop.web.annotation.AuthenticationIgnore;
import com.itshop.web.constants.SessionConstants;
import com.itshop.web.dao.auth.AuthorizationRepository;
import com.itshop.web.dto.RetResult;
import com.itshop.web.dto.RetWrapper;
import com.itshop.web.dto.auth.LoginUserAuthVO;
import com.itshop.web.dto.auth.Result;
import com.itshop.web.manager.MenuManager;
import com.itshop.web.util.ConvertUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        if (handler instanceof HandlerMethod) {
            String referrer = request.getHeader("referer");
            String email = request.getHeader("Remote-Email");
            String groups = request.getHeader("Remote-Groups");
            String user = request.getHeader("Remote-User");
            String name = request.getHeader("Remote-Name");
            log.info("referer:{},email:{},groups:{},user:{},name:{}", referrer, email, groups, user, name);

            Map<String,String> customHeader = ImmutableMap.of("Email",email);
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            //忽略登录情况
            AuthenticationIgnore loginIgnore = handlerMethod.getMethod().getAnnotation(AuthenticationIgnore.class);
            if (loginIgnore == null) {
                loginIgnore = handlerMethod.getMethod().getDeclaringClass().getAnnotation(AuthenticationIgnore.class);
            }
            if (loginIgnore != null) {
                addCustomHeader(response,customHeader);
                return true;
            }
            String msg ;
            //验证用户是否登录
            String userId = (String) request.getSession().getAttribute(SessionConstants.USER_ID_SESSION);
            if (StringUtils.isNotBlank(userId) && userId.equalsIgnoreCase(email)) {
                //判断用户缓存跟email是否一致
                addCustomHeader(response,customHeader);
                return true;
            }
            // Environment environment = (Environment) ApplicationContextProvider.getBean(Environment.class);
            // String traefikUrl = environment.getProperty("traefik.url");
            // if (StringUtils.isNotBlank(referrer)
            //         && StringUtils.isNotBlank(traefikUrl)
            //         && referrer.toLowerCase().contains(traefikUrl)) {
                if (StringUtils.isBlank(groups) || StringUtils.isBlank(email)) {
                    msg = "来自Traefik系统的请求,缺少Remote-Groups或Remote-Email!";
                    log.error("[AuthenticationInterceptor] " + msg);
                    responseResult(HttpStatus.BAD_REQUEST, response, RetWrapper.failure(msg));
                    return false;
                }
                if (!groups.toLowerCase().contains("itshop")) {
                    msg = "您没有访问itshop的权限，请联系管理员!";
                    log.error("[AuthenticationInterceptor] group信息中不包含itshop," + msg);
                    responseResult(HttpStatus.UNAUTHORIZED, response, RetWrapper.failure(msg));
                    return false;
                }
                AuthorizationRepository authorizationRepository = (AuthorizationRepository) ApplicationContextProvider.getBean(AuthorizationRepository.class);
                MenuManager menuManager = (MenuManager) ApplicationContextProvider.getBean(MenuManager.class);
                Result<LoginUserAuthVO, String> authResult = authorizationRepository.login(email, "123.cbd");
                if (Result.OK.equalsIgnoreCase(authResult.getCode()) && authResult.getSobj() != null) {
//                    log.info(String.format("[AuthenticationInterceptor],email:%s,获取用户权限成功!", email));
                    request.getSession().setAttribute(SessionConstants.USER_ID_SESSION, email);
                    request.getSession().setAttribute(SessionConstants.USER_MENU_SESSION, menuManager.getLoginUserMenuList(authResult.getSobj().getUserVO()));
                    request.getSession().setAttribute(SessionConstants.USER_INFO_SESSION, ConvertUtils.convert2UserInfoVO(authResult.getSobj()));
                    addCustomHeader(response,customHeader);
                    return true;
                } else {
                    msg = "验证用户权限时,出现错误!";
                    log.error(String.format("[AuthenticationInterceptor],email:%s," + msg, email));
                    responseResult(HttpStatus.INTERNAL_SERVER_ERROR, response, RetWrapper.internalServerError(msg));
                    return false;
                }
            // }
            // responseResult(HttpStatus.UNAUTHORIZED, response, RetWrapper.unauthorized());
            // return false;
        }
        return true;
    }

    /**
     * 添加自定义响应头
     *
     * @param response 响应
     * @param headers  自定义头信息
     */
    private void addCustomHeader(HttpServletResponse response, Map<String,String> headers) {
        headers.forEach(response::addHeader);
    }

    private void responseResult(HttpStatus httpStatus, HttpServletResponse response, RetResult<Object> result) {
        response.setStatus(httpStatus.value());
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        try {
            response.getWriter().write(JSON.toJSONString(result, SerializerFeature.WriteMapNullValue));
        } catch (IOException ex) {
//            log.error(ex.getMessage());
        }
    }
}
