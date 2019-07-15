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

package org.sergei.auth.service;

import org.sergei.auth.exceptions.UserNotFoundException;
import org.sergei.auth.jpa.model.AuthUser;
import org.sergei.auth.jpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Service
public class ApiUserService {

    private static final String USER_NOT_FOUND = "User not found";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApiUserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<AuthUser> findAll() {
        return userRepository.findAll();
    }

    public AuthUser findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(
                        () -> new UserNotFoundException(USER_NOT_FOUND)
                );
    }

    public AuthUser saveUser(AuthUser authUser) {
        authUser.setPassword(passwordEncoder.encode(authUser.getPassword()));
        userRepository.save(authUser);
        return authUser;
    }

    public AuthUser findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(
                        () -> new UserNotFoundException(USER_NOT_FOUND)
                );
    }

    // Saving admin once! Hardcoded for dev purposes only!
//    @PostConstruct
//    private void saveDefaultUser() {
//        userRepository.save(
//                new AuthUser("admin",
//                        passwordEncoder.encode("123456"),
//                        Arrays.asList(
//                                new AuthUserRoles("USER"),
//                                new AuthUserRoles("ADMIN")
//                        )
//                )
//        );
//    }
}
