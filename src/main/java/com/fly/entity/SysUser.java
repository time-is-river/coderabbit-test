package com.fly.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Collection;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.Transient;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * <p>
 *
 * </p>
 *
 * @author flyManage
 * @since 2021-04-22
 */
@Data
@TableName("sys_user")
public class SysUser implements/*  UserDetails,*/Serializable {
    /*@TableField(exist = false)//说明该字段 不是数据库表中的映射字段
    private Collection<? extends GrantedAuthority> authorities;*/

  private static final long serialVersionUID=1L;

  /**
   * 用户ID
   */
  @TableId(value = "id", type = IdType.AUTO)
  private Long id;

  /**
   * 用户名
   */
  private String username;

  /**
   * 密码
   */
  private String password;

  /**
   * 状态（0-正常，1-删除，2-禁用）
   */
  private String status;

  /**
   * 微信openid
   */
  private String wechatOpenId;

  /**
   * 微信unionid
   */
  private String wechatUnionId;

  /**
   * 微信用户昵称
   */
  private String wechatNickname;

  /**
   * 角色名称
   */
  @TableField(exist = false)
  private String roleName;


  /*@Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.authorities;
  }

  @Override
  public String getUsername() {
    return this.name;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }*/
}
