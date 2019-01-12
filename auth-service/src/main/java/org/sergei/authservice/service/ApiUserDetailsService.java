/*
 * Copyright 2018-2019 Sergei Visotsky
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

package org.sergei.authservice.service;

import org.sergei.authservice.exceptions.ResourceNotFoundException;
import org.sergei.authservice.model.ApiUserDetails;
import org.sergei.authservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * @author Sergei Visotsky
 */
@Service
public class ApiUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public ApiUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return new ApiUserDetails(
                userRepository.findByUsername(username)
                        .orElseThrow(
                                () -> new ResourceNotFoundException("User not found")
                        )
        );
    }
}
