/*
 * Copyright (c) 2018 Sergei Visotsky
 */

package org.sergei.flightreservation.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sergei Visotsky, 2018
 */
@RestController
public class EncryptionTestEndpoint {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping(value = "/encryption", produces = "text/plain")
    public String getEncryptedPassword(@RequestParam("password") String password) {
        return passwordEncoder.encode(password);
    }
}
