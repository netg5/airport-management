package org.sergei.booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BookingApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookingApplication.class, args);
    }

}
