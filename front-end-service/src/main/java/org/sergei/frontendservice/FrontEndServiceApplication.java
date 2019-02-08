package org.sergei.frontendservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class FrontEndServiceApplication {

    public static void main(String[] args) {

        if (System.getProperty("-Dspring.profiles.active") == null) {
            System.setProperty("-Dspring.profiles.active", "dev");
        }

        SpringApplication.run(FrontEndServiceApplication.class, args);
    }

}

