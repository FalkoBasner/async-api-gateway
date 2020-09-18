package de.demo.posts;

import de.demo.platform.api.JsonApiClient;
import lombok.AllArgsConstructor;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
@Component
public class UserPostsApiClient {
    private final UserPostsApiProperties properties;
    private final JsonApiClient jsonApiClient;

    public CompletableFuture<JSONArray> getUserPosts(final long userId) {
        return jsonApiClient.getJsonArray(properties.getUrl() + "/?userId=" + userId);
    }
}
