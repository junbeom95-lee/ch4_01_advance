package com.example.advance.common.config;

import com.example.advance.common.interceptor.UserOwnerCheckInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final UserOwnerCheckInterceptor userOwnerCheckInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userOwnerCheckInterceptor).addPathPatterns("/api/user/**/email");

        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
