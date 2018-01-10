package join.chat.mvp.platform.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "join.chat.configuration.validation")
public class ValidationConfiguration {
    /**
     * Verification Token timeout.
     */
    private Long authTokenTimeout ;

    public Long getAuthTokenTimeout() {
        return authTokenTimeout;
    }

    public void setAuthTokenTimeout(Long authTokenTimeout) {
        this.authTokenTimeout = authTokenTimeout;
    }
}
