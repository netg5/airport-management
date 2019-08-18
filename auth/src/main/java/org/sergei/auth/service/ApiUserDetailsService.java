package org.sergei.auth.service;

import org.sergei.auth.exceptions.UserNotFoundException;
import org.sergei.auth.jpa.model.AuthUserDetails;
import org.sergei.auth.jpa.repository.UserRepository;
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
        return new AuthUserDetails(
                userRepository.findByUsername(username)
                        .orElseThrow(
                                () -> new UserNotFoundException("User not found")
                        )
        );
    }
}
