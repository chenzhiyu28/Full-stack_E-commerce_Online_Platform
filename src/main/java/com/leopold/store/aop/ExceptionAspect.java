package com.leopold.store.aop;

import com.leopold.store.service.ex.DatabaseConnectionException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ExceptionAspect {
    @AfterThrowing(value = "execution(* com.leopold.store.service.IUserService.*(..))",
                   throwing = "exception")
    public void UserServiceExceptionThrower(JoinPoint joinPoint, Throwable exception) {
        if (exception instanceof DataAccessException) {
            throw new DatabaseConnectionException("Failed to create new user!");
        } else if (exception instanceof Exception) {
            throw new RuntimeException("An unexpected error occurred: " + exception.getMessage());
        }
    }
}
