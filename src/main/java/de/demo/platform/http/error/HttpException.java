package de.demo.platform.http.error;

import de.demo.platform.error.AppException;
import org.springframework.http.HttpStatus;

public class HttpException extends AppException {
    private final HttpStatus status;

    public HttpException(Throwable error, HttpStatus status) {
        super(error);
        this.status = status;
    }
}
