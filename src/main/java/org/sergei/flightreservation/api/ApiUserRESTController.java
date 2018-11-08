package org.sergei.flightreservation.api;

import org.sergei.flightreservation.model.User;
import org.sergei.flightreservation.model.UserRoles;
import org.sergei.flightreservation.service.ApiUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Collections;
import java.util.List;

/**
 * @author Sergei Visotsky, 2018
 */
@ApiIgnore
@RestController
@RequestMapping(value = "/users", produces = "application/json")
public class ApiUserRESTController {

    @Autowired
    private ApiUserService apiUserService;

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(apiUserService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        user.setUserRoles(Collections.singletonList(new UserRoles("USER")));
        User newUser = apiUserService.saveUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
}
