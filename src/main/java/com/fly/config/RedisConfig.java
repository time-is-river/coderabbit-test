package com.fly.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author
 * @date 2021/5/24 8:46
 */
@Component
@ConfigurationProperties(prefix = "redis")
public class RedisConfig {

    /**
     * redis 连接地址
     */
    String host;

    /**
     * redis 端口
     */
    String port;


    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(String port) { this.port = port; }
}
