package com.leopold.store.controller.ex;

public abstract class FileUploadException extends RuntimeException {
    // constructors cannot be extended

    public FileUploadException() {
        super();
    }

    /* frequently used */
    public FileUploadException(String message) {
        super(message);
    }

    /* frequently used */
    public FileUploadException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileUploadException(Throwable cause) {
        super(cause);
    }

    protected FileUploadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
