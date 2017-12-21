package join.chat.mvp.platform.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class WebSecurityAdapter extends WebSecurityConfigurerAdapter {
    private static final String H2 = "/h2/**";
    private static final String AUTHORIZATION = "/v1/authorization/**";


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

    }
}
