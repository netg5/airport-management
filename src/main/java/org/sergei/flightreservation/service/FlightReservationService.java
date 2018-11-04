/*
 * Copyright (c) 2018 Sergei Visotsky
 */

package org.sergei.flightreservation.service;

import org.modelmapper.ModelMapper;
import org.sergei.flightreservation.dao.generic.GenericJpaDAO;
import org.sergei.flightreservation.dto.CustomerDTO;
import org.sergei.flightreservation.dto.FlightReservationDTO;
import org.sergei.flightreservation.dto.RouteDTO;
import org.sergei.flightreservation.exceptions.ResourceNotFoundException;
import org.sergei.flightreservation.model.FlightReservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sergei Visotsky, 2018
 */
@Service
public class FlightReservationService {

    private final ModelMapper modelMapper;
    private final CustomerService customerService;
    private final RouteService routeService;
    private GenericJpaDAO<FlightReservation> genericDAO;

    @Autowired
    public FlightReservationService(ModelMapper modelMapper, CustomerService customerService, RouteService routeService) {
        this.modelMapper = modelMapper;
        this.customerService = customerService;
        this.routeService = routeService;
    }

    @Autowired
    public void setGenericDAO(GenericJpaDAO<FlightReservation> genericDAO) {
        this.genericDAO = genericDAO;
        genericDAO.setPersistentClass(FlightReservation.class);
    }

    public FlightReservationDTO findOne(Long reservationId) {
        return null;
    }

    public List<FlightReservationDTO> findAll() {
        return null;
    }

    public FlightReservationDTO save(FlightReservationDTO flightReservationDTO) {
        return null;
    }

    public FlightReservationDTO saveForCustomer(Long customerId, FlightReservationDTO flightReservationDTO) {
        CustomerDTO customerDTO = customerService.findOne(customerId);
        if (customerDTO == null) {
            throw new ResourceNotFoundException("Customer with this ID not found");
        }
        RouteDTO routeDTO = routeService.findOne(flightReservationDTO.getRouteId());
        if (routeDTO == null) {
            throw new ResourceNotFoundException("Route with this ID not found");
        }
        flightReservationDTO.setCustomerDTO(customerDTO);
        flightReservationDTO.setRouteDTO(routeDTO);
        FlightReservation flightReservation = modelMapper.map(flightReservationDTO, FlightReservation.class);
        genericDAO.save(flightReservation);
        return flightReservationDTO;
    }

    public FlightReservationDTO update(Long reservationId, FlightReservationDTO flightReservationDTO) {
        return null;
    }

    public FlightReservationDTO delete(Long reservationId) {
        return null;
    }
}
