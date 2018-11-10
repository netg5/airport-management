/*
 * Copyright (c) 2018 Sergei Visotsky
 */

package org.sergei.flightreservation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Random;

/**
 * @author Sergei Visotsky, 2018
 * <p>
 * Endpoint to see encrypted password
 */
@ApiIgnore
@RestController
public class ServiceEndpoints {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(value = "/encryption", produces = "text/plain")
    public String getEncryptedPassword(@RequestParam("password") String password) {
        return passwordEncoder.encode(password);
    }

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
