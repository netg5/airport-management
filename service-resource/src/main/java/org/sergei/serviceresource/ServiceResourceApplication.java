package org.sergei.serviceresource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class ServiceResourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceResourceApplication.class, args);
    }

    @RestController
    class WelcomeEntry {
        @GetMapping("/")
        public String welcome() {
            return "Service resource";
        }
    }
}
