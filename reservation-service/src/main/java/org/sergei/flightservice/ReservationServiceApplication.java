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
public class ReservationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReservationServiceApplication.class, args);
    }

    @RestController
    class WelcomeController {
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
