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

package org.sergei.reservation.service;

import org.sergei.reservation.jpa.model.Customer;
import org.sergei.reservation.jpa.model.Reservation;
import org.sergei.reservation.jpa.model.Route;
import org.sergei.reservation.jpa.repository.CustomerRepository;
import org.sergei.reservation.jpa.repository.ReservationRepository;
import org.sergei.reservation.jpa.repository.RouteRepository;
import org.sergei.reservation.rest.dto.ReservationRequestDTO;
import org.sergei.reservation.rest.dto.ReservationResponseDTO;
import org.sergei.reservation.rest.dto.RouteResponseDTO;
import org.sergei.reservation.rest.dto.mappers.ReservationDTOListMapper;
import org.sergei.reservation.rest.dto.mappers.ReservationDTOMapper;
import org.sergei.reservation.rest.dto.mappers.RouteDTOMapper;
import org.sergei.reservation.rest.dto.response.ResponseDTO;
import org.sergei.reservation.rest.exceptions.ResourceNotFoundException;
import org.sergei.reservation.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.sergei.reservation.utils.ObjectMapperUtil.map;
import static org.sergei.reservation.utils.ObjectMapperUtil.mapAllPages;

/**
 * @author Sergei Visotsky
 */
@Service
public class ReservationServiceImpl implements ReservationService {

    private final CustomerRepository customerRepository;
    private final RouteRepository routeRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationDTOMapper reservationDTOMapper;
    private final ReservationDTOListMapper reservationDTOListMapper;
    private final RouteDTOMapper routeDTOMapper;

    @Autowired
    public ReservationServiceImpl(CustomerRepository customerRepository,
                                  RouteRepository routeRepository,
                                  ReservationRepository reservationRepository,
                                  ReservationDTOMapper reservationDTOMapper,
                                  ReservationDTOListMapper reservationDTOListMapper,
                                  RouteDTOMapper routeDTOMapper) {
        this.customerRepository = customerRepository;
        this.routeRepository = routeRepository;
        this.reservationRepository = reservationRepository;
        this.reservationDTOMapper = reservationDTOMapper;
        this.reservationDTOListMapper = reservationDTOListMapper;
        this.routeDTOMapper = routeDTOMapper;
    }

