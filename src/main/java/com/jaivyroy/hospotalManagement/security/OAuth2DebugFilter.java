package com.jaivyroy.hospotalManagement.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class OAuth2DebugFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        if (req.getRequestURI().contains("/oauth2/authorization") ||
                req.getRequestURI().contains("/login/oauth2/code")) {

            String fullUrl = req.getRequestURL().toString();
            String query = req.getQueryString();

            log.info("💥 REQUEST URL = " + fullUrl);
            log.info("💥 QUERY = " + query);
        }

        chain.doFilter(request, response);
    }
}