package join.chat.mvp.platform;

import join.chat.mvp.platform.component.JWTAuthenticationSuccessHandler;
import join.chat.mvp.platform.configuration.JWTConfiguration;
import join.chat.mvp.platform.configuration.PasswordConfiguration;
import join.chat.mvp.platform.filter.AuthenticationFilter;
import join.chat.mvp.platform.filter.AuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
public class WebSecurityAdapter extends WebSecurityConfigurerAdapter {
    private static final String H2 = "/h2/**";
    private static final String AUTHORIZATION = "/v1/authorization/**";

    private final JWTConfiguration jwtConfiguration;
    private final PasswordConfiguration passwordConfiguration;

    private final UserDetailsService userDetailsService;
    private final JWTAuthenticationSuccessHandler jwtAuthenticationSuccessHandler;

    @Autowired
    public WebSecurityAdapter(UserDetailsService userDetailsService,
                              PasswordConfiguration passwordConfiguration,
                              JWTConfiguration jwtConfiguration, JWTAuthenticationSuccessHandler jwtAuthenticationSuccessHandler) {
        this.userDetailsService = userDetailsService;

        this.jwtConfiguration = jwtConfiguration;
        this.passwordConfiguration = passwordConfiguration;
        this.jwtAuthenticationSuccessHandler = jwtAuthenticationSuccessHandler;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity // http://docs.spring.io/spring-security/site/docs/4.1.x/reference/htmlsingle/#cors
                .cors(); // by default uses a Bean by the name of corsConfigurationSource

        httpSecurity
                .csrf().disable() // We don't need CSRF for JWT based authentication
                .headers().frameOptions().disable(); // H2 Console Dash-board - only for testing

        httpSecurity.authorizeRequests()
                .antMatchers(H2).permitAll() // H2 Console Dash-board - only for testing
                .antMatchers(AUTHORIZATION).permitAll()
                .anyRequest().authenticated();

        httpSecurity
                .addFilterBefore(new AuthorizationFilter(authenticationManager(), jwtConfiguration),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new AuthenticationFilter(jwtConfiguration.getDefaultProcessUrl(),
                                authenticationManager(), jwtAuthenticationSuccessHandler),
                        UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordConfiguration.passwordEncoder());
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration.applyPermitDefaultValues());
        return source;
    }
}
