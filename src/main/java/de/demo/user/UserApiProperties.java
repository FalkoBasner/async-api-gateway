package de.demo.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@ConfigurationProperties(prefix = "user.api")
@Getter
@Setter
public class UserApiProperties {
    private static final String HTTP_URL = "https?\\://.+";

    @NotNull
    @Pattern(regexp = HTTP_URL)
    private String url;
}
