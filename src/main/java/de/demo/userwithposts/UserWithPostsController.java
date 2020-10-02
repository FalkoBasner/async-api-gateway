package de.demo.userwithposts;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Slf4j
@RestController
public class UserWithPostsController {
    private final UserWithPostsService userWithPostsService;

    @GetMapping(value = "/userwithposts/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<JsonNode> getUserWithPosts(@PathVariable("userId") final Long userId) {
        return userWithPostsService.getUserWithPosts(userId);
    }

}
