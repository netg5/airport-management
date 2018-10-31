/*
 * Copyright (c) Sergei Visotsky, 2018
 */

package org.sergei.flightreservation.service;

import org.modelmapper.ModelMapper;
import org.sergei.flightreservation.dao.GenericJpaDAO;
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
public class CustomerService implements IService<CustomerDTO> {

    @Autowired
    private ModelMapper modelMapper;

    private GenericJpaDAO<Customer> genericDAO;

    @Autowired
    public void setGenericDAO(GenericJpaDAO<Customer> genericDAO) {
        this.genericDAO = genericDAO;
        genericDAO.setPersistentClass(Customer.class);
    }

    @Override
    public CustomerDTO findOne(Long customerId) {
        Customer customer = genericDAO.findOne(customerId);
        return modelMapper.map(customer, CustomerDTO.class);
    }

    @Override
    public List<CustomerDTO> findAll() {
        List<Customer> customerList = genericDAO.findAll();
        return ObjectMapperUtils.mapAll(customerList, CustomerDTO.class);
    }

    @Override
    public CustomerDTO save(CustomerDTO customerDTO) {
        Customer customer = modelMapper.map(customerDTO, Customer.class);
        genericDAO.save(customer);
        return customerDTO;
    }

    @Override
    public CustomerDTO update(Long customerId, CustomerDTO customerDTO) {
        customerDTO.setCustomerId(customerId);

        Customer customer = modelMapper.map(customerDTO, Customer.class);
        genericDAO.update(customer);

        return customerDTO;
    }

    @Override
    public CustomerDTO delete(Long customerId) {
        Customer customer = genericDAO.findOne(customerId);
        genericDAO.delete(customer);
        return modelMapper.map(customer, CustomerDTO.class);
    }
}
