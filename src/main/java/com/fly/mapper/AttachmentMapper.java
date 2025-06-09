package com.fly.mapper;

import com.fly.entity.Attachment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author flyManage
 * @since 2021-06-04
 */
public interface AttachmentMapper extends BaseMapper<Attachment> {

    Attachment queryOneByCommodityId(Long commodityId);

    List<Attachment> queryAllByCommodityId(Long commodityId);

    List<Attachment> queryAddressByCommodityId(Long commodityId);

    int deleteAttachmentByAddress(Attachment attachment);

}
