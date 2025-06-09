package com.fly.service;

import com.fly.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author flyManage
 * @since 2021-04-27
 */
public interface SysRoleService extends IService<SysRole> {

    String queryUserRoleType();

}
