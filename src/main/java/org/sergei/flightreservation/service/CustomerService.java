package org.sergei.flightreservation.service;

import org.sergei.flightreservation.dao.CustomerDAO;
import org.sergei.flightreservation.dto.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerDAO customerDAO;

    public List<CustomerDTO> findAll() {
//        return customerDAO.findAll();
        return null;
    }


}
