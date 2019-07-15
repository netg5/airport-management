package org.sergei.reports.config;

import org.sergei.reports.aop.LoggingAspect;
import org.sergei.reports.aop.PerformanceAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author Sergei Visotsky
 */
@Configuration
@EnableAspectJAutoProxy
public class AspectConfig {

    @Bean
    public PerformanceAspect performanceAspect() {
        return new PerformanceAspect();
    }

    @Bean
    public LoggingAspect loggingAspect() {
        return new LoggingAspect();
    }

}
