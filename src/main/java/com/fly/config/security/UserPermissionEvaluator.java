package com.fly.config.security;

import com.fly.entity.SysAuth;
import com.fly.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author
 * @date 2021/5/18 17:09
 */
@Component
public class UserPermissionEvaluator implements PermissionEvaluator {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 判断当前用户 是否拥有对应权限
     * @param authentication
     * @param targetDomainObject
     * @param permission
     * @return
     */
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        SysUserDetail sysUserDetails = (SysUserDetail) authentication.getPrincipal();

        Set<String> permissions = new HashSet<String>(); // 用户权限

        List<SysAuth> authList = sysUserService.findAuthByUserId(sysUserDetails.getId());
        authList.forEach(auth -> {
            permissions.add(auth.getPermission());
        });

        // 判断是否拥有权限
        if (permissions.contains(permission.toString())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
