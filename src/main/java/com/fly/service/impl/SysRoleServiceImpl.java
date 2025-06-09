package com.fly.service.impl;

import com.fly.config.security.SysUserDetail;
import com.fly.entity.SysRole;
import com.fly.mapper.SysRoleMapper;
import com.fly.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fly.utils.Constants;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Iterator;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author flyManage
 * @since 2021-04-27
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Override
    public String queryUserRoleType() {
        String roleType = Constants.RoleType.COMMON_USER;
        SysUserDetail sysUserDetails = (SysUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Iterator it = sysUserDetails.getAuthorities().iterator();
        while (it.hasNext()) {
            if (Constants.RoleType.ADMIN.equals(it.next().toString())) {
                roleType = Constants.RoleType.ADMIN;
                break;
            }
        }
        return roleType;
    }
}
