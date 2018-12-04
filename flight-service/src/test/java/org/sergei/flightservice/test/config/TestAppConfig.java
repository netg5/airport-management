package org.sergei.flightservice.test.config;

import org.sergei.flightservice.controller.CustomerController;
import org.sergei.flightservice.repository.CustomerRepository;
import org.sergei.flightservice.service.CustomerService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Sergei Visotsky
 * @since 12/4/2018
 */
@Configuration
public class TestAppConfig {
    private AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();

    @Bean
    public void registerCustomerService() {
        ctx.register(CustomerController.class);
        ctx.register(CustomerService.class);
        ctx.register(CustomerRepository.class);
    }
}
