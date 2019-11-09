package com.max.gateway.filter;

import com.google.gson.Gson;
import com.max.gateway.VO.ResultVO;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

public class PreFilter extends ZuulFilter {
    @Value("${exclude-routes.urls}")
    private String excludeUrls;

    @Value("${auth-routes.valid-token.url}")
    private String authUrl;

    @Value("${api.key}")
    private String apiKey;

    @Autowired
    RestTemplate restTemplate;

    private Gson gson = new Gson();

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String url = request.getRequestURL().toString();
        // add zuul api-key header for  authorization
        ctx.addZuulRequestHeader("api-key", apiKey);
        System.out.println(apiKey);
        if (excludeUrl(excludeUrls.split(","), url)) {
            System.out.println("pass");
            return null;
        }
        System.out.println("sds");
        String token = request.getHeader("token");
        if (token == null) {
            HttpServletResponse response = ctx.getResponse();
            ResultVO<String> resultVO = new ResultVO<>();
            resultVO.setData("{}");
            resultVO.setMsg("Api key invalid");
            resultVO.setCode(0);
            String resJsonString = this.gson.toJson(resultVO);
            PrintWriter out = null;
            try {
                out = response.getWriter();
            } catch (IOException e) {
                e.printStackTrace();
            }
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(resJsonString);
            out.flush();
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("token", token);
        String jsonStr = gson.toJson(map);
        System.out.println(jsonStr);
        ResponseEntity<String> request1 = request(authUrl, jsonStr);
        System.out.println(request1.getBody());

        return null;
    }


    public boolean excludeUrl(String[] excludeUrls, String url) {
        for (String str : excludeUrls) {
            System.out.println(str);
            System.out.println(url);
            if (str.equals(url)) {
                return true;
            }
        }
        return false;
    }


    private ResponseEntity<String> request(String requestUrl, String data) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/json;UTF-8"));
        headers.add("api-key", apiKey);
        //HttpEntity
        HttpEntity<String> formEntity = new HttpEntity<>(data, headers);
        return restTemplate.postForEntity(requestUrl, formEntity, String.class);
    }
}