    /**
     * Method to get flight reservation for the customer by reservation ID
     *
     * @param customerId    gets customer ID as a parameter
     * @param reservationId gets flight reservation ID as a parameter
     * @return Flight reservation extended DTO
     */
    @Override
    public ResponseEntity<ResponseDTO<ReservationResponseDTO>> findOneForCustomer(Long customerId, Long reservationId) {
        Optional<Customer> customer = customerRepository.findById(customerId);

        if (customer.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
        } else {
            Optional<Reservation> reservation = reservationRepository.findOneForCustomer(customerId, reservationId);
            if (reservation.isEmpty()) {
                return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
            } else {
                ReservationResponseDTO reservationResponseDTO = reservationDTOMapper.apply(reservation.get());
                // Find route by ID
                Optional<Route> route = routeRepository.findById(reservation.get().getRoute().getId());

                if (route.isEmpty()) {
                    return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
                } else {
                    RouteResponseDTO routeResponseDTO = routeDTOMapper.apply(route.get());
                    reservationResponseDTO.setRoutes(routeResponseDTO);

                    ResponseDTO<ReservationResponseDTO> response = new ResponseDTO<>();
                    response.setErrorList(List.of());
                    response.setResponse(List.of(reservationResponseDTO));

                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
            }
        }
    }

    /**
     * Method to get all flight reservations for the customer
     *
     * @param customerId gets customer ID
     * @return extended flight reservation DTO
     */
    @Override
    public ResponseEntity<ResponseDTO<ReservationResponseDTO>> findAllForCustomer(Long customerId) {
        // Find customer
        Optional<Customer> customer = customerRepository.findById(customerId);

        if (customer.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
        } else {
            // Find all flight reservation for the customer
            List<Reservation> reservation = reservationRepository.findAllForCustomer(customerId);
            if (reservation.isEmpty()) {
                return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
            } else {
                List<ReservationResponseDTO> reservationResponseList = reservationDTOListMapper.apply(reservation.get());
                int counter = 0;
                // For each DTO set customer ID, route extended DTO
                for (ReservationResponseDTO reservationResponseDTO : reservationResponseList) {
                    // Set customer ID in DTO response
                    reservationResponseDTO.setCustomerId(customer.get().getId());

                    // Find route by ID
                    Optional<Route> route = routeRepository.findById(reservation.get().getRoute().getId());

                    if (route.isEmpty()) {
                        return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
                    } else {
                        RouteResponseDTO routeResponseDTO = routeDTOMapper.apply(route.get());
                        reservationResponseDTO.setRoutes(routeResponseDTO);
                    }

                    counter++;
                }
                // Returns extended flight reservation DTO
                return reservationResponseList;

            }
        }
    }


    /**
     * Find all reservation for customer paginated
     *
     * @param page page number
     * @param size number of elements ont he page
     * @return list of found entities
     */
    @Override
    public ResponseEntity<ResponseDTO<ReservationResponseDTO>> findAllForCustomerPaginated(Long customerId, int page, int size) {
        // Find customer
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(Constants.CUSTOMER_NOT_FOUND)
                );

        // Find all flight reservation for the customer
        Page<Reservation> reservation = reservationRepository.findAllForCustomerPaginated(customerId, PageRequest.of(page, size));
        Page<ReservationExtendedDTO> flightReservationExtendedDTOList =
                mapAllPages(reservation, ReservationExtendedDTO.class);
        int counter = 0;
        // For each DTO set customer ID, route extended DTO
        for (ReservationExtendedDTO reservationExtendedDTO : flightReservationExtendedDTOList) {
            // Set customer ID in DTO response
//            reservationExtendedDTO.setCustomerId(customer.getId());

            // Find route by ID
            Route route = routeRepository.findById(reservation.getContent().get(counter).getRoute().getId())
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
     * @param customerId             Gets customer ID
     * @param reservationResponseDTO get flight reservation DTO from controller
     * @return flight reservation DTO as a response
     */
    @Override
    public ResponseEntity<ResponseDTO<ReservationResponseDTO>> saveReservation(ReservationRequestDTO request) {
        // Find customer by ID
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(Constants.CUSTOMER_NOT_FOUND)
                );

        // Find route by route ID
        Route route = routeRepository.findById(reservationResponseDTO.getReservationId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(Constants.ROUTE_NOT_FOUND)
                );

        final Long customerEntityId = customer.getId();
        final Long routeEntityId = route.getId();

        // Set customer ID and route into the flight reservation DTO
        reservationResponseDTO.setCustomerId(customerEntityId);
        reservationResponseDTO.setRouteId(routeEntityId);

        Reservation reservation = map(reservationResponseDTO, Reservation.class);
        reservation.setCustomer(customer);
        reservation.setRoute(route);

        Reservation savedReservation = reservationRepository.save(reservation);
        ReservationResponseDTO savedReservationResponseDTO = map(savedReservation, ReservationResponseDTO.class);
        savedReservationResponseDTO.setCustomerId(customerEntityId);
        savedReservationResponseDTO.setRouteId(routeEntityId);

        return savedReservationResponseDTO;
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
    public ResponseEntity<ResponseDTO<ReservationResponseDTO>> updateReservation(Long customerId,
                                                                                 Long reservationId, Map<String, Object> params) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(Constants.CUSTOMER_NOT_FOUND)
                );
        Reservation reservation = reservationRepository.findOneForCustomer(customerId, reservationId)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.RESERVATION_NOT_FOUND));
        reservation.setCustomer(customer);
        if (params.get("id") != null) {
            reservation.setRoute(
                    routeRepository.findById(Long.valueOf(String.valueOf(params.get("id"))))
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(Constants.ROUTE_NOT_FOUND)
                            ));
        }
        if (params.get("reservationDate") != null) {
            reservation.setReservationDate(LocalDateTime.parse(String.valueOf(params.get("reservationDate"))));
        }
        ReservationResponseDTO reservationResponseDTO = map(reservationRepository.save(reservation), ReservationResponseDTO.class);
        reservationResponseDTO.setRouteId(reservation.getRoute().getId());
        reservationResponseDTO.setCustomerId(customer.getId());
        return reservationResponseDTO;
    }


    /**
     * Delete reservation by ID and customer ID
     *
     * @param customerId    customer who made reservation
     * @param reservationId reservation ID to be deleted
     * @return deleted reservation DTO
     */
    @Override
    public ResponseEntity<ResponseDTO<ReservationResponseDTO>> deleteReservation(Long customerId, Long reservationId) {
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
