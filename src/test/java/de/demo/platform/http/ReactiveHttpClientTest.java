package de.demo.platform.http;

import com.fasterxml.jackson.databind.JsonNode;
import de.demo.platform.http.error.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.client.MockServerClient;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.mockserver.model.MediaType;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@ExtendWith(MockServerExtension.class)
class ReactiveHttpClientTest {
    public static final String BASE_URL_TEMPLATE = "http://localhost:%s";

    public static final String PATH = "users";
    public static final int ID = 11;
    public static final int OTHER_ID = 99;

    public static final String JSON_RESPONSE_TEMPLATE = "{\"id\":%s}";


    private ReactiveHttpClient client = new ReactiveHttpClient();

    private String baseUrl;

    @BeforeEach
    public void setup(MockServerClient mockServerClient) {
        mockServerClient.reset();
        baseUrl = format(BASE_URL_TEMPLATE, mockServerClient.getPort());
    }

    @Test
    void get(MockServerClient mockServerClient) {
        mockServerClient.when(
                request()
                        .withMethod("GET")
                        .withPath(format("/%s/%d", PATH, ID)))
                .respond(response()
                        .withContentType(MediaType.APPLICATION_JSON)
                        .withBody(format(JSON_RESPONSE_TEMPLATE, ID))
                        .withStatusCode(200));

        Mono<JsonNode> response = client.get(format("%s/%s/%d", baseUrl, PATH, ID));

        StepVerifier.create(response)
                .assertNext(jsonNode -> {
                    assertThat(jsonNode.has("id")).isTrue();
                    assertThat(jsonNode.get("id").intValue()).isEqualTo(ID);
                })
                .verifyComplete();
    }

    @Test
    void get_with_unknown_id(MockServerClient mockServerClient) {
        mockServerClient.when(
                request()
                        .withMethod("GET")
                        .withPath(format("/%s/%d", PATH, ID)))
                .respond(response()
                        .withContentType(MediaType.APPLICATION_JSON)
                        .withBody(format(JSON_RESPONSE_TEMPLATE, ID))
                        .withStatusCode(200));

        Mono<JsonNode> response = client.get(format("%s/%s/%d", baseUrl, PATH, OTHER_ID));

        StepVerifier.create(response)
                .expectError(NotFoundException.class)
                .verify();
    }

}