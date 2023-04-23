package com.itshop.web.support;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author liufenglong
 * @date 2022/7/18
 */
@Data
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {
    /**
     * 连接地址
     */
    private String endpoint;
    /**
     * 用户名
     */
    private String accessKey;
    /**
     * 密码
     */
    private String secretKey;
    /**
     * 默认桶
      */
    private String defaultBucket;
    /**
     * 域名
     */
    private String nginxHost;
}
