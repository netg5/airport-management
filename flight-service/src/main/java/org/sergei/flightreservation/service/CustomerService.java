/*
 * Copyright (c) Sergei Visotsky, 2018
 */

package org.sergei.flightreservation.service;

import org.modelmapper.ModelMapper;
import org.sergei.flightreservation.dao.CustomerDAO;
import org.sergei.flightreservation.dto.CustomerDTO;
import org.sergei.flightreservation.exceptions.ResourceNotFoundException;
import org.sergei.flightreservation.model.Customer;
import org.sergei.flightreservation.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sergei Visotsky, 2018
 */
@Service
public class CustomerService {

    private final ModelMapper modelMapper;
    private final CustomerDAO customerDAO;

    @Autowired
    public CustomerService(ModelMapper modelMapper, CustomerDAO customerDAO) {
        this.modelMapper = modelMapper;
        this.customerDAO = customerDAO;
    }

    /**
     * Find customer by ID
     *
     * @param customerId get customer ID as a parameter
     * @return customer DTO
     */
    public CustomerDTO findOne(Long customerId) {
        Customer customer = customerDAO.findOne(customerId);
        if (customer == null) {
            throw new ResourceNotFoundException("Customer with this ID not found");
        }
        return modelMapper.map(customer, CustomerDTO.class);
    }

    /**
     * Find all customers
     *
     * @return list with customer DTO
     */
    public List<CustomerDTO> findAll() {
        List<Customer> customerList = customerDAO.findAll();
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
        customerDAO.save(customer);
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

        Customer customer = modelMapper.map(customerDTO, Customer.class);
        customerDAO.update(customer);

        return customerDTO;
    }

    /**
     * Delete customer
     *
     * @param customerId get customer ID as a parameter
     * @return customer DTO as a response
     */
    public CustomerDTO delete(Long customerId) {
        Customer customer = customerDAO.findOne(customerId);
        if (customer == null) {
            throw new ResourceNotFoundException("Customer with this ID not found");
        }
        customerDAO.delete(customer);
        return modelMapper.map(customer, CustomerDTO.class);
    }
}
