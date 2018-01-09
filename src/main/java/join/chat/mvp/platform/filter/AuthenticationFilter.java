package join.chat.mvp.platform.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import join.chat.mvp.platform.AuthenticationSuccessHandler;
import join.chat.mvp.platform.essential.LoginEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Collections.emptyList;

public class AuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private final AuthenticationManager authenticationManager;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;

    public AuthenticationFilter(final String defaultProcessUrl, AuthenticationManager authenticationManager,
                                AuthenticationSuccessHandler authenticationSuccessHandler) {
        super(defaultProcessUrl);

        this.authenticationManager = authenticationManager;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            final LoginEntity credential = new ObjectMapper()
                    .readValue(req.getInputStream(), LoginEntity.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credential.getUsername(),
                            credential.getPassword(),
                            emptyList()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication auth) throws IOException, ServletException {
        authenticationSuccessHandler.onAuthenticationSuccess(request, response, auth);
    }
}
