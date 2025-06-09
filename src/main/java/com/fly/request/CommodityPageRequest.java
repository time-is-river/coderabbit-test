package com.fly.request;

import lombok.Data;

/**
 * @author
 * @date 2021/6/4 17:02
 */
@Data
public class CommodityPageRequest {


    /**
     * 商品查询条件
     */
    private String barcode;

    /**
     * 分页查询属性
     */
    private Integer page = 1;
    private Integer size = 10;
}
