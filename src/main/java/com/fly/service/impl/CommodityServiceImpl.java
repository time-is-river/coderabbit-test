package com.fly.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fly.component.BarcodeApi;
import com.fly.component.QiniuApi;
import com.fly.config.security.SysUserDetail;
import com.fly.config.security.UserRequest;
import com.fly.entity.Attachment;
import com.fly.entity.Commodity;
import com.fly.entity.barcode.BarcodeFreeResponse;
import com.fly.entity.barcode.BarcodeInfo;
import com.fly.mapper.AttachmentMapper;
import com.fly.mapper.CommodityMapper;
import com.fly.mapper.CommodityTypeMapper;
import com.fly.request.CommodityInfo;
import com.fly.request.CommodityPageRequest;
import com.fly.response.ResponseUtils;
import com.fly.response.ResultCode;
import com.fly.service.CommodityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fly.service.SysRoleService;
import com.fly.utils.Constants;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author flyManage
 * @since 2021-06-04
 */
@Slf4j
@Service
public class CommodityServiceImpl extends ServiceImpl<CommodityMapper, Commodity> implements CommodityService {

    @Autowired
    private CommodityMapper commodityMapper;

    @Autowired
    private AttachmentMapper attachmentMapper;

    @Autowired
    private CommodityTypeMapper commodityTypeMapper;

    @Autowired
    private BarcodeApi barcodeApi;

    @Autowired
    private QiniuApi qiniuApi;

    @Autowired
    private SysRoleService sysRoleService;

    @Override
    public PageInfo<Commodity> pageQueryCommodity(CommodityPageRequest request) {
        PageHelper.startPage(request.getPage(), request.getSize());
        PageInfo<Commodity> pageInfo = new PageInfo<>(commodityMapper.queryCommodityList(request.getBarcode()));
        //获取当前用户是否拥有管理员角色
        //String roleType = sysRoleService.queryUserRoleType();
        pageInfo.getList().forEach(item->{
            //条码为null 设置为空字符串
            if (item.getBarcode() == null) {
                item.setBarcode("");
            }
            //获取商品附件信息
            List<Attachment> attachments = attachmentMapper.queryAllByCommodityId(item.getId());
            if (attachments.size() != 0) {
                //获取最新的一张图片信息放在最外层
                Attachment att = attachments.get(0);
                if (att != null && StringUtils.isNotEmpty(att.getAttachmentAddress())) {
                    item.setAttachmentAddress(att.getAttachmentAddress());
                }
            }
            /*//如果是普通用户 隐藏价格信息
            if (!Constants.RoleType.ADMIN.equals(roleType)) {
                item.setSalePrice("***");
            }*/
        });
        return pageInfo;
    }

    /**
     * 根据商品Id 获取商品信息
     * @param id
     * @return
     */
    @Override
    public ResponseUtils queryGoodsInfoById(Long id) {
        Commodity commodity = commodityMapper.selectById(id);
        if (commodity != null) {
            if (commodity.getCommodityType() != null) {
                commodity.setCommodityTypeName(commodityTypeMapper.selectById(commodity.getCommodityType()).getName());
            }
            Attachment attachment = attachmentMapper.queryOneByCommodityId(id);
            //使用 Optional
            Optional<Attachment> attachmentOptional = Optional.ofNullable(attachment);
            commodity.setAttachmentAddress(attachmentOptional.map(Attachment::getAttachmentAddress).orElse(null));
            //获取所有商品图片列表
            List<Attachment> attachments = attachmentMapper.queryAllByCommodityId(id);
            commodity.setAttachments(attachments);
        }
        return ResponseUtils.response(ResultCode.SUCCESS.getCode(), "查询成功", commodity);
    }

