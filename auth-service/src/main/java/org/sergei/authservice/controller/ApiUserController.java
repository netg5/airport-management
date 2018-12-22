package org.sergei.authservice.controller;

import org.sergei.authservice.model.User;
import org.sergei.authservice.model.UserRoles;
import org.sergei.authservice.service.ApiUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

/**
 * @author Sergei Visotsky
 */
@RestController
@RequestMapping(value = "/users", produces = "application/json")
public class ApiUserController {

    @Autowired
    private ApiUserService apiUserService;

    @GetMapping("/authenticate")
    public ResponseEntity<Principal> authenticateUser(Principal user) {
        return ResponseEntity.ok(user);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(apiUserService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<User> getUserById(@PathVariable("userId") Long userId) {
        return new ResponseEntity<>(apiUserService.findById(userId), HttpStatus.OK);
    }

    @GetMapping(params = "username")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<User> getUserByUsername(@RequestParam("username") String username) {
        return new ResponseEntity<>(apiUserService.findByUsername(username), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        user.setUserRoles(Collections.singletonList(new UserRoles("USER")));
        User newUser = apiUserService.saveUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
}
