package backend.config.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private final Auth auth = new Auth();
    private final OAuth2 oauth2 = new OAuth2();

    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Auth {

        private String tokenSecret;
        private long tokenExpiry;
        private long refreshTokenExpiry;

    }

    @Getter @Setter
    public static final class OAuth2 {

        private List<String> authorizedRedirectUris = new ArrayList<>();

    }

}