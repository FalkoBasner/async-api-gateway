package de.demo.platform.http;

import de.demo.platform.http.error.HttpClientException;
import de.demo.platform.http.error.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StopWatch;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Component
public class RestTemplateHttpClient {
    private final RestTemplate restTemplate = new RestTemplate();
    private final MultiValueMap<String, String> headers;

    @Autowired
    public RestTemplateHttpClient() {
        this.headers = new LinkedMultiValueMap();
        headers.add(ACCEPT, APPLICATION_JSON_VALUE);
    }

    public ResponseEntity<String> get(final String url) {
        final StopWatch stopWatch = new StopWatch(url);
        stopWatch.start();

        try {
            return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class);
        } catch (Exception e) {
            if (e instanceof HttpClientErrorException) {
                var error = (HttpClientErrorException) e;

                if (error.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                    throw new NotFoundException(e);
                }
            }

            log.error("error while requesting api", e);
            throw new HttpClientException(e);
        } finally {
            stopWatch.stop();
            log.info("{}, {} seconds", stopWatch.getId(), stopWatch.getTotalTimeSeconds());
        }
    }
}
