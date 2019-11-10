package com.max.payment.controller;

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

    @Value("${creditUrl}")
    private String creditUrl;

    @RequestMapping("/{type}")
    public ResultVO<?> payment(@PathVariable("type") String type) {
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


        return NormalVO.normalReturn( request(paymentUrl+"/pay"));


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




