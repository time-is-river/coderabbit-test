package com.fly.service.impl;

import com.fly.entity.CommodityType;
import com.fly.mapper.CommodityTypeMapper;
import com.fly.response.CommodityTypeResponse;
import com.fly.response.ResponseUtils;
import com.fly.response.ResultCode;
import com.fly.service.CommodityTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品类型表 服务实现类
 * </p>
 *
 * @author flyManage
 * @since 2021-06-24
 */
@Service
public class CommodityTypeServiceImpl extends ServiceImpl<CommodityTypeMapper, CommodityType> implements CommodityTypeService {

    @Autowired
    private CommodityTypeMapper commodityTypeMapper;

    @Override
    public ResponseUtils queryCommodityTypeList() {
        List<CommodityTypeResponse> commodityTypes = commodityTypeMapper.queryCommodityTypeList();
        return ResponseUtils.response(ResultCode.SUCCESS.getCode(), "查询成功", commodityTypes);
    }
}
