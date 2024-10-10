package com.leopold.store.controller.ex;

public class FileDamagedException extends FileUploadException {
    public FileDamagedException() {
    }

    public FileDamagedException(String message) {
        super(message);
    }

    public FileDamagedException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileDamagedException(Throwable cause) {
        super(cause);
    }

    public FileDamagedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
