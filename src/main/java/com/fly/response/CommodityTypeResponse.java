package com.fly.response;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

/**
 * @author
 * @date 2021/6/24 11:05
 */
@Data
public class CommodityTypeResponse implements Serializable {
    /**
     * 类型名称
     */
    private Long value;

    /**
     * 类型名称
     */
    private String text;

    /**
     * 详细描述
     */
    private String description;
}
