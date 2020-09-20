package de.demo.userwithposts;

import com.google.common.collect.ImmutableMap;
import de.demo.posts.UserPostsApiClient;
import de.demo.user.UserApiClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserWithPostsServiceTest {
    private static final long USER_ID = 4711L;

    @Mock
    private UserApiClient userApiClient;
    @Mock
    private UserPostsApiClient userPostsApiClient;
    @InjectMocks
    private UserWithPostsService service;

    @Test
    public void getUserWithPosts() throws ExecutionException, InterruptedException {

        final CompletableFuture<JSONObject> userFuture = new CompletableFuture<>();
        when(userApiClient.getUser(USER_ID)).thenReturn(userFuture);

        final CompletableFuture<JSONArray> userPostsFuture = new CompletableFuture<>();
        when(userPostsApiClient.getUserPosts(USER_ID)).thenReturn(userPostsFuture);

        final CompletableFuture<JSONObject> combinedFuture = service.getUserWithPosts(USER_ID);

        userFuture.complete(expectedUserJson());
        userPostsFuture.complete(expectedPostsJson());

        final JSONObject json = combinedFuture.get();

        assertThat((Set<String>) json.keySet()).containsExactlyInAnyOrder("user", "posts");
        assertThat(json.get("user")).isEqualTo((expectedUserJson()));
        assertThat(json.get("posts")).isEqualTo((expectedPostsJson()));
    }

    private JSONObject expectedUserJson() {
        return new JSONObject(ImmutableMap.of("user_key", "user_value"));
    }

    private JSONArray expectedPostsJson() {
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(new JSONObject(ImmutableMap.of("post_key", "post_value")));
        return jsonArray;
    }
}