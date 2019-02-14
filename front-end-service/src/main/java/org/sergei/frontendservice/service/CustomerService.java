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
import org.sergei.frontendservice.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author Sergei Visotsky
 */
@Service
public class CustomerService {

    private static final String RESERVATION_API_URI = "http://localhost:8080/reservation-api";
    private static final String CUSTOMERS_PATH = "/customers/";
    private static final String ACCESS_TOKEN_PARAM = "?access_token=";

    private final RestTemplate restTemplate;
    private final TokenRetrievalService tokenRetrievalService;

    @Autowired
    public CustomerService(RestTemplate restTemplate, TokenRetrievalService tokenRetrievalService) {
        this.restTemplate = restTemplate;
        this.tokenRetrievalService = tokenRetrievalService;
    }

    /**
     * Get customer by ID
     *
     * @param customerId ID of the customer to be found
     * @return customer entity
     */
    public ResponseEntity<Customer> getCustomerById(Long customerId) {
        AuthTokenInfo tokenInfo = tokenRetrievalService.sendTokenRequest();
        HttpEntity<String> request = new HttpEntity<>(tokenRetrievalService.getHeaders());
        return this.restTemplate.exchange(RESERVATION_API_URI +
                        CUSTOMERS_PATH + customerId + ACCESS_TOKEN_PARAM + tokenInfo.getAccessToken(),
                HttpMethod.GET, request, Customer.class);
    }

    /**
     * Method to save customer
     *
     * @param customer entity to be saved
     * @return saved entity
     */
    public Customer save(Customer customer) {
        AuthTokenInfo tokenInfo = tokenRetrievalService.sendTokenRequest();
        HttpEntity<Customer> request = new HttpEntity<>(customer, tokenRetrievalService.getHeaders());
        return this.restTemplate.postForObject(RESERVATION_API_URI + CUSTOMERS_PATH +
                ACCESS_TOKEN_PARAM + tokenInfo.getAccessToken(), request, Customer.class);
    }
}
