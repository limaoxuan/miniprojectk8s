package com.max.bank.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bank")
public class BankController {

    @RequestMapping("/pay")
    public String pay() {
        return "bank success";
    }
}
