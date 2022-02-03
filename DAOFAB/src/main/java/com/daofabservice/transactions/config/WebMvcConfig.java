package com.daofabservice.transactions.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.Arrays;

@Component
public class WebMvcConfig  implements WebMvcConfigurer {

    @Autowired
    private final HandlerInterceptor authenticationInterceptor;

    public WebMvcConfig(@Lazy HandlerInterceptor authenticationInterceptor){
        this.authenticationInterceptor = authenticationInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns(new ArrayList<>(Arrays.asList("/swagger-ui.html/**",
                        "/swagger-resources/**", "/actuator/**/**")));
    }
}
