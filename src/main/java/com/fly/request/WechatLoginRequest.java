package com.fly.request;

import lombok.Data;

/**
 * 微信端登录 实体类
 * @author
 * @date 2021/5/21 10:46
 */
@Data
public class WechatLoginRequest {

    /**
     * 微信请求code
     */
    private String code;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 微信昵称
     */
    private String nickname;
}
