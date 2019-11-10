package com.max.payment.controller;

import com.max.payment.VO.NormalVO;
import com.max.payment.VO.ResultVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Value("${paypalUrl}")
    private String paypalUrl;

    @Value("${bankUrl}")
    private String bankUrl;

    @Value("${creditUrl}")
    private String creditUrl;

    @RequestMapping("/{type}")
    public ResultVO<?> payment(@PathVariable("type") String type) {

        RestTemplate template = new RestTemplate();
        String paymentUrl = "";
        switch (type){
            case "paypal":
                paymentUrl = paypalUrl;
                break;
            case "bank":
                paymentUrl = bankUrl;
                break;
            case "creditCard":
                paymentUrl = creditUrl;
                break;
        }

        String message =template.postForObject(paypalUrl+"pay",paymentUrl + "/pay",String.class);
        
        return NormalVO.normalReturn(message);



    }

}




