package de.demo.user;

import de.demo.platform.http.error.NotFoundException;
import lombok.SneakyThrows;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class UserApiClientIT {
    public static final long USER_ID = 1L;
    public static final long UNKNOWN_USER_ID = 4711L;

    @Autowired
    public UserApiClient apiClient;

    @Test
    @SneakyThrows
    public void getUserJson() {
        final Future<JSONObject> jsonFuture = apiClient.getUser(USER_ID);
        assertThat(jsonFuture.isDone()).isFalse();

        final JSONObject userJson = jsonFuture.get();
        assertThat(userJson.containsKey("id")).isTrue();
        assertThat(userJson.get("id")).isEqualTo(USER_ID);
    }

    @Test
    @SneakyThrows
    public void getUserJson_With_Unknown_Id() {
        final CompletableFuture<JSONObject> jsonFuture = apiClient.getUser(UNKNOWN_USER_ID);
        assertThat(jsonFuture.isDone()).isFalse();

        Throwable exception = assertThrows(ExecutionException.class, () -> jsonFuture.get());
        assertThat(exception.getCause()).isInstanceOf(NotFoundException.class);
    }
}