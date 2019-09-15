package org.sergei.timetable.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author Sergei Visotsky
 */
@Configuration
@Profile({"!plain", "!test"})
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/docs", "/v2/api-docs", "/configuration/**", "/swagger-resources/**", "/webjars/**");
    }
}
