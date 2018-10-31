/*
 * Copyright (c) 2018 Sergei Visotsky
 */

package org.sergei.flightreservation.config;

import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Sergei Visotsky, 2018
 */
@Configuration
@EnableAutoConfiguration
public class ApplicationContext {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
