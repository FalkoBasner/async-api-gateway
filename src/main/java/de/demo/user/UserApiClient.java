package de.demo.user;

import de.demo.platform.api.JsonApiClient;
import lombok.AllArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
@Component
public class UserApiClient extends JsonApiClient {
    private final UserApiProperties properties;
    private final JsonApiClient jsonApiClient;

    public CompletableFuture<JSONObject> getUser(final long userId) {
        return jsonApiClient.getJsonObject(properties.getUrl() + "/" + userId);
    }
}
