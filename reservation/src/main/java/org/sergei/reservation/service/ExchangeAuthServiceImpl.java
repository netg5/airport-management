package org.sergei.reservation.service;

import org.apache.tomcat.util.codec.binary.Base64;
import org.sergei.reservation.rest.dto.AuthTokenInfoDTO;
import org.sergei.reservation.rest.exceptions.FlightRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.LinkedHashMap;

/**
 * Service layer which needs to perform internal back-end calls to the other
 * services in order to exchange data
 *
 * @author Sergei Visotsky
 */
@Service
public class ExchangeAuthServiceImpl implements ExchangeAuthService {

    @Value("${security.exchange.auth-server}")
    private String authServer;

    @Value("${security.exchange.client-id}")
    private String clientId;

    @Value("${security.exchange.client-secret}")
    private String clientSecret;

    @Value("${security.exchange.user}")
    private String user;

    @Value("${security.exchange.password}")
    private String password;

    private final RestTemplate restTemplate;

    @Autowired
    public ExchangeAuthServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return headers;
    }

    @Override
    public HttpHeaders getHeadersWithClientCredentials() {
        String plainClientCredentials = clientId + ":" + clientSecret;
        String base64ClientCredentials = new String(Base64.encodeBase64(plainClientCredentials.getBytes()));

        HttpHeaders headers = getHeaders();
        headers.add("Authorization", "Basic " + base64ClientCredentials);
        return headers;
    }

    @SuppressWarnings("unchecked")
    @Override
    public AuthTokenInfoDTO sendTokenRequest() {
        HttpEntity<String> request = new HttpEntity<>(getHeadersWithClientCredentials());
        ResponseEntity<Object> response =
                this.restTemplate.exchange(authServer + "?grant_type=password&username=" + user + "&password=" + password,
                        HttpMethod.POST, request, Object.class);
        LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) response.getBody();

        if (map == null) {
            throw new FlightRuntimeException("Exchange with data was not performed: UNAUTHORIZED");
        } else {
            return AuthTokenInfoDTO.builder()
                    .accessToken((String) map.get("access_token"))
                    .tokenType((String) map.get("token_type"))
                    .refreshToken((String) map.get("refresh_token"))
                    .expiresIn((Integer) map.get("expires_in"))
                    .scope((String) map.get("scope"))
                    .build();
        }
    }
}
