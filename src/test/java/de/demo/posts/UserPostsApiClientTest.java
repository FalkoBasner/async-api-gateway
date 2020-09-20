package de.demo.posts;

import de.demo.platform.api.JsonApiClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.client.MockServerClient;
import org.mockserver.junit.jupiter.MockServerExtension;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@ExtendWith(MockServerExtension.class)
public class UserPostsApiClientTest {
    public static final String URL_TEMPLATE = "http://localhost:%s";

    public static final String USER_ID_KEY = "userId";
    public static final Long USER_ID_VALUE = 111L;
    public static final String USER_JSON_RESPONSE_TEMPLATE = "[{\"userId\":%s}]";

    private UserPostsApiProperties properties = new UserPostsApiProperties();
    public UserPostsApiClient client;

    @BeforeEach
    public void setup(MockServerClient mockServerClient) {
        properties.setUrl(format(URL_TEMPLATE, mockServerClient.getPort()));
        client = new UserPostsApiClient(properties, new JsonApiClient());
        mockServerClient.reset();
    }

    @Test
    public void getUserPosts(MockServerClient mockServerClient) throws ExecutionException, InterruptedException {
        mockServerClient.when(request()
                .withQueryStringParameter(USER_ID_KEY, USER_ID_VALUE.toString())
                .withMethod("GET"))
                .respond(response()
                        .withBody(format(USER_JSON_RESPONSE_TEMPLATE, USER_ID_VALUE))
                        .withStatusCode(200));

        final CompletableFuture<JSONArray> postsFuture = client.getUserPosts(USER_ID_VALUE);

        final JSONArray postsJson = postsFuture.get();
        final List<JSONObject> posts = new LinkedList<>();
        IntStream.range(0, postsJson.size()).forEach(ii -> posts.add((JSONObject) postsJson.get(ii)));

        posts.stream().forEach(p -> assertThat(p.get("userId")).isEqualTo(USER_ID_VALUE));
    }
}