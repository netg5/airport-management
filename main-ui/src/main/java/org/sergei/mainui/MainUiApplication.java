package org.sergei.mainui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MainUiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainUiApplication.class, args);
    }

}

