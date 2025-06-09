package com.fly.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 权限表
 * </p>
 *
 * @author flyManage
 * @since 2021-05-18
 */
/*@Data
  @EqualsAndHashCode(callSuper = false)*/
@Data
@TableName("sys_auth")
    public class SysAuth implements Serializable {

    private static final long serialVersionUID=1L;

      /**
     * ID
     */
        @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      /**
     * 权限名称
     */
      private String name;

      /**
     * 权限标识
     */
      private String permission;


}
