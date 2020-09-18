package de.demo.platform.api;

import de.demo.platform.http.HttpClient;
import lombok.SneakyThrows;
import org.asynchttpclient.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Component;

import java.io.InputStreamReader;
import java.util.concurrent.CompletableFuture;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component
public class JsonApiClient {
    private final HttpClient client = new HttpClient();

    public CompletableFuture<JSONObject> getJsonObject(final String url) {
        final CompletableFuture<Response> response = client.get(url);
        return response.thenApply(this::toJsonObject);
    }

    public CompletableFuture<JSONArray> getJsonArray(final String url) {
        final CompletableFuture<Response> response = client.get(url);
        return response.thenApply(this::toJsonArray);
    }

    @SneakyThrows
    JSONObject toJsonObject(final Response response) {
        return (JSONObject) new JSONParser().parse(
                new InputStreamReader(response.getResponseBodyAsStream(), UTF_8));
    }

    @SneakyThrows
    JSONArray toJsonArray(final Response response) {
        return (JSONArray) new JSONParser().parse(
                new InputStreamReader(response.getResponseBodyAsStream(), UTF_8));
    }

}
