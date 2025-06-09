package com.fly.controller;


import com.fly.response.ResponseUtils;
import com.fly.service.CommodityTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 商品类型表 前端控制器
 * </p>
 *
 * @author flyManage
 * @since 2021-06-24
 */
@RestController
@RequestMapping("/commodityType")
public class CommodityTypeController {
    @Autowired
    private CommodityTypeService commodityTypeService;

    /*@PreAuthorize(value = "hasRole('ADMIN')")*/
    @GetMapping("queryCommodityType")
    public ResponseUtils queryCommodityType() {
        return commodityTypeService.queryCommodityTypeList();
    }
}

