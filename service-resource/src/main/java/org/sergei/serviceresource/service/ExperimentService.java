/*
 * Copyright 2018-2019 the original author.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sergei.serviceresource.service;

import org.apache.tomcat.util.codec.binary.Base64;
import org.sergei.serviceresource.model.AuthTokenInfo;
import org.sergei.serviceresource.model.CustomerIds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resources;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Sergei Visotsky
 */
@Deprecated
@Service
public class ExperimentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExperimentService.class);

    private static final String REST_RESOURCE_URI = "https://localhost:9090/flight-api";
    private static final String AUTH_SERVER = "https://localhost:9090/auth-api/oauth/token";
    private static final String PASSWORD_GRANT = "?grant_type=password";
    private static final String USERNAME = "&username=";
    private static final String PASSWORD = "&password=";
    private static final String ACCESS_TOKEN = "?access_token=";

    @Value("${oauth.client-id}")
    private String clientId;

    @Value("${oauth.client-secret}")
    private String clientSecret;

    @Value("${oauth.username}")
    private String usernameValue;

    @Value("${oauth.password}")
    private String passwordValue;

    private final RestTemplate restTemplate;
    private final HttpHeaders httpHeaders;

    @Autowired
    public ExperimentService(RestTemplate restTemplate, HttpHeaders httpHeaders) {
        this.restTemplate = restTemplate;
        this.httpHeaders = httpHeaders;
    }

    /**
     * Prepare HTTP Headers
     *
     * @return return headers
     */
    private HttpHeaders getHeaders() {
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return httpHeaders;
    }

    /**
     * Add HTTP Authorization header, using Basic-Authentication to send client-credentials.
     *
     * @return headers with Authorization header added
     */
    private HttpHeaders getHeadersWithClientCredentials() {
        String plainClientCredentials = clientId + ":" + clientSecret;
        String base64ClientCredentials = new String(Base64.encodeBase64(plainClientCredentials.getBytes()));

        HttpHeaders headers = getHeaders();
        headers.add("Authorization", "Basic " + base64ClientCredentials);
        return headers;
    }

    /**
     * Send a POST request [on /oauth/token] to get an access_token, which will then be send with each request.
     *
     * @return access token to access resources
     */
    @SuppressWarnings("unchecked")
    private AuthTokenInfo sendTokenRequest() {

        HttpEntity<String> request = new HttpEntity<>(getHeadersWithClientCredentials());
        LOGGER.debug("Get headers with client credentials: {}", getHeadersWithClientCredentials());
        LOGGER.debug("Request body: {}", request.getBody());
        ResponseEntity<Object> response =
                this.restTemplate.exchange(AUTH_SERVER + PASSWORD_GRANT + USERNAME + usernameValue + PASSWORD + passwordValue,
                        HttpMethod.POST, request, Object.class);
        LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) response.getBody();
        AuthTokenInfo tokenInfo = null;

        if (map != null) {
            tokenInfo = new AuthTokenInfo();
            tokenInfo.setAccessToken((String) map.get("access_token"));
            tokenInfo.setTokenType((String) map.get("token_type"));
            tokenInfo.setRefreshToken((String) map.get("refresh_token"));
            tokenInfo.setExpiresIn((int) map.get("expires_in"));
            tokenInfo.setScope((String) map.get("scope"));
            LOGGER.debug("Authentication response: {}", response.getBody());
        } else {
            LOGGER.debug("User does not exist");
        }
        return tokenInfo;
    }

    /**
     * Get all IDs of all customers
     *
     * @return list of all IDs
     */
    public List<Long> getAllCustomerIds() {
        AuthTokenInfo tokenInfo = sendTokenRequest();
        LOGGER.debug("Token info: {}", tokenInfo.toString());
        HttpEntity<String> request = new HttpEntity<>(getHeaders());
        return this.restTemplate.exchange(REST_RESOURCE_URI + "/customers" + ACCESS_TOKEN + tokenInfo.getAccessToken(),
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<Resources<CustomerIds>>() {
                })
                .getBody()
                .getContent()
                .stream()
                .map(CustomerIds::getCustomerId)
                .collect(Collectors.toList());
    }
}