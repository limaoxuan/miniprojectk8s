package com.max.account.controller;

import com.max.account.VO.ResultVO;
import com.max.account.domain.Account;
import com.max.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Value("${router.auth.url}")
    private String authUrl;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    AccountService accountService;

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }

    @PostMapping("/register")
    public ResultVO<String> register(@RequestBody Account account) {
        accountService.save(account);
        ResultVO<String> resultVO = new ResultVO<>();
        resultVO.setCode(0);
        resultVO.setMsg("success");
        resultVO.setData("{}");
        return resultVO;
    }

    @PostMapping("/login")
    public ResultVO<HashMap<String, String>> login(@RequestBody Account account) {
        System.out.println(authUrl);
        Account myAccount = accountService.login(account);
        ResultVO<HashMap<String, String>> resultVO = new ResultVO<>();
        resultVO.setCode(0);
        HashMap<String, String> hashMap = new HashMap<>();
        resultVO.setData(hashMap);
        resultVO.setMsg("fail");
        if (myAccount != null) {
            resultVO.setMsg("success");
            ResponseEntity<String> response
                    = restTemplate.postForEntity(authUrl + "/auth/gen_token", myAccount, String.class);
            hashMap.put("token", response.getBody());
        }
        return resultVO;
    }
}
