package org.sergei.mainui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MainUiApplication {

    public static void main(String[] args) {

        if (System.getProperty("spring.profiles.active") == null) {
            System.setProperty("spring.profiles.active", "prod");
        }

        SpringApplication.run(MainUiApplication.class, args);
    }

}

