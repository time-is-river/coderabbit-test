package com.fly.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author
 * @date 2021/6/21 14:03
 */
@Data
public class CommodityInfo {
    /**
     * 商品id
     */
    private Long commodityId;

    /**
     * 商品条码
     */
    private String barcode;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品价格
     */
    private BigDecimal salePrice;

    /**
     * 商品备注信息
     */
    private String remark;

    /**
     * 商品类型
     */
    private Long commodityType;

    /**
     * 商品库存
     */
    private Integer repertory;


    /**
     * 生成厂商地址
     */
    private String manufacturerName;

    /**
     * 商标
     */
    private String brand;

    /**
     * 描述
     */
    private String description;


    /**
     * 商品图片信息
     */
    private List<ImageInfo> imageInfoList;

    /**
     * 商品结算单位
     */
    private String units;
}
