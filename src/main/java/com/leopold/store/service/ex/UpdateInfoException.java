package com.leopold.store.service.ex;

public class UpdateInfoException extends ServiceException {
    public UpdateInfoException() {
    }

    public UpdateInfoException(String message) {
        super(message);
    }

    public UpdateInfoException(String message, Throwable cause) {
        super(message, cause);
    }

    public UpdateInfoException(Throwable cause) {
        super(cause);
    }

    public UpdateInfoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
