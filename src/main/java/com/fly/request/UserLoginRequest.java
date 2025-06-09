package com.fly.request;

import lombok.Data;

/**
 * 微信端登录 实体类
 * @author
 * @date 2021/5/21 10:46
 */
@Data
public class UserLoginRequest {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

}
