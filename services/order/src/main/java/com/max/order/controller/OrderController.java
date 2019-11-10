package com.max.order.controller;

import com.google.gson.Gson;
import com.max.order.VO.ResultMessage;
import com.max.order.VO.ResultVO;

import com.max.order.domin.Order;
import com.max.order.domin.OrderProduct;
import com.max.order.domin.Product;
import com.max.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Value("${api.key}")
    private String apiKey;
    private Gson gson = new Gson();

    @Value("${payment.url}")
    private String paymentUrl;

    @Value("${product.url}")
    private String productUrl;
    @Autowired
    OrderService orderService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResultVO<?> order(@RequestHeader("user-email") String email, @RequestBody Order order) {

        Order newOrder = new Order();
        newOrder.setPaymentMethod(order.getPaymentMethod());
        newOrder.setShipAddress(order.getShipAddress());
        newOrder.setStatus("paying");
        newOrder.setUserEmail(email);
        newOrder.setOrderProductList(new ArrayList<>());

        for (OrderProduct orderProduct : order.getOrderProductList()) {
            newOrder.getOrderProductList().add(orderProduct);
        }

        System.out.println("newOrder");
        System.out.println(newOrder);
        orderService.save(newOrder);
//
//        for (OrderProduct orderProduct : order.getOrderProductList()) {
//            Product product = new Product();
//            product.setId(orderProduct.getProductId());
//            product.setUnits(orderProduct.getUnits());
//            ResponseEntity<String> responseEntity1 = request(productUrl + "/update", gson.toJson(product));
//
//        }
//
//        ResponseEntity<String> responseEntity2 = request(paymentUrl + "/" + order.getPaymentMethod(), "");
//
//        System.out.println(responseEntity2.getBody());
//        order.setStatus("shipping");
//        orderService.save(order);

        return ResultMessage.normalReturn();
    }

    private ResponseEntity<String> request(String url, String data) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        headers.add("api-key", apiKey);
        //HttpEntity
        HttpEntity<String> formEntity = new HttpEntity<>(data, headers);
        RestTemplate template = new RestTemplate();
        return template.postForEntity(url, formEntity, String.class);
    }
}
