package com.fly.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 商品类型表
 * </p>
 *
 * @author flyManage
 * @since 2021-06-24
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    public class CommodityType implements Serializable {

    private static final long serialVersionUID=1L;

      /**
     * ID
     */
        @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      /**
     * 类型名称
     */
      private String name;

      /**
     * 详细描述
     */
      private String description;

      /**
       * 创建时间
       */
      private LocalDateTime createDate;

      /**
     * 状态，0:正常，1：删除，2：禁用
     */
      private String status;

      /**
       * 类型名称
       */
      @TableField(exist = false)
      private Long value;

      /**
       * 类型名称
       */
      @TableField(exist = false)
      private String text;

}
