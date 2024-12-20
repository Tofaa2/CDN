package me.tofaa.cdn.exceptions;

public class FileDownloadException extends RuntimeException {

    public FileDownloadException() {
    }

    public FileDownloadException(String message) {
        super(message);
    }

    public FileDownloadException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileDownloadException(Throwable cause) {
        super(cause);
    }

    public FileDownloadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
