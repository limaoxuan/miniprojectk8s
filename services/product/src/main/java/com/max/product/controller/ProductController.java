package com.max.product.controller;

import com.max.product.VO.ProductsVO;
import com.max.product.VO.ResultVO;
import com.max.product.domain.Product;
import com.max.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResultVO<ProductsVO> list() {
        List<Product> productList = productService.findAllProduct();
        ResultVO<ProductsVO> resultVO = new ResultVO();
        ProductsVO productVO = new ProductsVO();
        productVO.setProductVOList(productList);
        resultVO.setCode(0);
        resultVO.setMsg("success");
        resultVO.setData(productVO);
        return resultVO;
    }

    public ResultVO<?> addProduct(@RequestBody Product product) {
        productService.addProduct(product);
        ResultVO<String> resultVO = new ResultVO<>();
        resultVO.setCode(0);
        resultVO.setMsg("success");
        resultVO.setData("{}");
        return resultVO;
    }

}
