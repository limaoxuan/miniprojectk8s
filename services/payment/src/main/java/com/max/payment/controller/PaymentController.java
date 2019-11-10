package com.max.payment.controller;

import com.google.gson.Gson;
import com.max.payment.DTO.Order;
import com.max.payment.VO.NormalVO;
import com.max.payment.VO.ResultVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Value("${api.key}")
    private String apiKey;
    @Value("${paypalUrl}")
    private String paypalUrl;

    @Value("${bankUrl}")
    private String bankUrl;

    @Value("${orderUrl}")
    private String orderUrl;

    @Value("${creditUrl}")
    private String creditUrl;
    private Gson gson = new Gson();

    @RequestMapping("/{type}/{order}")
    public ResultVO<?> payment(@PathVariable("type") String type, @PathVariable("order") Long orderId) {
        System.out.println(type);
        String paymentUrl = "";
        if (type.equals("paypal")) {
            paymentUrl = paypalUrl;
        } else if (type.equals("bank")) {
            paymentUrl = bankUrl;
        } else {
            paymentUrl = creditUrl;
        }
        System.out.println(paymentUrl + "/pay");
        System.out.println(paypalUrl + "/pay");

        String result = request(paymentUrl + "/pay");


//        if (result.equals("success")) {
            request1(orderUrl, gson.toJson(new Order(orderId, "shipping")));
//        }

        return NormalVO.normalReturn("finish payment");


    }

    private String request1(String url, String data) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        headers.add("api-key", apiKey);
        //HttpEntity
        HttpEntity<String> formEntity = new HttpEntity<>(data, headers);
        RestTemplate template = new RestTemplate();
        return template.postForObject(url, formEntity, String.class);
    }

    private String request(String url) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
//        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        headers.add("api-key", apiKey);
        //HttpEntity
        HttpEntity<String> formEntity = new HttpEntity<>("", headers);
        RestTemplate template = new RestTemplate();
        return template.postForObject(url, formEntity, String.class);
    }

}




