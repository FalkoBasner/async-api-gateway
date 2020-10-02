package de.demo.platform.http.error;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class NotFoundException extends HttpException {
    public NotFoundException(Throwable error) {
        super(error, NOT_FOUND);
    }
}
