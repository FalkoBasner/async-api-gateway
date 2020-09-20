package de.demo.posts;

import de.demo.platform.api.JsonApiClient;
import lombok.AllArgsConstructor;
import org.json.simple.JSONArray;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
@Component
public class UserPostsApiClient {
    private final UserPostsApiProperties properties;
    private final JsonApiClient jsonApiClient;

    @Async
    public CompletableFuture<JSONArray> getUserPosts(final long userId) {
        return CompletableFuture.completedFuture(
                jsonApiClient.getJsonArray(properties.getUrl() + "/?userId=" + userId));
    }
}
