package com.leopold.store.aop;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.UUID;

@Component
@Aspect
public class TimerAspect {
    private static final Logger logger = LoggerFactory.getLogger(TimerAspect.class);

    @Around("execution(* com.leopold.store.service.*.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        String requestId = UUID.randomUUID().toString();
        long start = System.currentTimeMillis();

        // 添加请求信息（如果在Web上下文中）
        String requestInfo = getRequestInfo();

        logger.debug("Request [{}] - Method [{}] started. {}", requestId, methodName, requestInfo);

        try {
            Object result = joinPoint.proceed();
            long timeElapsed = System.currentTimeMillis() - start;

            logger.info("Request [{}] - Method [{}] finished. Time elapsed: {}ms", requestId, methodName, timeElapsed);

            if (timeElapsed > 1000) {
                logger.warn("Request [{}] - Method [{}] execution time exceeded 1 second: {}ms", requestId, methodName, timeElapsed);
            }

            return result;
        } catch (Throwable t) {
            logger.error("Request [{}] - Method [{}] threw an exception: {}", requestId, methodName, t.getMessage(), t);
            throw t;
        }
    }

    private String getRequestInfo() {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                return String.format("URL: %s, HTTP Method: %s", request.getRequestURL().toString(), request.getMethod());
            }
        } catch (Exception e) {
            logger.warn("Unable to get request information", e);
        }
        return "";
    }
}








