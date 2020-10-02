package de.demo.platform.http;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.net.UnknownHostException;

import static org.assertj.core.api.Assertions.assertThat;

class ReactiveHttpClientTest {
    public static final long USER_ID = 1L;
    public static final String URL = "http://jsonplaceholder.typicode.com/users/" + USER_ID;
    public static final String URL_WITH_INVALID_HOST = "http://UPS.jsonplaceholder.typicode.com/users/" + USER_ID;

    private ReactiveHttpClient client = new ReactiveHttpClient();

    @Test
    void get_user() {
        Mono<JsonNode> response = client.get(URL);

        StepVerifier.create(response)
                .assertNext(jsonNode -> {
                    assertThat(jsonNode.has("id")).isTrue();
                })
                .expectNextMatches(json -> json.has("id"))
                .verifyComplete();
    }

    @Test
    void get_user_with_invalid_host() {
        Mono<JsonNode> response = client.get(URL_WITH_INVALID_HOST);

        StepVerifier.create(response)
                .expectError(UnknownHostException.class)
                .verify();
    }
}