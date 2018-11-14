package org.sergei.zuulgateway.service;

import org.sergei.zuulgateway.model.User;
import org.sergei.zuulgateway.repository.UserRepository;
import org.sergei.zuulgateway.security.ApiUserDetails;
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
        User user = userRepository.findByUserName(username);
        return new ApiUserDetails(user);
    }
}
