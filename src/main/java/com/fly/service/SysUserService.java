package com.fly.service;

import com.fly.entity.SysAuth;
import com.fly.entity.SysRole;
import com.fly.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fly.request.UserLoginRequest;
import com.fly.request.UserPasswordRequest;
import com.fly.response.ResponseUtils;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author flyManage
 * @since 2021-04-22
 */
public interface SysUserService extends IService<SysUser> {

    ResponseUtils login(UserLoginRequest request);

    ResponseUtils modifyPassword(UserPasswordRequest request);

    ResponseUtils getUserInfo();

    SysUser selectUserByUserName(String userName);

    List<SysRole> findRoleByUserId(Long userId);

    List<SysAuth> findAuthByUserId(Long userId);

    SysUser selectUserByOpenId(String openId);
}
