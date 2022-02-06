package com.inyoucells.myproj.service.filter;

import com.inyoucells.myproj.service.auth.TokenValidator;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthTokenFilter extends OncePerRequestFilter {

    private final TokenValidator tokenValidator;
    private final HandlerExceptionResolver handlerExceptionResolver;

    public AuthTokenFilter(TokenValidator tokenValidator, @Qualifier("handlerExceptionResolver") HandlerExceptionResolver handlerExceptionResolver) {
        this.tokenValidator = tokenValidator;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws ServletException, IOException {

        String token = req.getHeader("token");
        try {
            long userId = tokenValidator.parseUserId(token);
            req.setAttribute("userId", userId);
            chain.doFilter(req, resp);
        } catch (Exception exception) {
            handlerExceptionResolver.resolveException(req, resp, null, exception);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request)
            throws ServletException {
        String path = request.getRequestURI();
        return path.startsWith("/auth");
    }
}
