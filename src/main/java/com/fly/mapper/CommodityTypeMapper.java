package com.fly.mapper;

import com.fly.entity.CommodityType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fly.response.CommodityTypeResponse;

import java.util.List;

/**
 * <p>
 * 商品类型表 Mapper 接口
 * </p>
 *
 * @author flyManage
 * @since 2021-06-24
 */
public interface CommodityTypeMapper extends BaseMapper<CommodityType> {
    List<CommodityTypeResponse> queryCommodityTypeList();
}
