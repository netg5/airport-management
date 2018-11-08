/*
 * Copyright (c) 2018 Sergei Visotsky
 */

package org.sergei.flightreservation.service;

import org.sergei.flightreservation.dao.UserDAO;
import org.sergei.flightreservation.model.User;
import org.sergei.flightreservation.model.UserRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

/**
 * @author Sergei Visotsky, 2018
 */
@Service
public class ApiUserService {

    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApiUserService(UserDAO userDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDAO.save(user);
        return user;
    }

    // Saving admin once!
    /*@PostConstruct
    private void saveDefaultUser() {
        userDAO.save(
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
