package join.chat.mvp.platform.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import join.chat.mvp.platform.AuthenticationSuccessHandler;
import join.chat.mvp.platform.configuration.JWTConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final ObjectMapper objectMapper;
    private final JWTTokenFactory jwtTokenFactory;

    @Autowired
    public JWTAuthenticationSuccessHandler(ObjectMapper objectMapper, JWTTokenFactory jwtTokenFactory) {
        this.objectMapper = objectMapper;
        this.jwtTokenFactory = jwtTokenFactory;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        final Map<String, String> tokenMap = new HashMap<>();
        final User user = (User) authentication.getPrincipal();

        tokenMap.put(JWTConfiguration.JWT_TOKEN, jwtTokenFactory.getToken(user));
        tokenMap.put(JWTConfiguration.JWT_REFRESH_TOKEN, jwtTokenFactory.getRefreshToken(user));

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getWriter(), tokenMap);

        clearAuthenticationAttributes(request);
    }

    /**
     * Removes temporary authentication-related data which may have been stored
     * in the session during the authentication process..
     */
    private void clearAuthenticationAttributes(final HttpServletRequest request) {
        final HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION) != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }
}
