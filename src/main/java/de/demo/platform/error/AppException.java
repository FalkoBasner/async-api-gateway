package de.demo.platform.error;

import lombok.var;

import java.util.Optional;
import java.util.concurrent.CompletionException;

public class AppException extends RuntimeException {

    public static Optional<AppException> extractAppException(Throwable ex) {
        if (ex == null) {
            return Optional.empty();
        }

        if (ex instanceof AppException) {
            var appException = (AppException) ex;
            return Optional.of(appException);
        }

        if (ex instanceof CompletionException) {
            var otherExeption = (CompletionException) ex;
            return extractAppException(otherExeption.getCause());
        }

        return Optional.empty();
    }

    public AppException(Throwable error) {
        super(error);
    }
}
