/*
 * Copyright 2018-2019 the original author.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sergei.authservice.rest;

import io.swagger.annotations.*;
import org.sergei.authservice.jpa.model.AuthUser;
import org.sergei.authservice.jpa.model.AuthUserRoles;
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
        produces = "application/json",
        consumes = "application/json"
)
@RestController
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
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
    public ResponseEntity<List<AuthUser>> getAllUsers() {
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
    public ResponseEntity<AuthUser> getUserById(@ApiParam(value = "ID of the user to be found", required = true)
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
    public ResponseEntity<AuthUser> getUserByUsername(@ApiParam(value = "username of the user to be found", required = true)
                                                      @RequestParam("username") String username) {
        return new ResponseEntity<>(apiUserService.findByUsername(username), HttpStatus.OK);
    }

    @ApiOperation(
            value = "Method to create authUser",
            notes = "Allowed for the ROLE_ADMIN only"
    )
    @PostMapping(consumes = "application/json")
    public ResponseEntity<AuthUser> createUser(@ApiParam(value = "AuthUser payload to be saved", required = true)
                                               @RequestBody AuthUser authUser) {
        authUser.setAuthUserRoles(Collections.singletonList(new AuthUserRoles("USER")));
        AuthUser newAuthUser = apiUserService.saveUser(authUser);
        return new ResponseEntity<>(newAuthUser, HttpStatus.CREATED);
    }
}
