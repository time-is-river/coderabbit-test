package com.fly.service;

import com.fly.entity.Commodity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fly.request.CommodityInfo;
import com.fly.request.CommodityPageRequest;
import com.fly.response.ResponseUtils;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author flyManage
 * @since 2021-06-04
 */
public interface CommodityService extends IService<Commodity> {

    PageInfo<Commodity> pageQueryCommodity(CommodityPageRequest request);

    ResponseUtils queryGoodsInfoById(Long id);

    ResponseUtils queryGoodsInfoByBarcode(String barcode);

    ResponseUtils uploadPic(MultipartFile file);

    ResponseUtils saveCommodityInfo(CommodityInfo commodityInfo);

    ResponseUtils deleteCommodityInfo(Long id);
}
