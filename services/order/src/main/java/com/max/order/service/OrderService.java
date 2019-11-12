package com.max.order.service;

import com.max.order.dao.OrderDao;
import com.max.order.domin.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    OrderDao orderDao;


    public void save(Order order) {
        orderDao.save(order);
    }

    public Order getOne(Long id) {
        return orderDao.getOne(id);
    }

    public List<Order> getAll(String email) {
        return orderDao.getAllByUserEmail(email);
    }

}
