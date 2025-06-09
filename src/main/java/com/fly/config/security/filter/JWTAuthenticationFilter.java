package com.fly.config.security.filter;

import com.fly.config.JWTConfig;
import com.fly.config.security.SysUserDetail;
import com.fly.config.security.utils.JWTTokenUtil;
import com.fly.response.ResponseUtils;
import com.fly.response.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT 权限过滤 校验token 是否合法
 * @author
 * @date 2021/5/18 17:25
 */
@Slf4j
public class JWTAuthenticationFilter extends BasicAuthenticationFilter {

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }


   @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        String token = request.getHeader(JWTConfig.tokenHeader);
       if (token != null && token.startsWith(JWTConfig.tokenPrefix)) {
           if (JWTTokenUtil.hasToken(token)) {
               //过期时间
               String expiration = JWTTokenUtil.getExpirationByToken(token);
               String username = JWTTokenUtil.getUserNameByToken(token);
               //token 过期后判断是否超过刷新时间
               if (JWTTokenUtil.isExpiration(expiration)) {
                   String refreshTime = JWTTokenUtil.getRefreshTimeByToken(token);
                   if (JWTTokenUtil.isValid(refreshTime)) {
                       //token 已过期但没有超过刷新时间 刷新token 并放入redis
                       String newToken = JWTTokenUtil.refreshAccessToken(token);
                       response.setHeader(JWTConfig.tokenHeader, newToken);//需要处理token更新后 返回前台的情况
                       JWTTokenUtil.setTokenInfo(newToken, username, null, null);
                       JWTTokenUtil.deleteRedisToken(token);//删除token
                       log.info("{}的登录状态已过期,但未超出刷新时间。token已刷新!", username);
                   } else {
                       log.info("{}的登录状态已过期,请重新登录!", username);
                       ResponseUtils.responseJson(response, ResponseUtils.response(ResultCode.USER_ACCOUNT_EXPIRED.getCode(), "用户登录状态已过期", ""));
                       return;
                   }
               }
               SysUserDetail userDetail = JWTTokenUtil.parseAccessToken(token);
               if (userDetail != null) {
                   UsernamePasswordAuthenticationToken authenticationToken
                           = new UsernamePasswordAuthenticationToken(
                           userDetail,userDetail.getId(),userDetail.getAuthorities());
                   SecurityContextHolder.getContext().setAuthentication(authenticationToken);
               }
           }
       }
       filterChain.doFilter(request, response);
   }
}
