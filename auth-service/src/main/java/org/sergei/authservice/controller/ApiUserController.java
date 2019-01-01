package org.sergei.authservice.controller;

import io.swagger.annotations.*;
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
@Api(
        value = "/auth-api/users",
        produces = "application/json"
)
@RestController
@RequestMapping(value = "/users", produces = "application/json")
public class ApiUserController {

    private final ApiUserService apiUserService;

    @Autowired
    public ApiUserController(ApiUserService apiUserService) {
        this.apiUserService = apiUserService;
    }

    @ApiOperation("Method to authenticate user")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "User not found")
            }
    )
    @GetMapping("/authenticate")
    public ResponseEntity<Principal> authenticateUser(Principal user) {
        return ResponseEntity.ok(user);
    }

    @ApiOperation(
            value = "Method to get all users",
            notes = "Allowed for the ROLE_ADMIN only"
    )
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(apiUserService.findAll(), HttpStatus.OK);
    }

    @ApiOperation(
            value = "Method to user by ID",
            notes = "Allowed for the ROLE_ADMIN only"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "User not found")
            }
    )
    @GetMapping("/{userId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<User> getUserById(@ApiParam(value = "ID of the user to be found", required = true)
                                            @PathVariable("userId") Long userId) {
        return new ResponseEntity<>(apiUserService.findById(userId), HttpStatus.OK);
    }

    @ApiOperation(
            value = "Method to user by username",
            notes = "Allowed for the ROLE_ADMIN only"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(code = 404, message = "User not found")
            }
    )
    @GetMapping(params = "username")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<User> getUserByUsername(@ApiParam(value = "username of the user to be found", required = true)
                                                  @RequestParam("username") String username) {
        return new ResponseEntity<>(apiUserService.findByUsername(username), HttpStatus.OK);
    }

    @ApiOperation(
            value = "Method to create user",
            notes = "Allowed for the ROLE_ADMIN only"
    )
    @PostMapping(consumes = "application/json")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<User> createUser(@ApiParam(value = "User payload to be saved", required = true)
                                           @RequestBody User user) {
        user.setUserRoles(Collections.singletonList(new UserRoles("USER")));
        User newUser = apiUserService.saveUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
}
