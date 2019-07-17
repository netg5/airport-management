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

package org.sergei.reservation.service;

import org.sergei.reservation.jpa.model.Customer;
import org.sergei.reservation.jpa.repository.CustomerRepository;
import org.sergei.reservation.rest.dto.CustomerDTO;
import org.sergei.reservation.rest.dto.mappers.CustomerDTOListMapper;
import org.sergei.reservation.rest.dto.mappers.CustomerDTOMapper;
import org.sergei.reservation.rest.exceptions.ResourceNotFoundException;
import org.sergei.reservation.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.sergei.reservation.utils.ObjectMapperUtil.map;

/**
 * @author Sergei Visotsky
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerDTOMapper customerDTOMapper;
    private final CustomerDTOListMapper customerDTOListMapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository,
                               CustomerDTOMapper customerDTOMapper,
                               CustomerDTOListMapper customerDTOListMapper) {
        this.customerRepository = customerRepository;
        this.customerDTOMapper = customerDTOMapper;
        this.customerDTOListMapper = customerDTOListMapper;
    }

    /**
     * Find customer by ID
     *
     * @param customerId get customer ID as a parameter
     * @return customer
     */
    @Override
    public ResponseEntity<CustomerDTO> findOne(Long customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(customerDTOMapper.apply(customer.get()), HttpStatus.OK);
        }
    }

    /**
     * Find all customers
     *
     * @return list of customers
     */
    @Override
    public ResponseEntity<List<CustomerDTO>> findAll() {
        List<Customer> customerList = customerRepository.findAll();
        if (customerList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(customerDTOListMapper.apply(customerList), HttpStatus.OK);
        }
    }

    /**
     * Find ID of each customer in one JSON response as a list
     *
     * @return list of IDs
     */
    @Override
    public ResponseEntity<List<String>> findIdsOfAllCustomers() {
        List<String> customerIds = customerRepository.findIdsOfAllCustomers();
        if (customerIds.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(customerIds, HttpStatus.OK);
        }
    }

    /**
     * Find all customers with pagination
     *
     * @param page how many pages to show
     * @param size number of elements per page
     * @return list with entities
     */
    @Override
    public ResponseEntity<List<CustomerDTO>> findAllPaginated(int page, int size) {
        Page<Customer> customersPage = customerRepository.findAll(PageRequest.of(page, size));
        if (customersPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            List<CustomerDTO> customersList = customerDTOListMapper.apply(customersPage.getContent());
            return new ResponseEntity<>(customersList, HttpStatus.OK);
        }
    }

    /**
     * Save customerDTO
     *
     * @param customerDTO gets customerDTO DTO as a parameter
     * @return customerDTO DTO as a response
     */
    @Override
    public ResponseEntity<CustomerDTO> save(CustomerDTO customerDTO) {
        Customer customer = map(customerDTO, Customer.class);
        Customer savedCustomer = customerRepository.save(customer);
        return map(savedCustomer, CustomerDTO.class);
    }

    /**
     * Method to update customerDTO details
     *
     * @param customerId  gets customer ID as a parameter
     * @param customerDTO gets customerDTO DTO as a parameter
     * @return customerDTO DTO as a response
     */
    @Override
    public ResponseEntity<CustomerDTO> update(Long customerId, CustomerDTO customerDTO) {
        customerDTO.setCustomerId(customerId);

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(Constants.CUSTOMER_NOT_FOUND)
                );
        customer.setFirstName(customerDTO.getFirstName());
        customer.setLastName(customerDTO.getLastName());
        customer.setAge(customerDTO.getAge());

        customerRepository.save(customer);

        return customerDTO;
    }

    /**
     * Patch specific field of the customer
     *
     * @param customerId customer ID which field should be updated
     * @param params     List of params that should be updated
     * @return patched customer DTO
     */
    @Override
    public ResponseEntity<CustomerDTO> patch(Long customerId, Map<String, Object> params) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(Constants.CUSTOMER_NOT_FOUND)
                );
        if (params.get("firstName") != null) {
            customer.setFirstName(String.valueOf(params.get("firstName")));
        }
        if (params.get("lastName") != null) {
            customer.setLastName(String.valueOf(params.get("lastName")));
        }
        if (params.get("age") != null) {
            customer.setAge(Integer.valueOf(String.valueOf(params.get("age"))));
        }
        return map(customerRepository.save(customer), CustomerDTO.class);
    }

    /**
     * Delete customer
     *
     * @param customerId get customer ID as a parameter
     * @return customer DTO as a response
     */
    @Override
    public ResponseEntity<CustomerDTO> delete(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(Constants.CUSTOMER_NOT_FOUND)
                );
        customerRepository.delete(customer);
        return map(customer, CustomerDTO.class);
    }
}
