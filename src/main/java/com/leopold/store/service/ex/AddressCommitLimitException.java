package com.leopold.store.service.ex;

public class AddressCommitLimitException extends ServiceException {
    public AddressCommitLimitException() {
    }

    public AddressCommitLimitException(String message) {
        super(message);
    }

    public AddressCommitLimitException(String message, Throwable cause) {
        super(message, cause);
    }

    public AddressCommitLimitException(Throwable cause) {
        super(cause);
    }

    public AddressCommitLimitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
