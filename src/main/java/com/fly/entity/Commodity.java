package com.fly.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.List;

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
    public class Commodity implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Long id;

      /**
     * 条码号
     */
      @TableField(updateStrategy = FieldStrategy.IGNORED)  //mybatis-plus更新字段的时候设置为null，忽略实体null判断
      private String barcode;

      /**
     * 商品名称
     */
      private String name;

      /**
     * 商品类型 0:无条码,1:有条码
     */
      private Boolean haveBarcode;

      /**
     * 进货价格
     */
      private BigDecimal primePrice;

      /**
     * 零售价格
     */
      private BigDecimal salePrice;

      /**
     * 商品宽度
     */
      private BigDecimal width;

      /**
     * 商品长度
     */
      private BigDecimal height;

      /**
     * 商品深度
     */
      private BigDecimal depth;

      /**
     * 商品描述
     */
      private String description;

      /**
     * 商标
     */
      private String brand;

      /**
     * 商标名称
     */
      private String brandName;

      /**
     * 制造商名称
     */
      private String manufacturerName;

      /**
     * 制造商地址
     */
      private String manufacturerAddress;

      /**
     * 附件id
     */
      private Long attachmentId;

      /**
     * 状态，0:正常，1：删除，2：禁用
     */
      private String status;

      /**
     * 创建人
     */
      private String createPerson;

      /**
     * 创建时间
     */
      private LocalDateTime createDate;

      /**
     * 创建人
     */
      private String updatePerson;

      /**
     * 更新时间
     */
      private LocalDateTime updateDate;

      /**
     * 备注信息
     */
      private String remark;

      /**
     * 商品库存
     */
      private Integer repertory;

      /**
     * 订货电话
     */
      private String orderPhone;

    /**
     * 商品类型
     */
      private Long commodityType;

    /**
     * 商品结算单位
     */
      private String units;

  /**
   * 附件信息
   */
  @TableField(exist = false)
  private String commodityTypeName;

  /**
   * 附件信息
   */
  @TableField(exist = false)
  private List<Attachment> attachments;

  /**
   * 商品图片地址（目前只返回一条，后期需要在以列表方式返回）
   */
  @TableField(exist = false)
  private String attachmentAddress;

}
