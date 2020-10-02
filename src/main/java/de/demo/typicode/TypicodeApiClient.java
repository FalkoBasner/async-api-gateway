package de.demo.typicode;

import com.fasterxml.jackson.databind.JsonNode;
import de.demo.platform.http.ReactiveHttpClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Component
public class TypicodeApiClient {
    private final ReactiveHttpClient client = new ReactiveHttpClient();

    private final TypicodeApiProperties properties;

    public Mono<JsonNode> getUser(final Long userId) {
        return client.get(properties.getUsers().getUrl() + "?id=" + userId);
    }

    public Mono<JsonNode> getUserPosts(final Long userId) {
        return client.get(properties.getPosts().getUrl() + "?userId=" + userId);
    }
}
