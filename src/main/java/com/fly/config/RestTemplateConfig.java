package com.fly.config;

import com.fly.utils.CustomJacksonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author
 * @date 2021/5/20 14:57
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate RestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new CustomJacksonHttpMessageConverter());
        return restTemplate;
    }
}
