package com.max.order.controller;

import com.max.order.VO.ResultMessage;
import com.max.order.VO.ResultVO;

import com.max.order.domin.Order;
import com.max.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Value("${api.key}")
    private String apiKey;


    @Value("${payment.url}")
    private String paymentUrl;
    @Autowired
    OrderService orderService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResultVO<?> order(@RequestHeader("user-email") String email, @RequestBody Order order) {


        order.setUserEmail(email);
        order.setStatus("paying");
        orderService.save(order);

        ResponseEntity<String> responseEntity = request(paymentUrl + "/" + order.getPaymentMethod(), "");
        System.out.println(responseEntity.getBody());
        order.setStatus("shipping");
        orderService.save(order);

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
