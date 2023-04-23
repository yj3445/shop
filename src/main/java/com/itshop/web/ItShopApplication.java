package com.itshop.web;

import com.github.pagehelper.autoconfigure.PageHelperAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication(exclude = PageHelperAutoConfiguration.class)
@ServletComponentScan(basePackages = {"com.itshop.web"})
@EnableRetry
@EnableRedisHttpSession
public class ItShopApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ItShopApplication.class);
        application.run(args);
    }

}
