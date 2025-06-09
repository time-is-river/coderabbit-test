package com.fly.service;

import com.fly.request.WechatLoginRequest;
import com.fly.response.ResponseUtils;

/**
 * @author
 * @date 2021/5/21 10:05
 */
public interface WechatService {
    ResponseUtils binding(WechatLoginRequest request);

    ResponseUtils login(String code);
}
