/*
 * Copyright (c) 2018 Sergei Visotsky
 */

package org.sergei.flightreservation.service;

import org.modelmapper.ModelMapper;
import org.sergei.flightreservation.dao.CustomerDAO;
import org.sergei.flightreservation.dao.FlightReservationDAO;
import org.sergei.flightreservation.dao.RouteDAO;
import org.sergei.flightreservation.dto.FlightReservationDTO;
import org.sergei.flightreservation.exceptions.ResourceNotFoundException;
import org.sergei.flightreservation.model.Customer;
import org.sergei.flightreservation.model.FlightReservation;
import org.sergei.flightreservation.model.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Sergei Visotsky, 2018
 */
@Service
public class FlightReservationService {

    private final ModelMapper modelMapper;
    private final CustomerDAO customerDAO;
    private final RouteDAO routeDAO;
    private FlightReservationDAO flightReservationDAO;

    @Autowired
    public FlightReservationService(ModelMapper modelMapper,
                                    CustomerDAO customerDAO, RouteDAO routeDAO,
                                    FlightReservationDAO flightReservationDAO) {
        this.modelMapper = modelMapper;
        this.customerDAO = customerDAO;
        this.routeDAO = routeDAO;
        this.flightReservationDAO = flightReservationDAO;
    }

    public FlightReservationDTO saveForCustomer(Long customerId, FlightReservationDTO flightReservationDTO) {
        Customer customer = customerDAO.findOne(customerId);
        if (customer == null) {
            throw new ResourceNotFoundException("Customer with this ID not found");
        }
        Route route = routeDAO.findOne(flightReservationDTO.getRouteId());
        if (route == null) {
            throw new ResourceNotFoundException("Route with this ID not found");
        }
        flightReservationDTO.setCustomerId(customer.getCustomerId());
        flightReservationDTO.setRouteId(route.getRouteId());
        FlightReservation flightReservation = modelMapper.map(flightReservationDTO, FlightReservation.class);
        flightReservationDAO.save(flightReservation);
        return flightReservationDTO;
    }
}
