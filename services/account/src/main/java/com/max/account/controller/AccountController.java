package com.max.account.controller;

import com.google.gson.Gson;
import com.max.account.VO.ResultVO;
import com.max.account.domain.Account;
import com.max.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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

    @Value("${api.key}")
    private String apiKey;

    private Gson gson = new Gson();

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
