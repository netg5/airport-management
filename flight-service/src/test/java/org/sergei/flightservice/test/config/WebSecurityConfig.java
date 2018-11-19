package org.sergei.flightservice.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author Sergei Visotsky, 2018
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public ObjectPostProcessor objectPostProcessor() {
        return new ObjectPostProcessor() {
            @Override
            public Object postProcess(Object o) {
                return null;
            }
        };
    }
}
