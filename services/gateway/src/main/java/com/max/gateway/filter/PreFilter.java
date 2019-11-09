package com.max.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;

public class PreFilter extends ZuulFilter {
    @Value("${router.auth.url}")
    private String authUrl;

    @Value("${api.key}")
    private String apiKey;

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
        // add zuul api-key header for  authorization
        ctx.addZuulRequestHeader("api-key",apiKey);

        System.out.println(request.getRequestURL());
        System.out.println(apiKey);





        return null;
    }
}
