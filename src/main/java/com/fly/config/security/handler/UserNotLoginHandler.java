package com.fly.config.security.handler;

import com.fly.response.ResponseUtils;
import com.fly.response.ResultCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 匿名用户访问 没有权限处理
 * @author
 * @date 2021/5/14 9:32
 */
@Component
public class UserNotLoginHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ResponseUtils.responseJson(response, ResponseUtils.response(ResultCode.USER_NOT_LOGIN.getCode(), "未登录", authException.getMessage()));
    }
}
