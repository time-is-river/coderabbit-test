package com.fly.config.security;

import com.fly.entity.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author
 * 系统用户信息
 * @date 2021/4/29 13:27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysUserDetail extends SysUser implements UserDetails, Serializable {
    /**
     * 保证类的序列化兼容性
     */
    private static final long serialVersionUID = 1L;

    /**
     * 用户角色
     */
    private Collection<GrantedAuthority> authorities;

    /**
     * 账号是否过期
     */
    private boolean isAccountNonExpired = true;

    /**
     * 账号是否锁定
     */
    private boolean isAccountNonLocked = true;

    /**
     * 证书是否过期
     */
    private boolean isCredentialsNonExpired = true;

    /**
     * 账号是否有效
     */
    private boolean isEnabled = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
