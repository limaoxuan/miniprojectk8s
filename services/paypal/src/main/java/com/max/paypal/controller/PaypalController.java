package com.max.paypal.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/paypal")
public class PaypalController {


    @RequestMapping("/pay")
    public String pay() {
        return "Paypal success";
    }

}
