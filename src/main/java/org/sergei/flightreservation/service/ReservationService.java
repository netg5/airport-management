/*
 * Copyright (c) 2018 Sergei Visotsky
 */

package org.sergei.flightreservation.service;

import org.modelmapper.ModelMapper;
import org.sergei.flightreservation.dao.generic.GenericJpaDAO;
import org.sergei.flightreservation.dto.CustomerDTO;
import org.sergei.flightreservation.dto.ReservationDTO;
import org.sergei.flightreservation.dto.RouteDTO;
import org.sergei.flightreservation.exceptions.ResourceNotFoundException;
import org.sergei.flightreservation.model.Customer;
import org.sergei.flightreservation.model.Reservation;
import org.sergei.flightreservation.model.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sergei Visotsky, 2018
 */
@Service
public class ReservationService implements IService<ReservationDTO> {

    private final ModelMapper modelMapper;
    private final CustomerService customerService;
    private final RouteService routeService;
    private GenericJpaDAO<Reservation> genericDAO;

    @Autowired
    public ReservationService(ModelMapper modelMapper, CustomerService customerService, RouteService routeService) {
        this.modelMapper = modelMapper;
        this.customerService = customerService;
        this.routeService = routeService;
    }

    @Autowired
    public void setGenericDAO(GenericJpaDAO<Reservation> genericDAO) {
        this.genericDAO = genericDAO;
        genericDAO.setPersistentClass(Reservation.class);
    }

    @Override
    public ReservationDTO findOne(Long reservationId) {
        return null;
    }

    @Override
    public List<ReservationDTO> findAll() {
        return null;
    }

    @Override
    public ReservationDTO save(ReservationDTO reservationDTO) {
        return null;
    }

    public ReservationDTO saveForCustomer(Long customerId, ReservationDTO reservationDTO) {
        CustomerDTO customerDTO = customerService.findOne(customerId);
        if (customerDTO == null) {
            throw new ResourceNotFoundException("Customer with this ID not found");
        }
        /*RouteDTO routeDTO = routeService.findOne(reservationDTO.getRouteId());
        if (routeDTO == null) {
            throw new ResourceNotFoundException("Route with this ID not found");
        }*/
//        reservationDTO.setCustomerId(customerId);
        reservationDTO.setCustomerDTO(customerDTO);
        reservationDTO.setRouteId(reservationDTO.getRouteId());
        Reservation reservation = modelMapper.map(reservationDTO, Reservation.class);
        genericDAO.save(reservation);
        return reservationDTO;
    }

    @Override
    public ReservationDTO update(Long reservationId, ReservationDTO reservationDTO) {
        return null;
    }

    @Override
    public ReservationDTO delete(Long reservationId) {
        return null;
    }
}
