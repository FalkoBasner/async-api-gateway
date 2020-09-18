package de.demo.user;

import de.demo.platform.api.JsonApiClient;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.client.MockServerClient;
import org.mockserver.junit.jupiter.MockServerExtension;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.lang.String.format;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@ExtendWith(MockServerExtension.class)
public class UserApiClientTest {
    public static final String URL_TEMPLATE = "http://localhost:%s";
    public static final Long USER_ID = 111L;
    public static final String JSON_OBJECT_TEMPLATE = "{\"id\":%s}";

    public UserApiProperties properties = new UserApiProperties();
    public UserApiClient client;

    @BeforeEach
    public void setup(MockServerClient mockServerClient) {
        properties.setUrl(format(URL_TEMPLATE, mockServerClient.getPort()));
        client = new UserApiClient(properties, new JsonApiClient());
        mockServerClient.reset();
    }

    @Test
    public void getUser(MockServerClient mockServerClient) throws ExecutionException, InterruptedException {
        mockServerClient
                .when(request().withPath("/" + USER_ID.toString()).withMethod("GET"))
                .respond(response()
                        .withBody(format(JSON_OBJECT_TEMPLATE, USER_ID))
                        .withStatusCode(200));

        final CompletableFuture<JSONObject> userFuture = client.getUser(USER_ID);

        final JSONObject user = userFuture.get();
        assertThat(user.containsKey("id"), is(true));
        assertThat(user.get("id"), is(USER_ID));
    }
}