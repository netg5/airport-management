package org.sergei.flightreservation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
@ImportResource("classpath:WEB-INF/applicationContext.xml")
public class FlightReservationApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlightReservationApplication.class, args);
    }

    @Controller
    class WelcomeEntry {
        @GetMapping
        @ResponseBody
        public String welcome() {
            return "Flight reservation app";
        }
    }
}
