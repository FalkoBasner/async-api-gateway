package de.demo.platform.http.error;

import de.demo.platform.error.AppException;

public class HttpClientException extends AppException {
    public HttpClientException(Throwable error) {
        super(error);
    }
}
