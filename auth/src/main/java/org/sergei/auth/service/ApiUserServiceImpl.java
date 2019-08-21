package org.sergei.auth.service;

import org.sergei.auth.exceptions.UserNotFoundException;
import org.sergei.auth.jpa.model.AuthUser;
import org.sergei.auth.jpa.model.AuthUserRoles;
import org.sergei.auth.jpa.repository.UserRepository;
import org.sergei.auth.rest.AuthUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Service
public class ApiUserServiceImpl implements ApiUserService {

    private static final String USER_NOT_FOUND = "User not found";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApiUserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<AuthUser> findAll() {
        return userRepository.findAll();
    }

    @Override
    public AuthUser findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(
                        () -> new UserNotFoundException(USER_NOT_FOUND)
                );
    }

    @Override
    public AuthUser saveUser(AuthUserRequest request) {
        AuthUserRoles authUserRoles = AuthUserRoles.builder()
                .roleName("USER")
                .build();

        AuthUser authUser = AuthUser.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .authUserRoles(List.of(authUserRoles))
                .build();
        return userRepository.save(authUser);
    }

    @Override
    public AuthUser findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(
                        () -> new UserNotFoundException(USER_NOT_FOUND)
                );
    }
}
