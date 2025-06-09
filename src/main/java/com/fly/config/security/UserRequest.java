package com.fly.config.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author
 * @date 2021/6/21 16:02
 */
public class UserRequest {
    public static SysUserDetail getCurrentUser(){
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        SysUserDetail userDetails = (SysUserDetail) authenticationToken.getPrincipal();
        return userDetails;
    }
}
