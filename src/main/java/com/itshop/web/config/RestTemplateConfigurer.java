package com.itshop.web.config;


import lombok.extern.slf4j.Slf4j;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.StopWatch;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * RestTemplate 配置信息
 *
 * @author liufenglong
 * @date 2020/6/15
 */
@Configuration
@Slf4j
public class RestTemplateConfigurer {


    /**
     *
     * 套接字超时：建立连接后等待直到接收到数据的最长时间（毫秒)
     * */
    @Value("${http.socket.timeout:60000}")
    private int socketTimeout;

    /**
     * 连接超时：等待建立连接的最长时间（毫秒)
     * */
    @Value("${http.connect.timeout:3000}")
    private int connectTimeout;

    /**
     * 接请求超时：从连接池获得连接之前等待的最长时间 （毫秒)
     * */
    @Value("${http.connectionRequest.timeout:200}")
    private int connectionRequestTimeout;

    /**
     * 所有HTTP路由的连接总数
     * */
    @Value("${http.maxConnections:100}")
    private int maxConnections;

    /**
     * 池中的每个HTTP路由设置最大连接数
     * */
    @Value("${http.maxConnections:20}")
    private int maxConnectionsPerRoute;

    /**
     * 默认长链接保持时间（毫秒)
     */
    @Value("${http.defaultKeepAliveTimeMillis:10000}")
    private int defaultKeepAliveTimeMillis;


    @Bean
    public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager() {
        SSLConnectionSocketFactory socketFactory = null;
        try {
            //采用绕过验证的方式处理https请求
            SSLContext sc = SSLContext.getInstance("SSLv3");
            // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
            X509TrustManager trustManager = new X509TrustManager() {
                @Override
                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                        String paramString) {
                }

                @Override
                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                        String paramString) {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            sc.init(null, new TrustManager[]{trustManager}, null);
            socketFactory = new SSLConnectionSocketFactory(sc);
        } catch (GeneralSecurityException ex) {
            log.error("自定义SSLConnectionSocketFactory发生异常", ex);
            socketFactory = SSLConnectionSocketFactory.getSocketFactory();
        }
        //设置协议http和https对应的处理socket连接工厂的对象
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", socketFactory)
                .build();
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        // 可用空闲连接过期时间,重用空闲连接时会先检查是否空闲时间超过这个时间，如果超过，释放socket重新建立
        connectionManager.setValidateAfterInactivity(0);//改为0 立即释放socket
        // 最大连接数
        connectionManager.setMaxTotal(maxConnections);
        // 同路由并发数
        connectionManager.setDefaultMaxPerRoute(maxConnectionsPerRoute);
        return connectionManager;
    }

    @Bean
    public CloseableHttpClient closeableHttpClient() {
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(socketTimeout)
                .setConnectTimeout(connectTimeout)
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .build();

        return HttpClientBuilder.create()
                .setConnectionManager(poolingHttpClientConnectionManager())
                .setDefaultRequestConfig(requestConfig)
                //设置长连接保持策略
                .setKeepAliveStrategy(connectionKeepAliveStrategy())
                //设置后台线程剔除失效连接
                .evictExpiredConnections()
                .setRetryHandler((exception, executionCount, context) -> {
                    if (executionCount > 3) {
                        log.warn("Maximum tries reached for client http pool ");
                        return false;
                    }
                    if (exception instanceof NoHttpResponseException     //NoHttpResponseException 重试
                            || exception instanceof ConnectTimeoutException //连接超时重试
//              || exception instanceof SocketTimeoutException    //响应超时不重试，避免造成业务数据不一致
                    ) {
                        log.warn("NoHttpResponseException on " + executionCount + " call");
                        return true;
                    }
                    return false;
                })
                //最大空闲时间为10秒
                .evictIdleConnections(10, TimeUnit.SECONDS)
                .build();
    }

    @Bean
    public ConnectionKeepAliveStrategy connectionKeepAliveStrategy() {
        return (response, context) -> {
            HeaderElementIterator it = new BasicHeaderElementIterator
                    (response.headerIterator(HTTP.CONN_KEEP_ALIVE));
            while (it.hasNext()) {
                HeaderElement he = it.nextElement();
                String param = he.getName();
                String value = he.getValue();

                if (value != null && param.equalsIgnoreCase("timeout")) {
                    return Long.parseLong(value) * 1000;
                }
            }
            return defaultKeepAliveTimeMillis;
        };
    }

    @Bean
    public ClientHttpRequestFactory httpRequestFactory() {
        return new HttpComponentsClientHttpRequestFactory(closeableHttpClient()) ;
    }

    @Bean
    public RestTemplate restTemplate() {
        List<ClientHttpRequestInterceptor> list = new ArrayList<ClientHttpRequestInterceptor>(){{
            add(new CustomClientHttpRequestInterceptor());
        }};
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(httpRequestFactory());
        restTemplate.setErrorHandler(new CustomClientErrorHandler());
        restTemplate.setInterceptors(list);
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        MediaType[] mediaTypes = new MediaType[]{
                MediaType.APPLICATION_JSON,
                MediaType.APPLICATION_OCTET_STREAM,
                MediaType.TEXT_HTML,
                MediaType.TEXT_PLAIN,
                MediaType.TEXT_XML,
                MediaType.APPLICATION_STREAM_JSON,
                MediaType.APPLICATION_ATOM_XML,
                MediaType.APPLICATION_FORM_URLENCODED,
                MediaType.APPLICATION_JSON_UTF8,
                MediaType.APPLICATION_PDF,
        };
        Iterator<HttpMessageConverter<?>> iterator = messageConverters.iterator();
        while (iterator.hasNext()) {
            HttpMessageConverter<?> converter = iterator.next();
            //解决中文乱码
            if (converter instanceof StringHttpMessageConverter) {
                ((StringHttpMessageConverter) converter).setDefaultCharset(StandardCharsets.UTF_8);
            }
            //解决[Could not extract response: no suitable HttpMessageConverter found for response type [class ]]异常
            if(converter instanceof MappingJackson2HttpMessageConverter){
                ((MappingJackson2HttpMessageConverter) converter).setSupportedMediaTypes(Arrays.asList(mediaTypes));
            }
        }
        return restTemplate;
    }

    /**
     * 错误处理程序
     * */
    public static class CustomClientErrorHandler implements ResponseErrorHandler {
        private final Logger LOG = LoggerFactory.getLogger(CustomClientErrorHandler.class);

        @Override
        public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
            return clientHttpResponse.getStatusCode().is4xxClientError() || clientHttpResponse.getStatusCode().is5xxServerError();
        }

        @Override
        public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
            LOG.error("HTTP Status Code: " + clientHttpResponse.getStatusCode().value());
        }
    }

    /**
     * HTTP请求拦截器
     * */
    public static class CustomClientHttpRequestInterceptor implements ClientHttpRequestInterceptor {
        private Logger LOG = LoggerFactory.getLogger(CustomClientHttpRequestInterceptor.class);

        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
                StopWatch stopWatch = new StopWatch();
                stopWatch.start();
                ClientHttpResponse response = execution.execute(request, body);
                stopWatch.stop();
                Object[] obj = {request.getURI(), request.getMethodValue(), stopWatch.getLastTaskTimeMillis()};
                LOG.info("URI: {} ,HTTP Method: {},costTime:{} ", obj);
                return response;
        }
    }
}

