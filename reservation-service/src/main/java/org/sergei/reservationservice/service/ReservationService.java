/*
 * Copyright 2018-2019 the original author.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sergei.reservationservice.service;

import org.sergei.reservationservice.rest.dto.ReservationDTO;
import org.sergei.reservationservice.rest.dto.ReservationExtendedDTO;
import org.sergei.reservationservice.exceptions.ResourceNotFoundException;
import org.sergei.reservationservice.model.Customer;
import org.sergei.reservationservice.model.Reservation;
import org.sergei.reservationservice.model.Route;
import org.sergei.reservationservice.repository.CustomerRepository;
import org.sergei.reservationservice.repository.ReservationRepository;
import org.sergei.reservationservice.repository.RouteRepository;
import org.sergei.reservationservice.service.util.Constants;
import org.sergei.reservationservice.service.util.ServiceComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.sergei.reservationservice.util.ObjectMapperUtil.*;

/**
 * @author Sergei Visotsky
 */
@Service
public class ReservationService implements IReservationService<ReservationExtendedDTO, ReservationDTO> {

    private final CustomerRepository customerRepository;
    private final RouteRepository routeRepository;
    private final ReservationRepository reservationRepository;
    private final ServiceComponent serviceComponent;

    @Autowired
    public ReservationService(CustomerRepository customerRepository, RouteRepository routeRepository,
                              ReservationRepository reservationRepository, ServiceComponent serviceComponent) {
        this.customerRepository = customerRepository;
        this.routeRepository = routeRepository;
        this.reservationRepository = reservationRepository;
        this.serviceComponent = serviceComponent;
    }

    /**
     * Method to get flight reservation for the customer by reservation ID
     *
     * @param customerId    gets customer ID as a parameter
     * @param reservationId gets flight reservation ID as a parameter
     * @return Flight reservation extended DTO
     */
    @Override
    public ReservationExtendedDTO findOneForCustomer(Long customerId, Long reservationId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() ->
                new ResourceNotFoundException(Constants.CUSTOMER_NOT_FOUND)
        );

        Reservation reservation = reservationRepository.findOneForCustomer(customerId, reservationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(Constants.RESERVATION_NOT_FOUND)
                );
        ReservationExtendedDTO reservationExtendedDTO = map(reservation, ReservationExtendedDTO.class);
        reservationExtendedDTO.setCustomerId(customer.getCustomerId());

        // Find route by ID
        Route route = routeRepository.findById(reservation.getRoute().getRouteId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(Constants.ROUTE_NOT_FOUND)
                );

        reservationExtendedDTO.setRouteExtendedDTO(
                serviceComponent.setExtendedRoute(route)
        );

