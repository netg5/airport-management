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

import org.sergei.frontendservice.model.AuthTokenInfo;
import org.sergei.frontendservice.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Service
public class ReservationService {

    private final RestTemplate restTemplate;
    private final TokenRetrievalService tokenRetrievalService;

    private static final String RESERVATION_API_URI = "https://localhost:9090/reservation-api";
    private static final String CUSTOMERS_PATH = "/customers/";
    private static final String RESERVATIONS_PATH = "/reservations";
    private static final String ACCESS_TOKEN = "?access_token=";

    @Autowired
    public ReservationService(RestTemplate restTemplate, TokenRetrievalService tokenRetrievalService) {
        this.restTemplate = restTemplate;
        this.tokenRetrievalService = tokenRetrievalService;
    }

    /**
     * Get collection of reservations for a specific customer
     *
     * @param customerId whose reservations should be found
     * @return collection of customer reservations
     */
    public ResponseEntity<Resources<Reservation>> getReservationsByCustomerId(Long customerId) {
        AuthTokenInfo tokenInfo = tokenRetrievalService.sendTokenRequest();
        HttpEntity<String> request = new HttpEntity<>(tokenRetrievalService.getHeaders());
        return this.restTemplate.exchange(RESERVATION_API_URI +
                        CUSTOMERS_PATH + customerId + RESERVATIONS_PATH + ACCESS_TOKEN + tokenInfo.getAccessToken(),
                HttpMethod.GET, request, new ParameterizedTypeReference<Resources<Reservation>>() {
                });
    }
}
