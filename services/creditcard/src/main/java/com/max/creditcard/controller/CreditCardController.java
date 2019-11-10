package com.max.creditcard.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/credit")
@RestController
public class CreditCardController {

    @RequestMapping("/pay")
    public String pay() {
        return "creditcard success";
    }
}
