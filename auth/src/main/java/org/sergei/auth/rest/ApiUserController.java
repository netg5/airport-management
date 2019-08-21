package org.sergei.auth.rest;

import org.sergei.auth.jpa.model.AuthUser;
import org.sergei.auth.jpa.model.AuthUserRoles;
import org.sergei.auth.service.ApiUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

/**
 * @author Sergei Visotsky
 */
@RestController
@RequestMapping(value = "/users")
public class ApiUserController {

    private final ApiUserService apiUserService;

    @Autowired
    public ApiUserController(ApiUserService apiUserService) {
        this.apiUserService = apiUserService;
    }

    @GetMapping("/authenticate")
    public ResponseEntity<Principal> authenticateUser(Principal user) {
        return ResponseEntity.ok(user);
    }

    @GetMapping(value = "/getAllUsers")
    public ResponseEntity<List<AuthUser>> getAllUsers() {
        return new ResponseEntity<>(apiUserService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/getUserById/{userId}")
    public ResponseEntity<AuthUser> getUserById(@PathVariable("userId") Long userId) {
        return new ResponseEntity<>(apiUserService.findById(userId), HttpStatus.OK);
    }

    @GetMapping(value = "/getUserByUsername")
    public ResponseEntity<AuthUser> getUserByUsername(@RequestParam("username") String username) {
        return new ResponseEntity<>(apiUserService.findByUsername(username), HttpStatus.OK);
    }

    @PostMapping(value = "/createUser")
    public ResponseEntity<AuthUser> createUser(@RequestBody AuthUserRequest request) {
        AuthUser newAuthUser = apiUserService.saveUser(request);
        return new ResponseEntity<>(newAuthUser, HttpStatus.CREATED);
    }
}
