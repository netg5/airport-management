package org.sergei.processor.config;

import org.springframework.boot.task.TaskSchedulerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;

/**
 * @author Sergei Visotsky
 */
@Configuration
public class TaskSchedulingConfig {

    @Bean
    public TaskScheduler taskScheduler(TaskSchedulerBuilder schedulerBuilder) {
        return schedulerBuilder.build();
    }

}
