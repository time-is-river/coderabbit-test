package com.fly.entity.barcode;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 根据条形码查询商品信息返回(免费)
 * @author
 * @date 2021/6/3 15:47
 */
@Data
public class BarcodeFreeResponse {
    @JsonProperty("code")
    Integer  code;
    @JsonProperty("msg")
    String msg;
    @JsonProperty("data")
    BarcodeInfo data;
}
