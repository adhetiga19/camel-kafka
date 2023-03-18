package com.xyz.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProductListReq {

    @JsonProperty("products")
    private List<ProductReq> productListList;
}
