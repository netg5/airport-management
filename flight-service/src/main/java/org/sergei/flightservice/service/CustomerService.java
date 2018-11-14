/*
 * Copyright (c) Sergei Visotsky, 2018
 */

package org.sergei.flightservice.service;

import org.modelmapper.ModelMapper;
import org.sergei.flightservice.dto.CustomerDTO;
import org.sergei.flightservice.exceptions.ResourceNotFoundException;
import org.sergei.flightservice.model.Customer;
import org.sergei.flightservice.repository.CustomerRepository;
import org.sergei.flightservice.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sergei Visotsky, 2018
 */
@Service
public class CustomerService {

    private final ModelMapper modelMapper;
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(ModelMapper modelMapper, CustomerRepository customerRepository) {
        this.modelMapper = modelMapper;
        this.customerRepository = customerRepository;
    }

    /**
     * Find customer by ID
     *
     * @param customerId get customer ID as a parameter
     * @return customer DTO
     */
    public CustomerDTO findOne(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer with this ID not found")
                );
        return modelMapper.map(customer, CustomerDTO.class);
    }

    /**
     * Find all customers
     *
     * @return list with customer DTO
     */
    public List<CustomerDTO> findAll() {
        List<Customer> customerList = customerRepository.findAll();
        return ObjectMapperUtils.mapAll(customerList, CustomerDTO.class);
    }

    /**
     * Save customer
     *
     * @param customerDTO gets customer DTO as a parameter
     * @return customer DTO as a response
     */
    public CustomerDTO save(CustomerDTO customerDTO) {
        Customer customer = modelMapper.map(customerDTO, Customer.class);
        customerRepository.save(customer);
        return customerDTO;
    }

    /**
     * Method to update customer details
     *
     * @param customerId  gets customer ID as a parameter
     * @param customerDTO gets customer DO as a prameter
     * @return customer DTO as a response
     */
    public CustomerDTO update(Long customerId, CustomerDTO customerDTO) {
        customerDTO.setCustomerId(customerId);

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer with this ID not found")
                );
        customer.setFirstName(customerDTO.getFirstName());
        customer.setLastName(customerDTO.getLastName());
        customer.setAge(customerDTO.getAge());

        customerRepository.save(customer);

        return customerDTO;
    }

    /**
     * Delete customer
     *
     * @param customerId get customer ID as a parameter
     * @return customer DTO as a response
     */
    public CustomerDTO delete(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer with this ID not found")
                );
        customerRepository.delete(customer);
        return modelMapper.map(customer, CustomerDTO.class);
    }
}
