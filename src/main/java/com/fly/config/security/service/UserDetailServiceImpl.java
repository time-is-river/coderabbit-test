package com.fly.config.security.service;

import com.fly.config.security.SysUserDetail;
import com.fly.entity.SysRole;
import com.fly.entity.SysUser;
import com.fly.service.SysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author
 * @date 2021/4/25 14:17
 */
@Service("userDetailServiceImpl")
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        SysUser user = sysUserService.selectUserByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("当前用户：" + userName + "不存在！");
        }
        SysUserDetail sysUserDetail = new SysUserDetail();
        BeanUtils.copyProperties(user,sysUserDetail);
        //模拟权限集合
        Set<GrantedAuthority> authorities = new HashSet<>();
        List<SysRole> userRoles = sysUserService.findRoleByUserId(user.getId());
        userRoles.forEach( sysRole -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + sysRole.getRoleName()));
        });
        sysUserDetail.setAuthorities(authorities);
        //authorities.add(new SimpleGrantedAuthority("ROLE_admin"));
        //authorities.add(new SimpleGrantedAuthority("ROLE_user"));
        //将数据库的roles解析为userDetails的权限集
        //AuthorityUtils.commaSeparatedStringToAuthorityList() 将逗号分隔的字符集转成权限对象列表
        //user.setAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_admin,ROLE_user"));
        return sysUserDetail;
    }
}
