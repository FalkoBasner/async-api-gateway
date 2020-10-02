package de.demo.typicode;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@ConfigurationProperties(prefix = "typicode.api")
@Getter
@Setter
public class TypicodeApiProperties {
    private static final String HTTP_URL = "https?\\://.+";

    @Getter
    @Setter
    public static class Users {
        @NotNull
        @Pattern(regexp = HTTP_URL)
        private String url;
    }

    @Getter
    @Setter
    public static class Posts {
        @NotNull
        @Pattern(regexp = HTTP_URL)
        private String url;
    }

    @NotNull
    private Users users;

    @NotNull
    private Posts posts;
}
