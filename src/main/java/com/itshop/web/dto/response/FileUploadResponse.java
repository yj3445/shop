package com.itshop.web.dto.response;

import lombok.Data;

import java.io.Serializable;

/**
 * 文件上传响应
 *
 * @author liufenglong
 * @date 2022/7/18
 */
@Data
public class FileUploadResponse implements Serializable {
    /**
     * minio url地址
     */
    private String minIoUrl;

    /**
     * nginx url地址
     */
    private String nginxUrl;

    public FileUploadResponse(){

    }

    public FileUploadResponse( String minIoUrl,String nginxUrl) {
        this.minIoUrl = minIoUrl;
        this.nginxUrl = nginxUrl;
    }
}
