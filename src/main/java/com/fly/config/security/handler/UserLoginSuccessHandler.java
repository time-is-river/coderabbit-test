package com.fly.config.security.handler;

import com.fly.config.security.SysUserDetail;
import com.fly.config.security.utils.JWTTokenUtil;
import com.fly.response.ResponseUtils;
import com.fly.response.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author
 * @date 2021/5/14 16:58
 */
@Slf4j
@Component
public class UserLoginSuccessHandler implements AuthenticationSuccessHandler{
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        SysUserDetail sysUserDetail = (SysUserDetail) authentication.getPrincipal();
        String token = JWTTokenUtil.createAccessToken(sysUserDetail); //"这里存放登录成功后生成的token";
        //生成token后 存放到 redis中
        JWTTokenUtil.setTokenInfo(token, sysUserDetail.getUsername(), null, null);
        log.info("用户名为 {} 的用户，登录成功！token已存放到redis中", sysUserDetail.getUsername());
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        ResponseUtils.responseJson(response, ResponseUtils.response(ResultCode.SUCCESS.getCode(), "登录成功", tokenMap));
    }
}
