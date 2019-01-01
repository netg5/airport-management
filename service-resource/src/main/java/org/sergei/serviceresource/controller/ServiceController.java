package org.sergei.serviceresource.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * REST controller with service endpoints
 *
 * @author Sergei Visotsky
 */
@RestController
public class ServiceController {

    @GetMapping(value = "/greeting", produces = "application/json")
    public ResponseEntity<DemoDTO> demoGreeting() {
        return new ResponseEntity<>(new DemoDTO(), HttpStatus.OK);
    }

    class DemoDTO {
        Long id = new Random().nextLong() % (1000L - 100L) + 1000L;
        String content = "Hello World!";

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
