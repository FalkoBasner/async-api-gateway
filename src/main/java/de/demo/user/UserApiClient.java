package de.demo.user;

import de.demo.platform.api.JsonApiClient;
import lombok.AllArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
@Component
public class UserApiClient {
    private final UserApiProperties properties;
    private final JsonApiClient jsonApiClient;

    @Async
    public CompletableFuture<JSONObject> getUser(final long userId) {
        return CompletableFuture.completedFuture(
                jsonApiClient.getJsonObject(properties.getUrl() + "/" + userId));
    }
}
