package de.demo.platform.api;

import de.demo.platform.http.RestTemplateHttpClient;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class JsonApiClient {
    private final RestTemplateHttpClient client;

    public JSONObject getJsonObject(final String url) {
        final ResponseEntity<String> response = client.get(url);
        return toJsonObject(response);
    }

    public JSONArray getJsonArray(final String url) {
        final ResponseEntity<String> response = client.get(url);
        return toJsonArray(response);
    }

    @SneakyThrows
    JSONObject toJsonObject(final ResponseEntity<String> response) {
        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(response.getBody());
    }

    @SneakyThrows
    JSONArray toJsonArray(final ResponseEntity<String> response) {
        JSONParser parser = new JSONParser();
        return (JSONArray) parser.parse(response.getBody());
    }
}
