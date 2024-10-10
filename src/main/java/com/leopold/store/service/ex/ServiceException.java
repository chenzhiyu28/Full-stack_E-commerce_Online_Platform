package com.leopold.store.service.ex;

/* foundation of ALL service Exceptions ,service exception 基类，service只会在runtime中出错，所以extend */
public abstract class ServiceException extends RuntimeException {
    public ServiceException() {
        super();
    }

    /* frequently used */
    public ServiceException(String message) {
        super(message);
    }

    /* frequently used */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    protected ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}


/*
* 1. userName duplicated
* 2. database connection break
* */