        return reservationExtendedDTO;
    }

    /**
     * Method to get all flight reservations for the customer
     *
     * @param customerId gets customer ID
     * @return extended flight reservation DTO
     */
    @Override
    public List<ReservationExtendedDTO> findAllForCustomer(Long customerId) {
        // Find customer
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(Constants.CUSTOMER_NOT_FOUND)
                );

        // Find all flight reservation for the customer
        List<Reservation> reservation = reservationRepository.findAllForCustomer(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(Constants.RESERVATIONS_NOT_FOUND)
                );
        List<ReservationExtendedDTO> reservationExtendedDTOList = mapAll(reservation, ReservationExtendedDTO.class);
        int counter = 0;
        // For each DTO set customer ID, route extended DTO
        for (ReservationExtendedDTO reservationExtendedDTO : reservationExtendedDTOList) {
            // Set customer ID in DTO response
            reservationExtendedDTO.setCustomerId(customer.getCustomerId());

            // Find route by ID
            Route route = routeRepository.findById(reservation.get(counter).getRoute().getRouteId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(Constants.ROUTE_NOT_FOUND)
                    );

            reservationExtendedDTO.setRouteExtendedDTO(
                    serviceComponent.setExtendedRoute(route)
            );
            counter++;
        }

        // Returns extended flight reservation DTO
        return reservationExtendedDTOList;
    }


    /**
     * Find all reservation for customer paginated
     *
     * @param page page number
     * @param size number of elements ont he page
     * @return list of found entities
     */
    @Override
    public Page<ReservationExtendedDTO> findAllForCustomerPaginated(Long customerId, int page, int size) {
        // Find customer
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(Constants.CUSTOMER_NOT_FOUND)
                );

        // Find all flight reservation for the customer
        Page<Reservation> reservation = reservationRepository.findAllForCustomerPaginated(customerId, PageRequest.of(page, size))
                .orElseThrow(() ->
                        new ResourceNotFoundException(Constants.RESERVATIONS_NOT_FOUND)
                );
        Page<ReservationExtendedDTO> flightReservationExtendedDTOList =
                mapAllPages(reservation, ReservationExtendedDTO.class);
        int counter = 0;
        // For each DTO set customer ID, route extended DTO
        for (ReservationExtendedDTO reservationExtendedDTO : flightReservationExtendedDTOList) {
            // Set customer ID in DTO response
            reservationExtendedDTO.setCustomerId(customer.getCustomerId());

            // Find route by ID
            Route route = routeRepository.findById(reservation.getContent().get(counter).getRoute().getRouteId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(Constants.ROUTE_NOT_FOUND)
                    );

            reservationExtendedDTO.setRouteExtendedDTO(
                    serviceComponent.setExtendedRoute(route)
            );
            counter++;
        }

        // Returns extended flight reservation DTO
        return flightReservationExtendedDTOList;
    }

    /**
     * Method to save reservation for customer
     *
     * @param customerId     Gets customer ID
     * @param reservationDTO get flight reservation DTO from controller
     * @return flight reservation DTO as a response
     */
    @Override
    public ReservationDTO saveReservation(Long customerId,
                                          ReservationDTO reservationDTO) {
        // Find customer by ID
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(Constants.CUSTOMER_NOT_FOUND)
                );

        // Find route by route ID
        Route route = routeRepository.findById(reservationDTO.getRouteId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(Constants.ROUTE_NOT_FOUND)
                );

        final Long customerEntityId = customer.getCustomerId();
        final Long routeEntityId = route.getRouteId();

        // Set customer ID and route into the flight reservation DTO
        reservationDTO.setCustomerId(customerEntityId);
        reservationDTO.setRouteId(routeEntityId);

        Reservation reservation = map(reservationDTO, Reservation.class);
        reservation.setCustomer(customer);
        reservation.setRoute(route);

        Reservation savedReservation = reservationRepository.save(reservation);
        ReservationDTO savedReservationDTO = map(savedReservation, ReservationDTO.class);
        savedReservationDTO.setCustomerId(customerEntityId);
        savedReservationDTO.setRouteId(routeEntityId);

        return savedReservationDTO;
    }

    /**
     * Update reservation details
     *
     * @param customerId    customer who made reservation
     * @param reservationId reservation ID to be patched
     * @param params        Field(-s) to be patched
     * @return patched reservation
     */
    @Override
    public ReservationDTO updateReservation(Long customerId, Long reservationId, Map<String, Object> params) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(Constants.CUSTOMER_NOT_FOUND)
                );
        Reservation reservation = reservationRepository.findOneForCustomer(customerId, reservationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer or reservation with this ID not found")
                );
        reservation.setCustomer(customer);
        if (params.get("routeId") != null) {
            reservation.setRoute(
                    routeRepository.findById(Long.valueOf(String.valueOf(params.get("routeId"))))
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(Constants.ROUTE_NOT_FOUND)
                            ));
        }
        if (params.get("reservationDate") != null) {
            reservation.setReservationDate(LocalDateTime.parse(String.valueOf(params.get("reservationDate"))));
        }
        ReservationDTO reservationDTO = map(reservationRepository.save(reservation), ReservationDTO.class);
        reservationDTO.setRouteId(reservation.getRoute().getRouteId());
        reservationDTO.setCustomerId(customer.getCustomerId());
        return reservationDTO;
    }


    /**
     * Delete reservation by ID and customer ID
     *
     * @param customerId    customer who made reservation
     * @param reservationId reservation ID to be deleted
     * @return deleted reservation DTO
     */
    @Override
    public ReservationExtendedDTO deleteReservation(Long customerId, Long reservationId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(Constants.CUSTOMER_NOT_FOUND)
                );
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(Constants.RESERVATION_NOT_FOUND)
                );
        reservationRepository.deleteByCustomerIdAndReservationId(customer, reservation);
        return map(reservation, ReservationExtendedDTO.class);
    }
}
