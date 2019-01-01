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
