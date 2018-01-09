package join.chat.mvp.platform.component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import join.chat.mvp.platform.configuration.JWTConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JWTTokenFactory {
    private static final long JWT_ONE_MINUTE = 60000;

    private final JWTConfiguration jwtConfiguration;

    @Autowired
    public JWTTokenFactory(JWTConfiguration jwtConfiguration) {
        this.jwtConfiguration = jwtConfiguration;
    }

    String getToken(final User user) {
        final Claims claims = Jwts.claims().setSubject(user.getUsername());
        final long issuedAt = System.currentTimeMillis();
        final long expiration = issuedAt + jwtConfiguration.getTokenExpirationTime() * JWT_ONE_MINUTE;

        if (StringUtils.isEmpty(user.getUsername()))
            throw new IllegalArgumentException("Can't create JWT Token without username");

        if (user.getAuthorities() != null && !user.getAuthorities().isEmpty()) {
            claims.put(JWTConfiguration.JWT_AUTHORITIES, user.getAuthorities()
                    .stream().map(Object::toString).collect(Collectors.toList()));
        }


        return Jwts.builder()
                .setClaims(claims)
                .setIssuer(jwtConfiguration.getTokenIssuer())
                .setIssuedAt(new Date(issuedAt))
                .setExpiration(new Date(expiration))
                .signWith(SignatureAlgorithm.HS512,
                        jwtConfiguration.getTokenSigningKey())
                .compact();
    }

    String getRefreshToken(final User user) {
        final Claims claims = Jwts.claims().setSubject(user.getUsername());
        final long issuedAt = System.currentTimeMillis();
        final long expiration = issuedAt + jwtConfiguration.getRefreshTokenExpTime() * JWT_ONE_MINUTE;

        if (StringUtils.isEmpty(user.getUsername()))
            throw new IllegalArgumentException("Cannot create JWT Token without username");

        if (user.getAuthorities() != null && !user.getAuthorities().isEmpty()) {
            claims.put(JWTConfiguration.JWT_AUTHORITIES, user.getAuthorities()
                    .stream().map(Object::toString).collect(Collectors.toList()));
        }

        return Jwts.builder()
                .setClaims(claims)
                .setId(UUID.randomUUID().toString())
                .setIssuer(jwtConfiguration.getTokenIssuer())
                .setIssuedAt(new Date(issuedAt))
                .setExpiration(new Date(expiration))
                .signWith(SignatureAlgorithm.HS512,
                        jwtConfiguration.getTokenSigningKey())
                .compact();
    }
}
