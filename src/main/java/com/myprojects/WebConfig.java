package com.myprojects;

import com.myprojects.interceptor.TransactionInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins(new String[]{"*"}).allowedMethods(new String[]{"GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS"}).allowCredentials(true);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TransactionInterceptor());
    }
}
