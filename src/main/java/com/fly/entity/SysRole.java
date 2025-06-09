package com.fly.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author flyManage
 * @since 2021-04-27
 */
@Data
@TableName("sys_role")
public class SysRole implements Serializable {

  private static final long serialVersionUID=1L;

  /**
   * 角色ID
   */
  @TableId(value = "id", type = IdType.AUTO)
  private Long id;

  /**
   * 角色名称
   */
  private String roleName;


}
