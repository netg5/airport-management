package org.sergei.ticketservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
@EnableEurekaClient
public class TicketServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketServiceApplication.class, args);
    }

    @Controller
    class WelcomeEntry {
        @GetMapping("/")
        @ResponseBody
        public String welcome() {
            return "Tickets";
        }

        @GetMapping("/docs")
        public String docsRedirect() {
            return "redirect:swagger-ui.html";
        }
    }
}
