package com.fly.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.Arrays;

/**
 * RestTemplate 请求返回处理（解决 请求响应头 包含 Content-Type 是 text/plain问题）
 * @author
 * @date 2021/5/20 17:09
 */
public class CustomJacksonHttpMessageConverter extends MappingJackson2HttpMessageConverter {
    public CustomJacksonHttpMessageConverter() {
        this(Jackson2ObjectMapperBuilder.json().build());
    }

    public CustomJacksonHttpMessageConverter(ObjectMapper build) {
        super(build);
        //this.setSupportedMediaTypes(Arrays.asList(MediaType.ALL));
        setSupportedMediaTypes(Arrays.asList(MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON, new MediaType("application", "*+json")));
    }
}
