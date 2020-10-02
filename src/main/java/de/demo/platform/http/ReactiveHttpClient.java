package de.demo.platform.http;

import com.fasterxml.jackson.databind.JsonNode;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import static io.netty.channel.ChannelOption.CONNECT_TIMEOUT_MILLIS;

public class ReactiveHttpClient {
    private static final int TIMEOUT_IN_SECONDS = 2;

    TcpClient tcpClient = TcpClient.create()
            .option(CONNECT_TIMEOUT_MILLIS, TIMEOUT_IN_SECONDS * 1000)
            .doOnConnected(connection ->
                    connection
                            .addHandlerLast(new ReadTimeoutHandler(TIMEOUT_IN_SECONDS))
                            .addHandlerLast(new WriteTimeoutHandler(TIMEOUT_IN_SECONDS)));

    WebClient webClient = WebClient.builder()
            .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
            .build();

    public Mono<JsonNode> get(final String url) {
        return webClient
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(JsonNode.class);
    }
}
