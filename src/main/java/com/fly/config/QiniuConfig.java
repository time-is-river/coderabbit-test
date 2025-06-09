package com.fly.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author
 * @date 2021/6/15 13:43
 */
@Component
@ConfigurationProperties(prefix = "qiniu")
public class QiniuConfig {

    /**
     * 七牛云 accessKey
     */
    public static String accessKey;

    /**
     * 七牛云 secretKey
     */
    public static String secretKey;

    /**
     * 七牛云 存储空间名
     */
    public static String bucket;

    /**
     * 七牛云 图片上传地址
     */
    public static String path;

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
