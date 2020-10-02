package de.demo.platform.http.error;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class UnknownHostException extends HttpException {
    public UnknownHostException(Throwable error) {
        super(error, BAD_REQUEST);
    }
}
