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

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.sergei.mainui.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Slf4j
@Service
public class ReservationService {

    private static final String RESERVATION_API_URI = "http://localhost:8080/reservation";
    private static final String CUSTOMERS_PATH = "/customers/";
    private static final String RESERVATIONS_PATH = "/reservations";
    private static final String ACCESS_TOKEN_PARAM = "?access_token=";
    private static final String ROUTE_JSON_PATH = "reservedRoute";
    private static final String AIRCRAFT_JSON_PATH = "aircraftId";

    private final RestTemplate restTemplate;
    private final TokenRetrievalService tokenRetrievalService;

    @Autowired
    public ReservationService(RestTemplate restTemplate, TokenRetrievalService tokenRetrievalService) {
        this.restTemplate = restTemplate;
        this.tokenRetrievalService = tokenRetrievalService;
    }

    /**
     * Get collection of reservations for a specific passenger
     *
     * @param customerId whose reservations should be found
     * @return collection of passenger reservations
     * @throws IOException   IO exceptions and JSONException
     * @throws JSONException in case we have any JSON parsing problems
     */
    public List<Reservation> getReservationsByCustomerId(Long customerId) throws IOException, JSONException {
        AuthTokenInfo tokenInfo = tokenRetrievalService.sendTokenRequest();
        HttpEntity<String> request = new HttpEntity<>(tokenRetrievalService.getHeaders());
        ResponseEntity<String> responseEntity = this.restTemplate.exchange(RESERVATION_API_URI +
                        CUSTOMERS_PATH + customerId + RESERVATIONS_PATH + ACCESS_TOKEN_PARAM + tokenInfo.getAccessToken(),
                HttpMethod.GET, request, String.class);

        String data = responseEntity.getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        JsonNode jsonNode = objectMapper.readTree(data);

        // Extract data after [ /_embedded/reservationExtendedDTOList ] tags
        String nodeAt = jsonNode.at("/_embedded/reservationExtendedDTOList").toString();
        log.debug("Node at is: {}", nodeAt);

        JSONArray jsonArray = new JSONArray(nodeAt);

        List<Reservation> reservationList = new LinkedList<>();

        // Puts each reservation to a collection
        for (int i = 0; i < jsonArray.length(); i++) {
            Long reservationId = Long.valueOf(jsonArray.getJSONObject(i).get("reservationId").toString());
            Long customerIdParsed = Long.valueOf(jsonArray.getJSONObject(i).get("passengerId").toString());
            LocalDateTime reservationDate = LocalDateTime.parse(
                    jsonArray.getJSONObject(i).get("dateOfFlying").toString()
            );

            // Parsing fields of reserved route data JSON response
            Long routeId = Long.valueOf(jsonArray.getJSONObject(i).getJSONObject(ROUTE_JSON_PATH).get("routeId").toString());
            Double distance = Double.valueOf(jsonArray.getJSONObject(i).getJSONObject(ROUTE_JSON_PATH).get("distance").toString());
            LocalDateTime departureTime = LocalDateTime.parse(
                    jsonArray.getJSONObject(i).getJSONObject(ROUTE_JSON_PATH).get("departureTime").toString()
            );
            LocalDateTime arrivalTime = LocalDateTime.parse(
                    jsonArray.getJSONObject(i).getJSONObject(ROUTE_JSON_PATH).get("arrivalTime").toString()
            );
            BigDecimal price = new BigDecimal(jsonArray.getJSONObject(i).getJSONObject(ROUTE_JSON_PATH).get("price").toString());
            String place = jsonArray.getJSONObject(i).getJSONObject(ROUTE_JSON_PATH).get("place").toString();

            // Parsing fields of Aircraft JSON response which flies for particular route
            Long aircraftId =
                    Long.valueOf(
                            jsonArray.getJSONObject(i)
                                    .getJSONObject(ROUTE_JSON_PATH).getJSONObject(AIRCRAFT_JSON_PATH).get("aircraftId").toString()
                    );
            String model =
                    jsonArray.getJSONObject(i)
                            .getJSONObject(ROUTE_JSON_PATH).getJSONObject(AIRCRAFT_JSON_PATH).get("model").toString();
            String aircraftName =
                    jsonArray.getJSONObject(i)
                            .getJSONObject(ROUTE_JSON_PATH).getJSONObject(AIRCRAFT_JSON_PATH).get("aircraftName").toString();
            Double aircraftWeight =
                    Double.valueOf(
                            jsonArray.getJSONObject(i)
                                    .getJSONObject(ROUTE_JSON_PATH).getJSONObject(AIRCRAFT_JSON_PATH).get("weight").toString()
                    );
            Integer maxPassengers =
                    Integer.valueOf(
                            jsonArray.getJSONObject(i)
                                    .getJSONObject(ROUTE_JSON_PATH).getJSONObject(AIRCRAFT_JSON_PATH).get("capacity").toString()
                    );
            Aircraft aircraft = new Aircraft(aircraftId, model, aircraftName, aircraftWeight, maxPassengers);
            Route route = new Route(routeId, distance, departureTime, arrivalTime, price, place, aircraft);

            reservationList.add(new Reservation(reservationId, customerIdParsed, reservationDate, route));
        }

        return reservationList;
    }

    /**
     * Method to make reservation for passenger
     *
     * @param reservationPost reservation model to be saved
     * @return saved reservation
     */
    public ReservationPost save(ReservationPost reservationPost) {
        log.debug("Route ID for which reservation was made: {}", reservationPost.getRouteId());
        log.debug("Date of the reservation: {}", reservationPost.getReservationDate());
        AuthTokenInfo tokenInfo = tokenRetrievalService.sendTokenRequest();
        HttpEntity<ReservationPost> request = new HttpEntity<>(reservationPost, tokenRetrievalService.getHeaders());
        long customerId = reservationPost.getCustomerId();
        return this.restTemplate.postForObject(RESERVATION_API_URI + CUSTOMERS_PATH +
                customerId + RESERVATIONS_PATH + ACCESS_TOKEN_PARAM + tokenInfo.getAccessToken(), request, ReservationPost.class);
    }
}
