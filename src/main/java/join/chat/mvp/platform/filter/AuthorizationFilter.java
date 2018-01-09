package join.chat.mvp.platform.filter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import join.chat.mvp.platform.configuration.JWTConfiguration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class AuthorizationFilter extends BasicAuthenticationFilter {
    private final JWTConfiguration jwtConfiguration;

    public AuthorizationFilter(AuthenticationManager authManager, JWTConfiguration jwtConfiguration) {
        super(authManager);
        this.jwtConfiguration = jwtConfiguration;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        final String header = request.getHeader(JWTConfiguration.JWT_HEADER_STRING);

        if (header != null && header.startsWith(JWTConfiguration.JWT_TOKEN_PREFIX)) {
            try {
                SecurityContextHolder.getContext().setAuthentication(getAuthentication(request));
            } catch (ExpiredJwtException ignored) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token has expired");
                return;
            }
        }

        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        final String token = request.getHeader(JWTConfiguration.JWT_HEADER_STRING);
        if (token != null && !StringUtils.isEmpty(token)) {
            // parse the token.
            final String user = Jwts.parser()
                    .setSigningKey(jwtConfiguration.getTokenSigningKey())
                    .parseClaimsJws(token.replace(JWTConfiguration.JWT_TOKEN_PREFIX + " ", ""))
                    .getBody().getSubject();

            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            }
        }
        return null;
    }
}
