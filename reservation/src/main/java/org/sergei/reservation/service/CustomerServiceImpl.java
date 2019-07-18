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
import org.sergei.reservation.rest.dto.response.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public ResponseEntity<ResponseDTO<CustomerDTO>> findOne(Long customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            CustomerDTO customerDTO = customerDTOMapper.apply(customer.get());
            ResponseDTO<CustomerDTO> response = new ResponseDTO<>();
            response.setErrorList(List.of());
            response.setResponse(List.of(customerDTO));
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    /**
     * Find all customers
     *
     * @return list of customers
     */
    @Override
    public ResponseEntity<ResponseDTO<CustomerDTO>> findAll() {
        List<Customer> customerList = customerRepository.findAll();
        if (customerList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            List<CustomerDTO> customerDTOList = customerDTOListMapper.apply(customerList);
            ResponseDTO<CustomerDTO> response = new ResponseDTO<>();
            response.setErrorList(List.of());
            response.setResponse(customerDTOList);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    /**
     * Find ID of each customer in one JSON response as a list
     *
     * @return list of IDs
     */
    @Override
    public ResponseEntity<ResponseDTO<String>> findIdsOfAllCustomers() {
        List<String> customerIds = customerRepository.findIdsOfAllCustomers();
        if (customerIds.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            ResponseDTO<String> response = new ResponseDTO<>();
            response.setErrorList(List.of());
            response.setResponse(customerIds);
            return new ResponseEntity<>(response, HttpStatus.OK);
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
    public ResponseEntity<ResponseDTO<CustomerDTO>> findAllPaginated(int page, int size) {
        Page<Customer> customersPage = customerRepository.findAll(PageRequest.of(page, size));
        if (customersPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            List<CustomerDTO> customerDTOList = customerDTOListMapper.apply(customersPage.getContent());
            ResponseDTO<CustomerDTO> response = new ResponseDTO<>();
            response.setErrorList(List.of());
            response.setResponse(customerDTOList);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    /**
     * Save customerDTO
     *
     * @param customerDTO gets customerDTO DTO as a parameter
     * @return customerDTO DTO as a response
     */
    @Override
    public ResponseEntity<ResponseDTO<CustomerDTO>> save(CustomerDTO customerDTO) {
        Customer customer = new Customer();

        customer.setId(customerDTO.getCustomerId());
        customer.setFirstName(customerDTO.getFirstName());
        customer.setLastName(customerDTO.getLastName());
        customer.setAge(customerDTO.getAge());

        Customer savedCustomer = customerRepository.save(customer);

        return new ResponseEntity<>(customerDTOMapper.apply(savedCustomer), HttpStatus.CREATED);
    }

    /**
     * Method to update customerDTO details
     *
     * @param customerId  gets customer ID as a parameter
     * @param customerDTO gets customerDTO DTO as a parameter
     * @return customerDTO DTO as a response
     */
    @Override
    public ResponseEntity<ResponseDTO<CustomerDTO>> update(Long customerId, CustomerDTO customerDTO) {
        customerDTO.setCustomerId(customerId);

        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            customer.get().setFirstName(customerDTO.getFirstName());
            customer.get().setLastName(customerDTO.getLastName());
            customer.get().setAge(customerDTO.getAge());
            customerRepository.save(customer.get());

            return new ResponseEntity<>(customerDTOMapper.apply(customer.get()), HttpStatus.OK);
        }
    }

    /**
     * Patch specific field of the customer
     *
     * @param customerId customer ID which field should be updated
     * @param params     List of params that should be updated
     * @return patched customer DTO
     */
    @Override
    public ResponseEntity<ResponseDTO<CustomerDTO>> patch(Long customerId, Map<String, Object> params) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            if (params.get("firstName") != null) {
                customer.get().setFirstName(String.valueOf(params.get("firstName")));
            }
            if (params.get("lastName") != null) {
                customer.get().setLastName(String.valueOf(params.get("lastName")));
            }
            if (params.get("age") != null) {
                customer.get().setAge(Integer.valueOf(String.valueOf(params.get("age"))));
            }
            Customer savedCustomer = customerRepository.save(customer.get());
            return new ResponseEntity<>(customerDTOMapper.apply(savedCustomer), HttpStatus.CREATED);
        }
    }

    /**
     * Delete customer
     *
     * @param customerId get customer ID as a parameter
     * @return customer DTO as a response
     */
    @Override
    public ResponseEntity<ResponseDTO<CustomerDTO>> delete(Long customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            customerRepository.delete(customer.get());
            return new ResponseEntity<>(customerDTOMapper.apply(customer.get()), HttpStatus.NO_CONTENT);
        }
    }
}
