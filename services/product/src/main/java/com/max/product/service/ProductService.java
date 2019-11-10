package com.max.product.service;

import com.max.product.dao.ProductDao;
import com.max.product.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductDao productDao;

    public List<Product> findAllProduct() {
        return productDao.findAll();
    }

    public void addProduct(Product product) {
        productDao.save(product);
    }

}
