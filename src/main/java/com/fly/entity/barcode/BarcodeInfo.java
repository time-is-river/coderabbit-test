package com.fly.entity.barcode;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BarcodeInfo {
    @JsonProperty("goodsName")
    String goodsName;
    @JsonProperty("barcode")
    String barcode;
    @JsonProperty("price")
    String price;
    @JsonProperty("brand")
    String brand;
    @JsonProperty("supplier")
    String supplier;
    @JsonProperty("standard")
    String standard;
}
