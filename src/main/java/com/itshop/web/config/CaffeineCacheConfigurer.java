package com.itshop.web.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.itshop.web.constants.SnowConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * CaffeineCache缓存配置
 */
@Slf4j
@Configuration
public class CaffeineCacheConfigurer {
    @Bean("keyGenerator")
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(SnowConstants.COLON);
            sb.append(method.getName());
            sb.append(SnowConstants.COLON);
            StringBuilder paraAndValue = new StringBuilder();
            for (Object obj : params) {
                sb.append(SnowConstants.COLON);
                if (null != obj && StringUtils.isNotBlank(obj.toString())) {
                    paraAndValue.append(obj.toString());
                }
            }
            String sha256Hex = DigestUtils.sha256Hex(sb + paraAndValue.toString());
            log.info("cache key str: {}  cache key sha256Hex: {} ", (sb + paraAndValue.toString()), sha256Hex);
            return sha256Hex;
        };
    }

    /**
     * caffeineCacheManager
     * @return CacheManager
     */
    @Bean("caffeineCacheManager")
    public CacheManager caffeineCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder()
                // 设置最后一次写入或访问后经过固定时间过期
                .expireAfterAccess(600, TimeUnit.SECONDS)
                // 初始的缓存空间大小
                .initialCapacity(100)
                // 缓存的最大条数
                .maximumSize(500));
        return cacheManager;
    }
}
