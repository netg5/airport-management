package org.sergei.flightservice.test.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * @author Sergei Visotsky
 * @since 12/5/2018
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable().authorizeRequests().anyRequest().permitAll();
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/v1/**").permitAll();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer security) {
        security.stateless(false);
    }
}
