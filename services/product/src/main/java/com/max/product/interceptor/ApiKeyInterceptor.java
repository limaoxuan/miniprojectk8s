package com.max.product.interceptor;

import com.google.gson.Gson;
import com.max.product.VO.ResultVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Component
public class ApiKeyInterceptor implements HandlerInterceptor {
    @Value("${api.key}")
    private String apiKey;
    private Gson gson = new Gson();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(apiKey);
        String sendApiKey = request.getHeader("api-key");
        if (!apiKey.equals(sendApiKey)) {
            ResultVO<String> resultVO = new ResultVO<>();
            resultVO.setData("{}");
            resultVO.setMsg("Api key invalid");
            resultVO.setCode(0);
            String resJsonString = this.gson.toJson(resultVO);
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(resJsonString);
            out.flush();
            return false;
        }

        return true;
    }
}