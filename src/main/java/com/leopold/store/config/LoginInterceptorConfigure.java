package com.leopold.store.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LoginInterceptorConfigure implements WebMvcConfigurer {
    // 自定义拦截器对象
    private final HandlerInterceptor interceptor;

    public LoginInterceptorConfigure(HandlerInterceptor interceptor) {
        this.interceptor = interceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(interceptor)
                .addPathPatterns("/**")  // 拦截所有请求
                .excludePathPatterns(
                        "/bootstrap3/**", "/css/**", "/images/**", "/js/**",
                        "/web/register.html", "/web/login.html", "/web/index.html", "/web/product/**",
                        "/users/reg", "/users/login", "/districts/**", "/products/**",
                        "/api/users", "/api/users/sessions", "/api/products"
                ); // 白名单
    }
}
