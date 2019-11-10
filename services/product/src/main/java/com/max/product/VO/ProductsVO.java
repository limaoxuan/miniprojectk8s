package com.max.product.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.max.product.domain.Product;
import lombok.Data;

import java.util.List;

@Data
public class ProductsVO {
    @JsonProperty("products")
    List<Product> productVOList;
}
