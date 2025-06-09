package com.fly.controller;


import com.fly.component.QiniuApi;
import com.fly.component.WechatApi;
import com.fly.entity.Commodity;
import com.fly.request.CommodityInfo;
import com.fly.request.CommodityPageRequest;
import com.fly.response.ResponseUtils;
import com.fly.service.CommodityService;
import com.github.pagehelper.PageInfo;
import org.bouncycastle.jcajce.provider.asymmetric.util.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author flyManage
 * @since 2021-06-04
 */

@RestController
@RequestMapping("/commodity")
public class CommodityController {
    @Autowired
    private CommodityService commodityService;

    @Autowired
    private QiniuApi qiniuApi;

    @Autowired
    private WechatApi wechatApi;

    @PreAuthorize(value = "hasRole('ADMIN') or hasRole('USER')")
    @PostMapping("/pageList")
    public PageInfo<Commodity> pageList(@RequestBody CommodityPageRequest request){
        return commodityService.pageQueryCommodity(request);
    }
    @PreAuthorize(value = "hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("queryGoodsInfoById")
    public ResponseUtils queryGoodsInfoById(@RequestParam Long id) {
        return commodityService.queryGoodsInfoById(id);
    }

    @PreAuthorize(value = "hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("queryGoodsInfoByBarcode")
    public ResponseUtils queryGoodsInfoByBarcode(@RequestParam String barcode) {
        return commodityService.queryGoodsInfoByBarcode(barcode);
    }

    /**
     * 上传图片信息到本地
     */
    @PostMapping("uploadToLocal")
    @ResponseBody
    public ResponseUtils uploadToLocal(@RequestParam("file")MultipartFile file, HttpServletResponse response) {
        StringBuffer path = new StringBuffer("http://");
        response.setContentType("text/html;charset=UTF-8");
        if (file.isEmpty()) {
            return ResponseUtils.response(500, "图片不能为空", null);
        } else {
            return commodityService.uploadPic(file);
        }
    }
    @PreAuthorize(value = "hasRole('ADMIN')")
    @PostMapping("saveCommodityInfo")
    public ResponseUtils saveCommodityInfo(@RequestBody CommodityInfo commodityInfo) {
        return commodityService.saveCommodityInfo(commodityInfo);
    }

    @PreAuthorize(value = "hasRole('SUPER')")
    @GetMapping("deleteCommodityInfo")
    public ResponseUtils deleteCommodityInfo(@RequestParam Long id) {
        return commodityService.deleteCommodityInfo(id);
    }


}

