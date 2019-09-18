package org.sergei.processor.config;

import io.opentracing.contrib.spring.web.starter.WebTracingProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Sergei Visotsky
 */
@Configuration
public class TraceConfig {

    @Bean
    public WebTracingProperties webTracingProperties() {
        return new WebTracingProperties();
    }

}
