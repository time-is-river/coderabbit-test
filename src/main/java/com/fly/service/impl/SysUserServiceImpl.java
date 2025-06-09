package com.fly.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fly.config.security.SysUserDetail;
import com.fly.config.security.service.UserDetailServiceImpl;
import com.fly.config.security.utils.JWTTokenUtil;
import com.fly.entity.SysAuth;
import com.fly.entity.SysRole;
import com.fly.entity.SysUser;
import com.fly.mapper.SysUserMapper;
import com.fly.request.UserLoginRequest;
import com.fly.request.UserPasswordRequest;
import com.fly.response.ResponseUtils;
import com.fly.response.ResultCode;
import com.fly.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fly.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author flyManage
 * @since 2021-04-22
 */
@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final static Logger logger = LoggerFactory.getLogger(SysUserService.class);


    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private SysUserMapper sysUserMapper;


    @Override
    public ResponseUtils login(UserLoginRequest request) {
        //更新token 并返回
        SysUserDetail userDetail = (SysUserDetail)userDetailService.loadUserByUsername(request.getUsername());
        if (!passwordEncoder.matches(request.getPassword(), userDetail.getPassword())) {
            logger.info("用户名密码登录，密码错误：", request.getPassword());
            return ResponseUtils.response(ResultCode.USER_PASSWORD_ERROR.getCode(), "密码错误", null);
        }
        //获取 登录成功的token
        String token = JWTTokenUtil.createAccessToken(userDetail);
        //redis存储用户信息，微信 openId、sessionKey
        JWTTokenUtil.setTokenInfo(token, userDetail.getUsername(), null, null);
        log.info("用户:{} 登录成功。", userDetail.getUsername());
        return ResponseUtils.response(ResultCode.SUCCESS.getCode(), "登录成功了", token);
    }

    @Override
    public ResponseUtils modifyPassword(UserPasswordRequest request) {
        SysUserDetail sysUserDetails = (SysUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SysUser user = selectUserByUserName(sysUserDetails.getUsername());
        //校验原密码是否正确
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            logger.info("用户名密码登录，密码错误：", request.getPassword());
            return ResponseUtils.response(ResultCode.USER_PASSWORD_ERROR.getCode(), "原密码错误", null);
        }
        if (StringUtils.isEmpty(request.getPassword()) || request.getPassword().length() < 6) {
            return ResponseUtils.response(ResultCode.USER_PASSWORD_ERROR.getCode(), "新密码不能为空且长度不小于6位", null);
        }
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        sysUserMapper.updateById(user);
        log.info("用户:{} 修改密码成功。", user.getUsername());
        return ResponseUtils.response(ResultCode.SUCCESS.getCode(), "操作成功", null);
    }

    @Override
    public ResponseUtils getUserInfo() {
        SysUserDetail sysUserDetails = (SysUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SysUser user = new SysUser();
        user.setUsername(sysUserDetails.getUsername());
        user.setStatus(sysUserDetails.getStatus());
        StringBuffer roleName = new StringBuffer("");
        sysUserDetails.getAuthorities().forEach(item->{
            if (item.toString().equals(Constants.RoleType.ADMIN)) {
                roleName.append("管理员").append("、");
            } else if (item.toString().equals(Constants.RoleType.SUPER)) {
                roleName.append("超级管理员").append("、");
            } else {
                roleName.append("普通用户").append("、");
            }
        });
        user.setRoleName(roleName.deleteCharAt(roleName.length() -1).toString());
        return ResponseUtils.response(ResultCode.SUCCESS.getCode(), "获取当前用户信息成功", user);
    }

    @Override
    public SysUser selectUserByUserName(String userName) {
        return this.baseMapper.selectUserByUserName(userName);
    }

    @Override
    public List<SysRole> findRoleByUserId(Long userId) {
        return this.baseMapper.findRolesByUserId(userId);
    }

    @Override
    public List<SysAuth> findAuthByUserId(Long userId) {
        return this.baseMapper.findAuthByUserId(userId);
    }

    @Override
    public SysUser selectUserByOpenId(String openId) {
        SysUser user = new SysUser();
        user.setWechatOpenId(openId);
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(user);
        return this.baseMapper.selectOne(queryWrapper);
    }
}