    /**
     * 查询数据库和外部接口 获取商品信息
     * @param barcode
     * @return
     */
    @Override
    public ResponseUtils queryGoodsInfoByBarcode(String barcode) {
        Commodity commodity = commodityMapper.queryCommodityByBarcode(barcode);
        if (commodity == null) {
            //数据库不存在商品信息 调取免费的条码查询接口获取商品信息
            BarcodeFreeResponse result = barcodeApi.queryGoodsInfoByBarcodeForFree(barcode);
            if (result!=null && Constants.ApiResultCode.SUCCESS.equals(result.getCode())) {
                log.info("第三方接口查询商品信息成功.queryResult:{} ", result);
                BarcodeInfo barcodeInfo = result.getData();
                commodity = new Commodity();
                commodity.setBarcode(barcode);
                if (StringUtils.isNotEmpty(barcodeInfo.getPrice())) {
                    commodity.setSalePrice(new BigDecimal(barcodeInfo.getPrice()));
                }
                commodity.setName(barcodeInfo.getGoodsName());
                commodity.setBrand(barcodeInfo.getBrand());
                commodity.setManufacturerName(barcodeInfo.getSupplier());
                commodity.setDescription(barcodeInfo.getStandard());
                return ResponseUtils.response(ResultCode.SUCCESS.getCode(), "通过外部接口查询商品信息成功！", commodity);
            } else {
                log.info("数据库和外部接口均未查询到商品信息！.barcode:{} ", barcode);
                return ResponseUtils.response(ResultCode.SUCCESS.getCode(), "数据库和外部接口均未查询到商品信息！", null);
            }
        } else {
            //商品已存在，获取商品类型、附件图片信息
            if (commodity.getCommodityType() != null) {
                commodity.setCommodityTypeName(commodityTypeMapper.selectById(commodity.getCommodityType()).getName());
            }
            Attachment attachment = attachmentMapper.queryOneByCommodityId(commodity.getId());
            //使用 Optional
            Optional<Attachment> attachmentOptional = Optional.ofNullable(attachment);
            commodity.setAttachmentAddress(attachmentOptional.map(Attachment::getAttachmentAddress).orElse(null));
            //获取所有商品图片列表
            List<Attachment> attachments = attachmentMapper.queryAllByCommodityId(commodity.getId());
            commodity.setAttachments(attachments);
            log.info("数据库已存在该商品信息！.commodity:{} ", commodity);
            return ResponseUtils.response(203, "已存在该商品信息", commodity);
        }
    }

