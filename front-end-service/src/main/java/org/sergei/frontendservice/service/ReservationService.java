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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.sergei.frontendservice.model.AuthTokenInfo;
import org.sergei.frontendservice.model.Reservation;
import org.sergei.frontendservice.model.ReservationPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Service
public class ReservationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationService.class);

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
    public ResponseEntity<List<Reservation>> getReservationsByCustomerId(Long customerId) throws IOException {
        AuthTokenInfo tokenInfo = tokenRetrievalService.sendTokenRequest();
        HttpEntity<String> request = new HttpEntity<>(tokenRetrievalService.getHeaders());
        ResponseEntity<String> responseEntity = this.restTemplate.exchange(RESERVATION_API_URI +
                        CUSTOMERS_PATH + customerId + RESERVATIONS_PATH + ACCESS_TOKEN + tokenInfo.getAccessToken(),
                HttpMethod.GET, request, String.class);

        String data = responseEntity.getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        JsonNode jsNode = objectMapper.readTree(data);
        String nodeAt = jsNode.at("/_embedded/reservationExtendedDTOList").toString();

        return objectMapper.readValue(nodeAt, new TypeReference<List<Reservation>>() {
        });
    }

    /**
     * Method to make reservation for customer
     *
     * @param reservationPost reservation model to be saved
     */
    public ReservationPost submitReservation(ReservationPost reservationPost) {
        LOGGER.debug("Route ID for which reservation was made: {}", reservationPost.getRouteId());
        AuthTokenInfo tokenInfo = tokenRetrievalService.sendTokenRequest();
        HttpEntity<ReservationPost> request = new HttpEntity<>(reservationPost, tokenRetrievalService.getHeaders());
        long customerId = reservationPost.getCusotmerId();
        return this.restTemplate.postForObject(RESERVATION_API_URI + CUSTOMERS_PATH +
                customerId + RESERVATIONS_PATH + ACCESS_TOKEN + tokenInfo.getAccessToken(), request, ReservationPost.class);
    }
}
