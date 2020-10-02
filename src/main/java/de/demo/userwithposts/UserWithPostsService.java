package de.demo.userwithposts;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.demo.typicode.TypicodeApiClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Slf4j
@Component
public class UserWithPostsService {
    private ObjectMapper mapper;

    private TypicodeApiClient typicodeApiClient;

    public Mono<JsonNode> getUserWithPosts(final long userId) {
        return typicodeApiClient.getUser(userId)
                .zipWith(typicodeApiClient.getUserPosts(userId), this::combine);
    }

    JsonNode combine(final JsonNode user, final JsonNode posts) {
        final ObjectNode combined = mapper.createObjectNode();
        combined.set("user", user);
        combined.set("posts", posts);
        return combined;
    }
}
