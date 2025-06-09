package com.fly.component;

import com.fly.config.WechatConfig;
import com.fly.entity.wechat.Code2SessionResponse;
import com.fly.utils.CustomJacksonHttpMessageConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 微信接口请求
 * @author
 * @date 2021/5/20 14:01
 */
@Slf4j
@Component
public class WechatApi {

    @Autowired
    private RestTemplate restTemplate;
    /**
     * 获取 用户 openId session_key 的url
     */
    final String url = "https://api.weixin.qq.com/sns/jscode2session?appid={appID}&secret={secret}&js_code={jsCode}&grant_type=authorization_code";

    /**
     * 使用微信code换取 openId session_key
     * @param code
     * @return
     */
    public Code2SessionResponse code2Session(String code) {
        Code2SessionResponse response;
        try {
            //RestTemplate restTemplate = new RestTemplate();
            //restTemplate.getMessageConverters().add(new CustomJacksonHttpMessageConverter());
            response = restTemplate.getForObject(
                    url,
                    Code2SessionResponse.class,
                    WechatConfig.appId,
                    WechatConfig.secret,
                    code
            );
            System.out.println("请求返回结果:" + response);
        } catch (Exception e) {
            log.error("request code2Session exception", e);
            response = new Code2SessionResponse();
            response.setErrCode("401");
            response.setErrMsg("request code2Session error");
        }
        return response;
    }

}
