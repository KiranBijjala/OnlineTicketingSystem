package com.capstone.ticket.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) {
//        logger.info("Before Handler execution"+httpServletRequest.getRequestURL().toString());

//        logger.info("Before Handler execution");
        httpServletResponse.setHeader("Cache-Control", "no-store"); // HTTP 1.1.
        httpServletResponse.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        httpServletResponse.setHeader("Expires", "0"); // Proxies.
        return true;
    }

}
