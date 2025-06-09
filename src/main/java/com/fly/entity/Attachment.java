package com.fly.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author flyManage
 * @since 2021-06-04
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    public class Attachment implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      /**
     * 商品id
     */
      private Long commodityId;

      /**
     * 附件地址
     */
      private String attachmentAddress;

      /**
     * 附件名称
     */
      private String attachmentName;

      /**
     * 创建人
     */
      private String createPerson;

      /**
     * 创建时间
     */
      private LocalDateTime createTime;

      /**
     * 状态，0:正常，1：删除
     */
      private String status;

      /**
     * 附件类型 0:图片
     */
      private String type;



}
