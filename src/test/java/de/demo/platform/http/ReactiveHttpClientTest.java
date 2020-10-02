package de.demo.platform.http;

import com.fasterxml.jackson.databind.JsonNode;
import de.demo.platform.http.error.NotFoundException;
import de.demo.platform.http.error.UnknownHostException;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

class ReactiveHttpClientTest {
    public static final int USER_ID = 1;
    public static final int INVALID_USER_ID = 99;

    public static final String URL = "http://jsonplaceholder.typicode.com/users/";
    public static final String URL_WITH_INVALID_HOST = "http://UPS.jsonplaceholder.typicode.com/users/" + USER_ID;

    private ReactiveHttpClient client = new ReactiveHttpClient();

    @Test
    void get_user() {
        Mono<JsonNode> response = client.get(URL + USER_ID);

        StepVerifier.create(response)
                .assertNext(jsonNode -> {
                    assertThat(jsonNode.has("id")).isTrue();
                    assertThat(jsonNode.get("id").intValue()).isEqualTo(USER_ID);
                })
                .verifyComplete();
    }

    @Test
    void get_user_with_invalid_user_id() {
        Mono<JsonNode> response = client.get(URL + INVALID_USER_ID);

        StepVerifier.create(response)
                .expectError(NotFoundException.class)
                .verify();
    }

    @Test
    void get_user_with_invalid_host() {
        Mono<JsonNode> response = client.get(URL_WITH_INVALID_HOST);

        StepVerifier.create(response)
                .expectError(UnknownHostException.class)
                .verify();
    }
}