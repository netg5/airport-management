package org.sergei.auth.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Sergei Visotsky
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserRequest implements Serializable {
    private static final long serialVersionUID = 7747586326913003479L;
    private String username;
    private String password;
}