    @Override
    public ResponseUtils uploadPic(MultipartFile file) {
        StringBuffer path = new StringBuffer("http://");
        try {
            FileInputStream inputStream = (FileInputStream)file.getInputStream();
            path.append(qiniuApi.uploadImg(inputStream));
            log.info("上传图片信息成功 imageUrl：{}", path.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseUtils.response(ResultCode.SUCCESS.getCode(), "上传成功！", path.toString());
    }

    @Override
    @Transactional
    public ResponseUtils saveCommodityInfo(CommodityInfo commodityInfo) {
        if (commodityInfo != null && commodityInfo.getCommodityId() != null) {
            //保存商品信息
            Long commodityId = commodityInfo.getCommodityId();
            Commodity commodity = commodityMapper.selectById(commodityId);
            if (commodity != null) {
                commodity.setBarcode("".equals(commodityInfo.getBarcode())?null:commodityInfo.getBarcode());
                commodity.setName(commodityInfo.getName());
                commodity.setSalePrice(commodityInfo.getSalePrice());
                commodity.setRemark(commodityInfo.getRemark());
                commodity.setCommodityType(commodityInfo.getCommodityType());
                //commodity.setCommodityTypeName(commodityTypeMapper.selectById(commodityInfo.getCommodityType()).getName());
                commodity.setManufacturerName(commodityInfo.getManufacturerName());
                commodity.setBrand(commodityInfo.getBrand());
                commodity.setRepertory(commodityInfo.getRepertory());
                commodity.setUpdateDate(LocalDateTime.now());
                commodity.setUpdatePerson(UserRequest.getCurrentUser().getUsername());
                commodity.setUnits(commodityInfo.getUnits());
                commodityMapper.updateById(commodity);
            } else {
                return ResponseUtils.response(ResultCode.SUCCESS.getCode(), "获取商品信息失败！", "");
            }
            //处理删除的图片信息
            List<Attachment> compareAttachmentList = new ArrayList<>();
            List<Attachment> oldAttachmentList = attachmentMapper.queryAddressByCommodityId(commodityId);
            Attachment attachment = new Attachment();
            attachment.setCommodityId(commodityId);
            //保存图片附件信息
            commodityInfo.getImageInfoList().forEach(item->{
                if (item.isAppend()) {
                    attachment.setAttachmentAddress(item.getUrl());
                    attachment.setAttachmentName(item.getName());
                    attachment.setStatus(Constants.DataStatus.VALID);
                    attachment.setType(Constants.AttachmentType.PICTURE);
                    attachment.setCreatePerson(UserRequest.getCurrentUser().getUsername());
                    attachmentMapper.insert(attachment);
                } else {
                    Attachment oldAttachment = new Attachment();
                    oldAttachment.setCommodityId(commodityId);
                    oldAttachment.setAttachmentAddress(item.getUrl());
                    compareAttachmentList.add(oldAttachment);
                }
            });
            //处理删除掉的商品图片
            List<Attachment> deleteAttachmentList = oldAttachmentList.stream().filter(o -> !compareAttachmentList.contains(o)).collect(Collectors.toList());
            deleteAttachmentList.forEach(item -> {
                attachmentMapper.deleteAttachmentByAddress(item);
            });
            log.info("更新商品信息成功.commodityInfo:{}。", commodityInfo);
        } else {
            // 新增逻辑
            Commodity commodity = new Commodity();
            String userName = UserRequest.getCurrentUser().getUsername();
            commodity.setBarcode("".equals(commodityInfo.getBarcode())?null:commodityInfo.getBarcode());
            commodity.setName(commodityInfo.getName());
            commodity.setSalePrice(commodityInfo.getSalePrice());
            commodity.setRemark(commodityInfo.getRemark());
            commodity.setCommodityType(commodityInfo.getCommodityType());
            //commodity.setCommodityTypeName(commodityTypeMapper.selectById(commodityInfo.getCommodityType()).getName());
            commodity.setRepertory(commodityInfo.getRepertory());
            commodity.setManufacturerName(commodityInfo.getManufacturerName());
            commodity.setBrand(commodityInfo.getBrand());
            commodity.setCreateDate(LocalDateTime.now());
            commodity.setCreatePerson(userName);
            commodity.setUpdateDate(LocalDateTime.now());
            commodity.setUpdatePerson(userName);
            commodity.setUnits(commodityInfo.getUnits());
            commodityMapper.insert(commodity);
            //新增附件信息
            Attachment attachment = new Attachment();
            attachment.setCommodityId(commodity.getId());
            Long commodityId = commodity.getId();
            //保存图片附件信息
            attachment.setCommodityId(commodityId);
            commodityInfo.getImageInfoList().forEach(item->{
                attachment.setAttachmentAddress(item.getUrl());
                attachment.setAttachmentName(item.getName());
                attachment.setStatus(Constants.DataStatus.VALID);
                attachment.setType(Constants.AttachmentType.PICTURE);
                attachment.setCreateTime(LocalDateTime.now());
                attachment.setCreatePerson(UserRequest.getCurrentUser().getUsername());
                attachmentMapper.insert(attachment);
            });
            log.info("新增商品信息成功.commodityInfo:{}。", commodityInfo);
        }
        return ResponseUtils.response(ResultCode.SUCCESS.getCode(), "保存成功！", "");
    }

    @Override
    public ResponseUtils deleteCommodityInfo(Long id) {
        Commodity commodity = commodityMapper.selectById("100");
        if (commodity == null) {
            return  ResponseUtils.response(ResultCode.FAIL.getCode(),"获取商品信息失败！", "");
        } else {
            commodity.setStatus(Constants.DataStatus.INVALID);
            commodityMapper.updateById(commodity);
        }
        log.info("商品信息删除成功.commodity:{};操作人：username:{}", commodity, UserRequest.getCurrentUser().getUsername());
        return ResponseUtils.response(ResultCode.SUCCESS.getCode(), "操作成功！", "");
    }
}
