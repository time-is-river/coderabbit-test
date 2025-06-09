package com.fly.config.security.handler;

import com.fly.response.ResponseUtils;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录失败处理返回
 * @author
 * @date 2021/5/17 9:42
 */
@Component
public class UserLoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errMessage;
        if (exception instanceof AccountExpiredException) {
            //账号过期
            errMessage = "登录账号已过期";

        } else if (exception instanceof BadCredentialsException) {
            //密码错误
            errMessage = "密码错误";
        } else if (exception instanceof CredentialsExpiredException) {
            //密码过期
            errMessage = "密码过期";

        } else if (exception instanceof DisabledException) {
            //账号不可用
            errMessage = "账号不可用";
        } else if (exception instanceof LockedException) {
            //账号锁定
            errMessage = "账号锁定";
        } else if (exception instanceof InternalAuthenticationServiceException) {
            //用户不存在
            errMessage = "用户不存在";
        } else {
            //其他错误
            errMessage = "登录失败，其他原因";
        }

        ResponseUtils.responseJson(response, ResponseUtils.response(500, errMessage, exception.getMessage()));
    }
}
