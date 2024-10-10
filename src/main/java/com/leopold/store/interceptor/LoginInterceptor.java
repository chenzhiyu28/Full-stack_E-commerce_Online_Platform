package com.leopold.store.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    // 检测全局session 对象是否有UID？ 放行/重定向到login
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        if (request.getSession().getAttribute("uid") == null) {
            response.sendRedirect("/web/login.html");
            // 重定向到登录页面
            return false;
        }
        return true;
        // true: 放行  false: 重定向(拦截)
    }
}
