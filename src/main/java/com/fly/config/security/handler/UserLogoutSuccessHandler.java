package com.fly.config.security.handler;

import com.fly.config.JWTConfig;
import com.fly.config.security.utils.JWTTokenUtil;
import com.fly.response.ResponseUtils;
import com.fly.response.ResultCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登出成功处理
 * @author
 * @date 2021/5/17 14:23
 */
@Component
public class UserLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //用户登出 删除redis中的token
        String token = request.getHeader(JWTConfig.tokenHeader);
        JWTTokenUtil.deleteRedisToken(token);
        SecurityContextHolder.clearContext();
        ResponseUtils.responseJson(response, ResponseUtils.response(ResultCode.SUCCESS.getCode(), "登出处理成功", null));
    }
}
