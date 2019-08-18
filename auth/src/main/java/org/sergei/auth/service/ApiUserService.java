package org.sergei.auth.service;

import org.sergei.auth.jpa.model.AuthUser;
import org.sergei.auth.rest.AuthUserRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Service
public interface ApiUserService {
    List<AuthUser> findAll();

    AuthUser findById(Long userId);

    AuthUser saveUser(AuthUserRequest request);

    AuthUser findByUsername(String username);
}
