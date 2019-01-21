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

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.sergei.frontendservice.model.AuthTokenInfo;
import org.sergei.frontendservice.model.Reservation;
import org.sergei.frontendservice.model.ReservationPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Service
public class ReservationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationService.class);
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");

    private final RestTemplate restTemplate;
    private final TokenRetrievalService tokenRetrievalService;

    private static final String RESERVATION_API_URI = "https://localhost:9090/reservation-api";
    private static final String CUSTOMERS_PATH = "/customers/";
    private static final String RESERVATIONS_PATH = "/reservations";
    private static final String ACCESS_TOKEN_PARAM = "?access_token=";

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
     * @throws Exception IO exceptions and JSONException
     */
    public List<Reservation> getReservationsByCustomerId(Long customerId) throws Exception {
        AuthTokenInfo tokenInfo = tokenRetrievalService.sendTokenRequest();
        HttpEntity<String> request = new HttpEntity<>(tokenRetrievalService.getHeaders());
        ResponseEntity<String> responseEntity = this.restTemplate.exchange(RESERVATION_API_URI +
                        CUSTOMERS_PATH + customerId + RESERVATIONS_PATH + ACCESS_TOKEN_PARAM + tokenInfo.getAccessToken(),
                HttpMethod.GET, request, String.class);

        String data = responseEntity.getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        JsonNode jsonNode = objectMapper.readTree(data);
        LOGGER.debug("JSON node is: {}", jsonNode.toString());

        String nodeAt = jsonNode.at("/_embedded/reservationExtendedDTOList").toString();
        LOGGER.debug("Node at is: {}", nodeAt);

        JSONArray jsonArray = new JSONArray(nodeAt);

        List<Reservation> reservationList = new LinkedList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            Long reservationId = Long.valueOf(jsonArray.getJSONObject(i).get("reservationId").toString());
            Long customerIdParsed = Long.valueOf(jsonArray.getJSONObject(i).get("customerId").toString());
            LocalDateTime reservationDate = LocalDateTime.parse(
                    jsonArray.getJSONObject(i).get("reservationDate").toString()
            );

            reservationList.add(new Reservation(reservationId, customerIdParsed, reservationDate));
        }

        return reservationList;
    }

    /**
     * Method to make reservation for customer
     *
     * @param reservationPost reservation model to be saved
     */
    public ReservationPost save(ReservationPost reservationPost) {
        LOGGER.debug("Route ID for which reservation was made: {}", reservationPost.getRouteId());
        AuthTokenInfo tokenInfo = tokenRetrievalService.sendTokenRequest();
        HttpEntity<ReservationPost> request = new HttpEntity<>(reservationPost, tokenRetrievalService.getHeaders());
        long customerId = reservationPost.getCustomerId();
        return this.restTemplate.postForObject(RESERVATION_API_URI + CUSTOMERS_PATH +
                customerId + RESERVATIONS_PATH + ACCESS_TOKEN_PARAM + tokenInfo.getAccessToken(), request, ReservationPost.class);
    }
}
