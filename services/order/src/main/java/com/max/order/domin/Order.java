package com.max.order.domin;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_email")
    private String userEmail;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProductList;


    @Column(name = "ship_address")
    private String shipAddress;

    @Column(name = "payment_method")
    private String paymentMethod;

    private String status;

}
