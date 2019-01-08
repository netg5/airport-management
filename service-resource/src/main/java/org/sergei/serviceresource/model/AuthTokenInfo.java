package org.sergei.serviceresource.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Sergei Visotsky
 */
@Deprecated
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthTokenInfo {
    private String accessToken;
    private String tokenType;
    private String refreshToken;
    private Integer expiresIn;
    private String scope;
}