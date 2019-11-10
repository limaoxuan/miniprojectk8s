package com.max.order.domin;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(targetEntity = Order.class)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    private Integer units;

    private double price;
}
