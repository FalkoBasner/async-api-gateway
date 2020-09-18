package de.demo.userwithposts;

import de.demo.posts.UserPostsApiClient;
import de.demo.user.UserApiClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
@Slf4j
@Component
public class UserWithPostsService {
    private final UserApiClient userApiClient;
    private final UserPostsApiClient userPostsApiClient;

    static JSONObject combine(final JSONObject user, final JSONArray posts) {
        final JSONObject combined = new JSONObject();
        combined.put("user", user);
        combined.put("posts", posts);
        return combined;
    }

    public CompletableFuture<JSONObject> getUserWithPosts(final long userId) {
        return userApiClient.getUser(userId)
                .thenCombine(userPostsApiClient.getUserPosts(userId), UserWithPostsService::combine);
    }
}
