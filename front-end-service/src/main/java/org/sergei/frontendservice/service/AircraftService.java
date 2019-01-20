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

package org.sergei.frontendservice.service;

import org.sergei.frontendservice.model.Aircraft;
import org.sergei.frontendservice.model.AuthTokenInfo;
import org.sergei.frontendservice.model.Customer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author Sergei Visotsky
 */
@Service
public class AircraftService {

    private static final String RESERVATION_API_URI = "https://localhost:9090/reservation-api";
    private static final String AIRCRAFTS_PATH = "/aircrafts/";
    private static final String ACCESS_TOKEN_PARAM = "?access_token=";

    private final RestTemplate restTemplate;
    private final TokenRetrievalService tokenRetrievalService;


    public AircraftService(RestTemplate restTemplate, TokenRetrievalService tokenRetrievalService) {
        this.restTemplate = restTemplate;
        this.tokenRetrievalService = tokenRetrievalService;
    }

    /**
     * Method to get aircraft by ID
     *
     * @param aircraftId which should be found
     * @return aircraft response to be shown in view
     */
    public ResponseEntity<Aircraft> getAircraftById(Long aircraftId) {
        AuthTokenInfo tokenInfo = tokenRetrievalService.sendTokenRequest();
        HttpEntity<String> request = new HttpEntity<>(tokenRetrievalService.getHeaders());
        return this.restTemplate.exchange(RESERVATION_API_URI + AIRCRAFTS_PATH + aircraftId + ACCESS_TOKEN_PARAM +
                tokenInfo.getAccessToken(), HttpMethod.GET, request, Aircraft.class);
    }

    public Aircraft save(Aircraft aircraft) {
        AuthTokenInfo tokenInfo = tokenRetrievalService.sendTokenRequest();
        HttpEntity<Aircraft> request = new HttpEntity<>(aircraft, tokenRetrievalService.getHeaders());
        return this.restTemplate.postForObject(RESERVATION_API_URI + AIRCRAFTS_PATH +
                ACCESS_TOKEN_PARAM + tokenInfo.getAccessToken(), request, Aircraft.class);
    }
}
