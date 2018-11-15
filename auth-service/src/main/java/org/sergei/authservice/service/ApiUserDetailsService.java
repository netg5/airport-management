package org.sergei.authservice.service;

import org.sergei.authservice.model.User;
import org.sergei.authservice.repository.UserRepository;
import org.sergei.authservice.model.ApiUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Sergei Visotsky, 2018
 */
@Service
public class ApiUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        return new ApiUserDetails(user);
    }
}
