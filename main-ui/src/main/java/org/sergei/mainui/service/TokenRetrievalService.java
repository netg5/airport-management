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

package org.sergei.mainui.service;

import org.apache.tomcat.util.codec.binary.Base64;
import org.sergei.mainui.model.AuthTokenInfo;
import org.sergei.mainui.properties.OAuthClientProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.LinkedHashMap;

/**
 * @author Sergei Visotsky
 */
@Service
public class TokenRetrievalService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

    private static final String AUTH_SERVER = "http://localhost:8080/auth/oauth/token";
    private static final String PASSWORD_GRANT = "?grant_type=password";
    private static final String USERNAME = "&username=";
    private static final String PASSWORD = "&password=";

    private final RestTemplate restTemplate;
    private final HttpHeaders httpHeaders;
    private final OAuthClientProperties oauthClientProperties;

    @Autowired
    public TokenRetrievalService(RestTemplate restTemplate, HttpHeaders httpHeaders,
                                 @Qualifier("OAuthClientProperties")
                                         OAuthClientProperties oauthClientProperties) {
        this.restTemplate = restTemplate;
        this.httpHeaders = httpHeaders;
        this.oauthClientProperties = oauthClientProperties;
    }

    /**
     * Prepare HTTP Headers
     *
     * @return return headers
     */
    HttpHeaders getHeaders() {
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return httpHeaders;
    }

    /**
     * Add HTTP Authorization header, using Basic-Authentication to send client-credentials.
     *
     * @return headers with Authorization header added
     */
    private HttpHeaders getHeadersWithClientCredentials() {
        String plainClientCredentials = oauthClientProperties.getClientId() + ":" + oauthClientProperties.getClientSecret();
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
    AuthTokenInfo sendTokenRequest() {

        HttpEntity<String> request = new HttpEntity<>(getHeadersWithClientCredentials());
        ResponseEntity<Object> response =
                this.restTemplate.exchange(AUTH_SERVER + PASSWORD_GRANT + USERNAME +
                                oauthClientProperties.getUsername() + PASSWORD + oauthClientProperties.getPassword(),
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
        } else {
            LOGGER.debug("User does not exist");
        }
        return tokenInfo;
    }
}
