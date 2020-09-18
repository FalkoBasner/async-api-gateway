package de.demo.platform.http;

import lombok.SneakyThrows;
import org.asynchttpclient.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.OK;

class HttpClientTest {
    public static final long USER_ID = 1L;
    public static final String URL = "http://jsonplaceholder.typicode.com/users/" + USER_ID;
    public static final String URL_WITH_INVALID_HOST = "http://UPS.jsonplaceholder.typicode.com/users/" + USER_ID;

    private HttpClient client = new HttpClient();

    @Test
    @SneakyThrows
    public void get() {
        final Response resp = client.get(URL).get();
        assertThat(resp.getStatusCode()).isEqualTo(OK.value());
    }

    @Test
    @SneakyThrows
    public void get_With_unknown_host() {
        try {
            client.get(URL_WITH_INVALID_HOST).get();
        } catch (ExecutionException e) {
            assertThat(e.getCause()).isInstanceOf(HttpClientException.class);
        }
    }

}