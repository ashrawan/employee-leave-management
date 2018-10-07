//package com.lb.employeeleave.config;
//
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Component
//@Order(Ordered.HIGHEST_PRECEDENCE)
//public class WebConfig implements Filter {
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        HttpServletResponse res = (HttpServletResponse) response;
//        res.setHeader("Access-Control-Allow-Origin", "*");
//        res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
//        res.setHeader("Access-Control-Max-Age", "3600");
//        res.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept, x-requested-with, Cache-Control");
//        //chain.doFilter(req, res);
//        HttpServletRequest request1 = (HttpServletRequest) request;
//
//        if ("OPTIONS".equalsIgnoreCase(request1.getMethod())) {
//            res.setStatus(HttpServletResponse.SC_OK);
//            res.setHeader("Access-Control-Allow-Headers", "X-Requested-With,content-type,Authorization");
//        } else {
//            chain.doFilter(request, response);
//        }
//
//    }
//    @Override
//    public void destroy() {
//    }
//}