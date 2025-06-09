package com.fly.mapper;

import com.fly.entity.Commodity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fly.entity.SysUser;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author flyManage
 * @since 2021-06-04
 */
public interface CommodityMapper extends BaseMapper<Commodity> {

    List<Commodity> queryCommodityList(String barcode);

    Commodity queryCommodityByBarcode(String barcode);

}
