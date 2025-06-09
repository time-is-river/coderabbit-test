package com.fly.mapper;

import com.fly.entity.SysAuth;
import com.fly.entity.SysRole;
import com.fly.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author flyManage
 * @since 2021-04-22
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    SysUser selectUserByUserName(String userName);

    List<SysRole> findRolesByUserId(Long userId);

    List<SysAuth> findAuthByUserId(Long userId);
}
