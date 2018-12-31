package org.sergei.authservice.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sergei Visotsky
 */
@RestController
public class EncryptionEndpoint {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @ApiOperation("Service method to encrypt client_secret or password")
    @GetMapping(value = "/encryption")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String getEncryptedPassword(@ApiParam(value = "Password to be encrypted", required = true)
                                       @RequestParam("password") String password) {
        return passwordEncoder.encode(password);
    }
}
