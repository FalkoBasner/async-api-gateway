package de.demo.platform.http;

import de.demo.platform.http.error.HttpClientException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.http.HttpStatus.OK;

class RestTemplateHttpClientTest {
    public static final long USER_ID = 1L;
    public static final String URL = "http://jsonplaceholder.typicode.com/users/" + USER_ID;
    public static final String URL_WITH_INVALID_HOST = "http://UPS.jsonplaceholder.typicode.com/users/" + USER_ID;

    private RestTemplateHttpClient client = new RestTemplateHttpClient();

    @Test
    @SneakyThrows
    public void get() {
        final ResponseEntity<String> resp = client.get(URL);
        assertThat(resp.getStatusCode()).isEqualTo(OK);
    }

    @Test
    public void get_With_unknown_host() {
        assertThrows(HttpClientException.class, () -> client.get(URL_WITH_INVALID_HOST));
    }

}