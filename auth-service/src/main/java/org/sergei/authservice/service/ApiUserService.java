/*
 * Copyright (c) 2018 Sergei Visotsky
 */

package org.sergei.authservice.service;

import org.sergei.authservice.exceptions.ResourceNotFoundException;
import org.sergei.authservice.model.User;
import org.sergei.authservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sergei Visotsky, 2018
 */
@Service
public class ApiUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApiUserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    public User findByUsername(String username) {
        User user = userRepository.findByUserName(username);
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }
        return user;
    }

    // Saving admin once!
    /*@PostConstruct
    private void saveDefaultUser() {
        userRepository.save(
                new User("admin",
                        passwordEncoder.encode("123456"),
                        Arrays.asList(
                                new UserRoles("USER"),
                                new UserRoles("ADMIN")
                        )
                )
        );
    }*/
}
