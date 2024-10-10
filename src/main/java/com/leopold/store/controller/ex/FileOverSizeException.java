package com.leopold.store.controller.ex;

public class FileOverSizeException extends FileUploadException {
    public FileOverSizeException() {
    }

    public FileOverSizeException(String message) {
        super(message);
    }

    public FileOverSizeException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileOverSizeException(Throwable cause) {
        super(cause);
    }

    public FileOverSizeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
