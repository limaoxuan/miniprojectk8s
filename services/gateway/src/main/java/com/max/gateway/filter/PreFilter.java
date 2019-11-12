package com.max.gateway.filter;

import com.google.gson.Gson;
import com.max.gateway.VO.MessageVO;
import com.max.gateway.VO.ResultVO;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import tools.JWTUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class PreFilter extends ZuulFilter {
    @Value("${exclude-routes.urls}")
    private String excludeUrls;

    @Value("${auth-url}")
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
    public Object run() {

//        try {
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
        String token = request.getHeader("token");
        if (token == null) {
            String resJsonString = this.gson.toJson(MessageVO.normalReturn("token  invalid"));
            output(ctx, resJsonString);
            return null;
        }


        JWTUtil jwtUtil = new JWTUtil();
        try {
            Claims claims = jwtUtil.parseJWT(token);
            System.out.println(claims.getSubject());
            ctx.addZuulRequestHeader("api-key", apiKey);
            ctx.addZuulRequestHeader("user-email", claims.getSubject());
        } catch (Exception e) {
            String resJsonString = this.gson.toJson(MessageVO.normalReturn("token  invalid"));
            output(ctx, resJsonString);
            return null;
        }


        return null;
    }

    private void output(RequestContext ctx, String resJsonString) {
        ctx.setSendZuulResponse(false);
        ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        PrintWriter out = null;
        try {
            out = ctx.getResponse().getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ctx.getResponse().setContentType("application/json");
        ctx.getResponse().setCharacterEncoding("UTF-8");
        out.write(resJsonString);
    }


    public boolean excludeUrl(String[] excludeUrls, String url) {
        for (String str : excludeUrls) {
            System.out.println(str);
            System.out.println(url);
            if (url.contains(str)) {
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
