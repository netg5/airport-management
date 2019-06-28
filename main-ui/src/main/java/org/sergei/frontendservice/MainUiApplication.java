package org.sergei.frontendservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MainUiApplication {

    public static void main(String[] args) {

        if (System.getProperty("-Dspring.profiles.active") == null) {
            System.setProperty("-Dspring.profiles.active", "prod");
        }

        SpringApplication.run(MainUiApplication.class, args);
    }

}

