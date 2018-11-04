/*
 * Copyright (c) Sergei Visotsky, 2018
 */

package org.sergei.flightreservation.service;

import org.modelmapper.ModelMapper;
import org.sergei.flightreservation.dao.CustomerDAO;
import org.sergei.flightreservation.dto.CustomerDTO;
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

    public CustomerDTO findOne(Long customerId) {
        Customer customer = customerDAO.findOne(customerId);
        return modelMapper.map(customer, CustomerDTO.class);
    }

    public List<CustomerDTO> findAll() {
        List<Customer> customerList = customerDAO.findAll();
        return ObjectMapperUtils.mapAll(customerList, CustomerDTO.class);
    }

    public CustomerDTO save(CustomerDTO customerDTO) {
        Customer customer = modelMapper.map(customerDTO, Customer.class);
        customerDAO.save(customer);
        return customerDTO;
    }

    public CustomerDTO update(Long customerId, CustomerDTO customerDTO) {
        customerDTO.setCustomerId(customerId);

        Customer customer = modelMapper.map(customerDTO, Customer.class);
        customerDAO.update(customer);

        return customerDTO;
    }

    public CustomerDTO delete(Long customerId) {
        Customer customer = customerDAO.findOne(customerId);
        customerDAO.delete(customer);
        return modelMapper.map(customer, CustomerDTO.class);
    }
}
