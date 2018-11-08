package org.sergei.flightreservation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
@EnableDiscoveryClient
public class FlightServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlightServiceApplication.class, args);
    }

    @Controller
    class WelcomeEntry {
        @GetMapping("/")
        @ResponseBody
        public String welcome() {
            return "Flights";
        }

        @GetMapping("/docs")
        public String docsRedirect() {
            return "redirect:swagger-ui.html";
        }
    }
}
