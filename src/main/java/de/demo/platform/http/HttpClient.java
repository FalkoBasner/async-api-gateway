package de.demo.platform.http;

import lombok.extern.slf4j.Slf4j;
import org.asynchttpclient.DefaultAsyncHttpClientConfig;
import org.asynchttpclient.Response;
import org.springframework.util.StopWatch;

import java.util.concurrent.CompletableFuture;

import static org.asynchttpclient.Dsl.asyncHttpClient;

@Slf4j
public class HttpClient {
    private final org.asynchttpclient.AsyncHttpClient client;

    public HttpClient() {
        this.client = asyncHttpClient(new DefaultAsyncHttpClientConfig.Builder().build());
    }

    public CompletableFuture<Response> get(final String url) {
        final StopWatch stopWatch = new StopWatch(url);
        stopWatch.start();

        return client
                .prepareGet(url)
                .execute()
                .toCompletableFuture()
                .thenApply(response -> {
                    stopWatch.stop();
                    log.info("{}, {} seconds", stopWatch.getId(), stopWatch.getTotalTimeSeconds());
                    return response;
                })
                .handle((response, error) -> {
                    if (error != null) {
                        throw new HttpClientException(error);
                    }
                    return response;
                });
    }
}
