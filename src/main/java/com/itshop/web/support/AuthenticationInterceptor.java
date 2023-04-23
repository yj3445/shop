package com.itshop.web.support;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.itshop.web.annotation.AuthenticationIgnore;
import com.itshop.web.constants.SessionConstants;
import com.itshop.web.dto.RetResult;
import com.itshop.web.dto.RetWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            //忽略登录情况
            AuthenticationIgnore loginIgnore = handlerMethod.getMethod().getAnnotation(AuthenticationIgnore.class);
            if (loginIgnore == null) {
                loginIgnore = handlerMethod.getMethod().getDeclaringClass().getAnnotation(AuthenticationIgnore.class);
            }
            if (loginIgnore != null) {
                return true;
            }
            //验证用户是否登录
            if (request.getSession().getAttribute(SessionConstants.USER_LOGIN_FLAG_SESSION) != null) {
                return true;
            }
            log.info("--------responseResult 1------");
            responseResult(response, RetWrapper.unauthorized());
            return false;
        }
        log.info("--------preHandle------");
        return true;
    }

    private void responseResult(HttpServletResponse response, RetResult<Object> result) {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        try {
            response.getWriter().write(JSON.toJSONString(result, SerializerFeature.WriteMapNullValue));
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
    }
}
