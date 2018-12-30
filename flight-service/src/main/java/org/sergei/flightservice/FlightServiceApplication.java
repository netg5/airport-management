package org.sergei.flightservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SpringBootApplication
@EnableEurekaClient
public class FlightServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlightServiceApplication.class, args);
    }

    @RestController
    class WelcomeEntry {
        @GetMapping("/")
        public String welcome() {
            return "Flights";
        }

        @GetMapping("/docs")
        public void docsRedirect(HttpServletResponse response) throws IOException {
            response.sendRedirect("swagger-ui.html");
        }
    }
}
