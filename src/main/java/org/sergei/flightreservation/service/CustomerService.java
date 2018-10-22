package org.sergei.flightreservation.service;

import org.modelmapper.ModelMapper;
import org.sergei.flightreservation.dao.CustomerDAO;
import org.sergei.flightreservation.dto.CustomerDTO;
import org.sergei.flightreservation.model.Customer;
import org.sergei.flightreservation.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService implements IService<CustomerDTO> {

    private final ModelMapper modelMapper;
    private final CustomerDAO customerDAO;

    @Autowired
    public CustomerService(ModelMapper modelMapper, CustomerDAO customerDAO) {
        this.modelMapper = modelMapper;
        this.customerDAO = customerDAO;
    }

    @Override
    public CustomerDTO findOne(Long aLong) {
        Customer customer = new Customer();
        return modelMapper.map(customer, CustomerDTO.class);
    }

    @Override
    public List<CustomerDTO> findAll() {
        List<Customer> customerList = customerDAO.findAll();
        return ObjectMapperUtils.mapAll(customerList, CustomerDTO.class);
    }

    @Override
    public void save(CustomerDTO entity) {

    }

    @Override
    public void update(CustomerDTO entity) {

    }

    @Override
    public void delete(CustomerDTO entity) {

    }
}
