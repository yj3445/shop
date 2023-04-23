package com.itshop.web.dao.deviceControl;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

/**
 * rest基础服务
 *
 * @author liufenglong
 * @date 2022/5/7
 */
@Slf4j
public class RestBaseRepository {

    @Value("${deviceControl.apiUrl}")
    protected String apiUrl;

    @Autowired
    protected RestTemplate restTemplate;


    /**
     * rest-exchange 方法
     *
     * @param url 地址
     * @param method 请求方法
     * @param responseBodyType 响应类型
     * @param requestBody 请求体
     * @param <T> 响应体类型
     * @param <A> 请求体类型
     * @return 响应体
     * @throws RestClientException
     */
    protected <T, A> T exchange(String url, HttpMethod method, ParameterizedTypeReference<T> responseBodyType, A requestBody) throws RestClientException {
        // 请求头
        HttpHeaders headers = new HttpHeaders();
        MimeType mimeType = MimeTypeUtils.parseMimeType("application/json");
        MediaType mediaType = new MediaType(mimeType.getType(), mimeType.getSubtype(), Charset.forName("UTF-8"));
        // 请求体
        headers.setContentType(mediaType);
        // 发送请求
        HttpEntity<A> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<T> resultEntity = restTemplate.exchange(url, method, entity, responseBodyType);
        T response = resultEntity.getBody();
        log.info("url:{},method:{},request:{},response:{}", url, method, requestBody, JSONObject.toJSONString(response));
        return response;
    }
}
