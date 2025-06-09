package com.fly.service;

import com.fly.entity.CommodityType;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fly.response.ResponseUtils;

/**
 * <p>
 * 商品类型表 服务类
 * </p>
 *
 * @author flyManage
 * @since 2021-06-24
 */
public interface CommodityTypeService extends IService<CommodityType> {

    ResponseUtils queryCommodityTypeList();

}
