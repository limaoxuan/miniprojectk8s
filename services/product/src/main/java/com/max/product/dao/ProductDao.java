package com.max.product.dao;

import com.max.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDao extends JpaRepository<Product,String>, JpaSpecificationExecutor<Product> {
}
