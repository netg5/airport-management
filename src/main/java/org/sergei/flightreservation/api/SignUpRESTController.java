package org.sergei.flightreservation.api;

import org.sergei.flightreservation.model.User;
import org.sergei.flightreservation.model.UserRoles;
import org.sergei.flightreservation.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

/**
 * @author Sergei Visotsky, 2018
 */
@RestController
@RequestMapping("/signup")
public class SignUpRESTController {

    @Autowired
    private SignUpService signUpService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        user.setUserRoles(Collections.singletonList(new UserRoles("USER")));
        User newUser = signUpService.saveUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
}
