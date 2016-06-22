package net.squirrel.postar.client.exception;

public class AppException extends Exception {
    public AppException(String cause) {
        super(cause);
    }

    public AppException(String detailMessage, Throwable cause) {
        super(detailMessage, cause);
    }
}
