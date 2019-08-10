package org.sergei.reservation.service;

import org.sergei.reservation.rest.dto.AuthTokenInfo;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

/**
 * Service layer which needs to perform internal back-end calls to the other
 * services in order to exchange data
 *
 * @author Sergei Visotsky
 */
@Service
public interface ExchangeAuthService {
    HttpHeaders getHeaders();

    HttpHeaders getHeadersWithClientCredentials();

    AuthTokenInfo sendTokenRequest();
}
