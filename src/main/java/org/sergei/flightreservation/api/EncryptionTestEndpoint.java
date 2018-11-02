/*
 * Copyright (c) 2018 Sergei Visotsky
 */

package org.sergei.flightreservation.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sergei Visotsky, 2018
 */
@RestController
public class EncryptionTestEndpoint {

    /*@Autowired
    private BCryptPasswordEncoder passwordEncoder;*/

    @GetMapping(value = "/encryption", produces = "text/plain")
    public String getEncryptedPassword(@RequestParam("password") String password) {
//        return passwordEncoder.encode(password);
        return null;
    }
}
