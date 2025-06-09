package com.fly.request;

import lombok.Data;

/**
 * @author
 * @date 2021/11/25 15:22
 */
@Data
public class UserPasswordRequest {
    /**
     * 原密码
     */
    private String oldPassword;

    /**
     * 新密码
     */
    private String password;
}
