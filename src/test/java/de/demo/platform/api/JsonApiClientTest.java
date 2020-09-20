package de.demo.platform.api;

import de.demo.platform.http.RestTemplateHttpClient;
import lombok.SneakyThrows;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class JsonApiClientTest {
    public static final long USER_ID = 1L;
    public static final String URL = "http://jsonplaceholder.typicode.com/users/" + USER_ID;

    private JsonApiClient apiClient = new JsonApiClient(new RestTemplateHttpClient());

    @Test
    @SneakyThrows
    public void getJsonObject() {
        final JSONObject json = apiClient.getJsonObject(URL);

        assertThat(json.containsKey("id")).isEqualTo(true);
        assertThat(json.get("id")).isEqualTo(USER_ID);
    }
}