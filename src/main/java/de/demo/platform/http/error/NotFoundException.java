package de.demo.platform.http.error;

public class NotFoundException extends HttpClientException {
    public NotFoundException(Throwable error) {
        super(error);
    }
}
