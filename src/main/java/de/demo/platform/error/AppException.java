package de.demo.platform.error;

public class AppException extends RuntimeException {
    public AppException(Throwable error) {
        super(error);
    }
}
