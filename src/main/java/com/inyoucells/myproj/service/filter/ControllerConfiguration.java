package com.inyoucells.myproj.service.filter;

import com.inyoucells.myproj.service.auth.TokenValidator;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
public class ControllerConfiguration {

    @Bean
    public FilterRegistrationBean<AuthTokenFilter> authTokenFilterFilterRegistrationBean(TokenValidator tokenValidator, @Qualifier("handlerExceptionResolver") HandlerExceptionResolver handlerExceptionResolver) {
        FilterRegistrationBean<AuthTokenFilter> registrationBean
                = new FilterRegistrationBean<>();

        registrationBean.setFilter(new AuthTokenFilter(tokenValidator, handlerExceptionResolver));
        registrationBean.addUrlPatterns("/driver/**", "/car/**");
        registrationBean.setOrder(1);

        return registrationBean;
    }

}
