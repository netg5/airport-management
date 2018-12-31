package org.sergei.authservice.service;

import org.sergei.authservice.exceptions.ResourceNotFoundException;
import org.sergei.authservice.model.User;
import org.sergei.authservice.repository.UserRepository;
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

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(USER_NOT_FOUND)
                );
    }

    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(
                        () -> new ResourceNotFoundException(USER_NOT_FOUND)
                );
    }

    // Saving admin once! Hardcoded for dev purposes only!
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
