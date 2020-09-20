package de.demo.posts;

import lombok.SneakyThrows;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
public class UserPostsApiClientIT {
    public static final long USER_ID = 1L;
    public static final long UNKNOWN_USER_ID = 4711L;
    public static final String JSON_KEY_USER_ID = "userId";

    @Autowired
    public UserPostsApiClient apiClient;

    @Test
    @SneakyThrows
    public void getUserPostsJson() {
        final Future<JSONArray> jsonFuture = apiClient.getUserPosts(USER_ID);
        assertThat(jsonFuture.isDone(), is(false));

        final List<JSONObject> posts = new LinkedList<>();

        final JSONArray postsJson = jsonFuture.get();

        IntStream.range(0, postsJson.size()).forEach(ii -> posts.add((JSONObject) postsJson.get(ii)));

        posts.forEach(p -> assertThat(p.get(JSON_KEY_USER_ID), is(USER_ID)));
    }

    @Test
    @SneakyThrows
    public void getUserPostsJson_With_Unknown_Id() {
        final CompletableFuture<JSONArray> jsonFuture = apiClient.getUserPosts(UNKNOWN_USER_ID);
        assertThat(jsonFuture.isDone(), is(false));

        assertThat(jsonFuture.get().isEmpty(), is(true));
    }
}