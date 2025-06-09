package com.fly.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 小程序配置
 * @author
 * @date 2021/5/20 14:17
 */
@Component
@ConfigurationProperties(prefix = "tencent")
public class TencentConfig {

    /**
     * 请求 url
     */
    public static String url;

    /**
     * 腾讯API secretId
     */
    public static String secretId;

    /**
     * 腾讯API appSecret
     */
    public static String secretKey;

    public void setUrl(String url) {
        this.url = url;
    }

    public void setAppId(String secretId) {
        this.secretId = secretId;
    }

    public void setSecret(String secretKey) {
        this.secretKey = secretKey;
    }
}
