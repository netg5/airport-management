package org.sergei.gateway;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author Sergei Visotsky
 */
@Configuration
@EnableWebSecurity
public class GatewaySecurityConfiguration extends WebSecurityConfigurerAdapter {

    /**
     * Permits to all
     *
     * @param http request
     * @throws Exception any kind of exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/**")
                .permitAll();
    }

    /**
     * Allows to pass all request to the path
     *
     * @param web security
     */
    @Override
    public void configure(WebSecurity web) {
        web
                .ignoring()
                .antMatchers("/**");
    }
}
