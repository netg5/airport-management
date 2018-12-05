package org.sergei.flightservice.test.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author Sergei Visotsky, 2018
 *
 * <pre>
 *     Security config for test cases
 * </pre>
 */
@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Order(99)
public class WebSecurityConfigTest extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable().authorizeRequests().anyRequest().permitAll();
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/v1/**").permitAll();
    }
}
