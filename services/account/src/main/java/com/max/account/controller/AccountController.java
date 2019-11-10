package com.max.account.controller;

import com.google.gson.Gson;
import com.max.account.VO.ResultMessage;
import com.max.account.VO.ResultVO;
import com.max.account.domain.Account;
import com.max.account.domain.Address;
import com.max.account.service.AccountService;
import com.max.account.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Value("${router.auth.url}")
    private String authUrl;

    @Value("${api.key}")
    private String apiKey;

    private Gson gson = new Gson();

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    AccountService accountService;

    @Autowired
    AddressService addressService;

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }

    @PostMapping("/register")
    public ResultVO<?> register(@RequestBody Account account) {
        accountService.save(account);
        return ResultMessage.normalReturn();
    }

    @RequestMapping(value = "/email", method = RequestMethod.POST)
    public ResultVO<?> addAddress(@RequestHeader("user-email") String email, @RequestBody Address address) {

        System.out.println(email);
        Account account = accountService.findAccountByEmail(email);
        address.setAccount(account);
        addressService.save(address);

        return ResultMessage.normalReturn();
    }

    @PostMapping("/login")
    public ResultVO<?> login(@RequestBody Account account) {
        System.out.println(authUrl);
        Account myAccount = accountService.login(account);
        System.out.println(myAccount);
        System.out.println("myAccount");
        if (myAccount == null) {
            return ResultMessage.normalReturn("username password wrong");
        }

        ResultVO<HashMap<String, String>> resultVO = new ResultVO<>();
        resultVO.setCode(0);
        HashMap<String, String> hashMap = new HashMap<>();
        resultVO.setData(hashMap);
        resultVO.setMsg("fail");
        if (myAccount != null) {
            resultVO.setMsg("success");
            ResponseEntity<String> response = request(authUrl + "/auth/gen_token", gson.toJson(myAccount));
            hashMap.put("token", response.getBody());
        }
        return resultVO;
    }

    private ResponseEntity<String> request(String authUrl, String data) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        headers.add("api-key", apiKey);
        //HttpEntity
        HttpEntity<String> formEntity = new HttpEntity<>(data, headers);
        return restTemplate.postForEntity(authUrl, formEntity, String.class);
    }
}
