/*
 * Copyright (c) 2018 Sergei Visotsky
 */

package org.sergei.flightreservation.service;

import org.modelmapper.ModelMapper;
import org.sergei.flightreservation.dao.AircraftDAO;
import org.sergei.flightreservation.dao.CustomerDAO;
import org.sergei.flightreservation.dao.FlightReservationDAO;
import org.sergei.flightreservation.dao.RouteDAO;
import org.sergei.flightreservation.dto.AircraftDTO;
import org.sergei.flightreservation.dto.FlightReservationDTO;
import org.sergei.flightreservation.dto.FlightReservationExtendedDTO;
import org.sergei.flightreservation.dto.RouteExtendedDTO;
import org.sergei.flightreservation.exceptions.ResourceNotFoundException;
import org.sergei.flightreservation.model.Aircraft;
import org.sergei.flightreservation.model.Customer;
import org.sergei.flightreservation.model.FlightReservation;
import org.sergei.flightreservation.model.Route;
import org.sergei.flightreservation.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sergei Visotsky, 2018
 */
@Service
public class FlightReservationService {

    private final ModelMapper modelMapper;
    private final CustomerDAO customerDAO;
    private final RouteDAO routeDAO;
    private FlightReservationDAO flightReservationDAO;
    private final AircraftDAO aircraftDAO;

    @Autowired
    public FlightReservationService(ModelMapper modelMapper,
                                    CustomerDAO customerDAO, RouteDAO routeDAO,
                                    FlightReservationDAO flightReservationDAO, AircraftDAO aircraftDAO) {
        this.modelMapper = modelMapper;
        this.customerDAO = customerDAO;
        this.routeDAO = routeDAO;
        this.flightReservationDAO = flightReservationDAO;
        this.aircraftDAO = aircraftDAO;
    }

    /**
     * Method to get flight reservation for the customer by reservation ID
     *
     * @param customerId    gets customer ID as a parameter
     * @param reservationId gets flight reservation ID as a parameter
     * @return Flight reservation extended DTO
     */
    public FlightReservationExtendedDTO getOneForCustomerById(Long customerId, Long reservationId) {
        Customer customer = customerDAO.findOne(customerId);
        if (customer == null) {
            throw new ResourceNotFoundException("Customer with this ID not found");
        }

        FlightReservation flightReservation = flightReservationDAO.findOneForCustomer(customerId, reservationId);
        if (flightReservation == null) {
            throw new ResourceNotFoundException("Flight reservation with this ID not found");
        }
        FlightReservationExtendedDTO flightReservationExtendedDTO =
                modelMapper.map(flightReservation, FlightReservationExtendedDTO.class);
        flightReservationExtendedDTO.setCustomerId(customer.getCustomerId());

        // Find route by ID
        Route route = routeDAO.findOne(flightReservation.getRoute().getRouteId());
        RouteExtendedDTO routeExtendedDTO = modelMapper.map(route, RouteExtendedDTO.class);

        // Find aircraft by ID taken from the entity
        Aircraft aircraft = aircraftDAO.findOne(route.getAircraft().getAircraftId());

        // Set aircraft DTO to the flight reservation extended DTO
        routeExtendedDTO.setAircraftDTO(modelMapper.map(aircraft, AircraftDTO.class));
        flightReservationExtendedDTO.setRouteExtendedDTO(routeExtendedDTO);

        return flightReservationExtendedDTO;
    }

    /**
     * Method to get all flight reservations for the customer
     *
     * @param customerId gets customer ID
     * @return extended flight reservation DTO
     */
    public List<FlightReservationExtendedDTO> getAllReservationsForCustomer(Long customerId) {
        // Find customer
        Customer customer = customerDAO.findOne(customerId);
        if (customer == null) {
            throw new ResourceNotFoundException("Customer with this ID not found");
        }

        // Find all flight reservation for the customer
        List<FlightReservation> flightReservation = flightReservationDAO.findAllForCustomer(customerId);
        List<FlightReservationExtendedDTO> flightReservationExtendedDTOList =
                ObjectMapperUtils.mapAll(flightReservation, FlightReservationExtendedDTO.class);
        int counter = 0;
        // For each DTO set customer ID, route extended DTO
        for (FlightReservationExtendedDTO flightReservationExtendedDTO : flightReservationExtendedDTOList) {
            // Set customer ID in DTO response
            flightReservationExtendedDTO.setCustomerId(customer.getCustomerId());

            // Find route by ID
            Route route = routeDAO.findOne(flightReservation.get(counter).getRoute().getRouteId());
            RouteExtendedDTO routeExtendedDTO = modelMapper.map(route, RouteExtendedDTO.class);

            // Find aircraft by ID taken from the entity
            Aircraft aircraft = aircraftDAO.findOne(route.getAircraft().getAircraftId());

            // Set aircraft DTO to the flight reservation extended DTO
            routeExtendedDTO.setAircraftDTO(modelMapper.map(aircraft, AircraftDTO.class));
            flightReservationExtendedDTO.setRouteExtendedDTO(routeExtendedDTO);
            counter++;
        }

        // Returns extended flight reservation DTO
        return flightReservationExtendedDTOList;
    }

    /**
     * Method to create reservation for customer
     *
     * @param customerId           Gets customer ID
     * @param flightReservationDTO get flight reservation DTO from controller
     * @return flight reservation DTO as a response
     */
    public FlightReservationDTO createReservationForCustomer(Long customerId,
                                                             FlightReservationDTO flightReservationDTO) {
        // Find customer by ID
        Customer customer = customerDAO.findOne(customerId);
        if (customer == null) {
            throw new ResourceNotFoundException("Customer with this ID not found");
        }

        // Find route by route ID
        Route route = routeDAO.findOne(flightReservationDTO.getRouteId());
        if (route == null) {
            throw new ResourceNotFoundException("Route with this ID not found");
        }

        // Set customer ID and route into the flight reservation DTO
        flightReservationDTO.setCustomerId(customer.getCustomerId());
        flightReservationDTO.setRouteId(route.getRouteId());
        FlightReservation flightReservation = modelMapper.map(flightReservationDTO, FlightReservation.class);
        flightReservationDAO.save(flightReservation);
        return flightReservationDTO;
    }

    /**
     * Method to update reservation details
     *
     * @param customerId           gets as a parameter customer ID
     * @param reservationId        get as a parameter reservation ID
     * @param flightReservationDTO get as a parameter Flight reservation DTO
     * @return flight reservation DTO
     */
    public FlightReservationDTO updateReservationForCustomer(Long customerId,
                                                             Long reservationId,
                                                             FlightReservationDTO flightReservationDTO) {
        // Find customer by ID
        Customer customer = customerDAO.findOne(customerId);
        if (customer == null) {
            throw new ResourceNotFoundException("Customer with this ID not found");
        }

        flightReservationDTO.setCustomerId(customer.getCustomerId());
        flightReservationDTO.setReservationId(reservationId);

        FlightReservation flightReservation = modelMapper.map(flightReservationDTO, FlightReservation.class);
        flightReservationDAO.update(flightReservation);

        return flightReservationDTO;
    }

    /**
     * Delete reservation by ID
     *
     * @param reservationId get reservation ID to be deleted
     * @return deleted reservation DTO
     */
    public FlightReservationExtendedDTO delete(Long reservationId) {
        FlightReservation flightReservation = flightReservationDAO.findOne(reservationId);
        flightReservationDAO.delete(flightReservation);
        return modelMapper.map(flightReservation, FlightReservationExtendedDTO.class);
    }
}
