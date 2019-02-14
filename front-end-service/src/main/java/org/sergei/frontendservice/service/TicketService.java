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
import org.sergei.frontendservice.model.Ticket;
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
public class TicketService {

    private static final String TICKET_API_URI = "https://localhost:8080/ticket-api";
    private static final String TICKETS_PATH = "/tickets/";
    private static final String CUSTOMER_ID_PARAM = "?customerId=";
    private static final String ACCESS_TOKEN_PARAM = "&access_token=";

    private final RestTemplate restTemplate;
    private final TokenRetrievalService tokenRetrievalService;

    @Autowired
    public TicketService(RestTemplate restTemplate, TokenRetrievalService tokenRetrievalService) {
        this.restTemplate = restTemplate;
        this.tokenRetrievalService = tokenRetrievalService;
    }

    /**
     * Method to find all existing tickets for customer
     *
     * @param customerId whose tickets should be found
     * @return list of tickets found
     * @throws IOException input-output exception
     */
    public List<Ticket> findAllTicketsByCustomerId(Long customerId) throws IOException {
        AuthTokenInfo tokenInfo = tokenRetrievalService.sendTokenRequest();
        HttpEntity<String> request = new HttpEntity<>(tokenRetrievalService.getHeaders());
        ResponseEntity<String> responseEntity = this.restTemplate.exchange(TICKET_API_URI +
                        TICKETS_PATH + CUSTOMER_ID_PARAM + customerId + ACCESS_TOKEN_PARAM + tokenInfo.getAccessToken(),
                HttpMethod.GET, request, String.class);

        String data = responseEntity.getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        JsonNode jsonNode = objectMapper.readTree(data);
        String nodeAt = jsonNode.at("/_embedded/ticketList").toString();

        return objectMapper.readValue(nodeAt, new TypeReference<List<Ticket>>() {
        });
    }
}
