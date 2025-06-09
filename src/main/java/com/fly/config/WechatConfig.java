package com.fly.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 小程序配置
 * @author
 * @date 2021/5/20 14:17
 */
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatConfig {
    /**
     * 小程序 appId
     */
    public static String appId;

    /**
     * 小程序 appSecret
     */
    public static String secret;

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
