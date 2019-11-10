package com.max.product.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer categoryType;
    private String vendor;
    private String category;
    private Integer productStock;
    private Date createTime;
    private Date updateTime;

}
