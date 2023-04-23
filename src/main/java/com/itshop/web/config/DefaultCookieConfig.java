package com.itshop.web.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

import java.util.Optional;

@Configuration
public class DefaultCookieConfig {
    @Autowired
    private ServerProperties serverProperties;

    @Bean
    public CookieSerializer cookieSerializer() {
        // 解决cookiePath会多一个"/" 的问题
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        String contextPath = Optional.ofNullable(serverProperties).map(ServerProperties::getServlet)
                .map(ServerProperties.Servlet::getContextPath).orElse(null);
        // 当配置了context path 时设置下cookie path ; 防止cookie path 变成 contextPath + /
        if (!StringUtils.isEmpty(contextPath)) {
            serializer.setCookiePath(contextPath);
        }
        serializer.setUseHttpOnlyCookie(true);
        serializer.setUseSecureCookie(false);
        // 干掉 SameSite=Lax
        serializer.setSameSite(null);
        return serializer;
    }
}
