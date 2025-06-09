package com.fly.request;

import lombok.Data;

/**
 * @author
 * @date 2021/6/21 15:27
 */
@Data
public class ImageInfo {
    /**
     * 图片名称
     */
    private String name;

    /**
     * 图片地址
     */
    private String url;

    /**
     * 图片是否为新增
     */
    private boolean append;
}
