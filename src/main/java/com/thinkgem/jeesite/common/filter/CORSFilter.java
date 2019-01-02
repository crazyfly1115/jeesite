package com.thinkgem.jeesite.common.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CORSFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;
        HttpServletRequest  req=(HttpServletRequest) request;

        String []  allowDomain= {"http://trackapp.com:1990","http://sftpw-img.heikuai.com:8000","http://113.204.4.250:8000"};
        Set<String> allowedOrigins= new HashSet<String>(Arrays.asList(allowDomain));
        String originHeader=((HttpServletRequest) req).getHeader("Origin");
        if (allowedOrigins.contains(originHeader)) {
            res.setHeader("Access-Control-Allow-Origin", originHeader);
            res.setHeader("Allow", "*");
            res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
            res.setHeader("Access-Control-Max-Age", "3600");
            res.setHeader("Access-Control-Allow-Headers", "x-requested-with,x-token");
            res.setHeader("Access-Control-Allow-Credentials", "true");

        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}