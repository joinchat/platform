package join.chat.mvp.platform.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "join.chat.configuration.jwt")
public class JWTConfiguration {
    public static final String JWT_TOKEN = "token";
    public static final String JWT_REFRESH_TOKEN = "refreshToken";
    public static final String JWT_AUTHORITIES = "authorities";
    public static final String JWT_TOKEN_PREFIX = "Bearer";
    public static final String JWT_HEADER_STRING = "Authorization";
    /**
     * Token issuer.
     */
    private String tokenIssuer;
    /**
     * Key is used to sign.
     */
    private String tokenSigningKey;
    /**
     * Key is used to sign.
     */
    private String defaultProcessUrl;
    /**
     * Will expire after this time.
     */
    private Integer tokenExpirationTime;
    /**
     * Can be refreshed during this time frame.
     */
    private Integer refreshTokenExpTime;

    public String getTokenIssuer() {
        return tokenIssuer;
    }

    public void setTokenIssuer(String tokenIssuer) {
        this.tokenIssuer = tokenIssuer;
    }

    public String getTokenSigningKey() {
        return tokenSigningKey;
    }

    public void setTokenSigningKey(String tokenSigningKey) {
        this.tokenSigningKey = tokenSigningKey;
    }

    public String getDefaultProcessUrl() {
        return defaultProcessUrl;
    }

    public void setDefaultProcessUrl(String defaultProcessUrl) {
        this.defaultProcessUrl = defaultProcessUrl;
    }

    public Integer getTokenExpirationTime() {
        return tokenExpirationTime;
    }

    public void setTokenExpirationTime(Integer tokenExpirationTime) {
        this.tokenExpirationTime = tokenExpirationTime;
    }

    public Integer getRefreshTokenExpTime() {
        return refreshTokenExpTime;
    }

    public void setRefreshTokenExpTime(Integer refreshTokenExpTime) {
        this.refreshTokenExpTime = refreshTokenExpTime;
    }
}
