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

import org.sergei.reservation.jpa.model.Passenger;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
     * Method to get flight reservation for the passenger by reservation ID
     *
     * @param customerId    gets passenger ID as a parameter
     * @param reservationId gets flight reservation ID as a parameter
     * @return Flight reservation extended DTO
     */
    @Override
    public ResponseEntity<ResponseDTO<ReservationResponseDTO>> findOneForCustomer(Long customerId, Long reservationId) {
        Optional<Passenger> customer = customerRepository.findById(customerId);

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
     * Method to get all flight reservations for the passenger
     *
     * @param customerId gets passenger ID
     * @return extended flight reservation DTO
     */
    @Override
    public ResponseEntity<ResponseDTO<ReservationResponseDTO>> findAllForCustomer(Long customerId) {
        // Find passenger
        Optional<Passenger> customer = customerRepository.findById(customerId);

        if (customer.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
        } else {
            // Find all flight reservation for the passenger
            List<Reservation> reservation = reservationRepository.findAllForCustomer(customerId);
            return populateReservationResponse(reservation, customer.get());
        }
    }


    /**
     * Find all reservation for passenger paginated
     *
     * @param page page number
     * @param size number of elements ont he page
     * @return list of found entities
     */
    @Override
    public ResponseEntity<ResponseDTO<ReservationResponseDTO>> findAllForCustomerPaginated(Long customerId, int page, int size) {
        // Find passenger
        Optional<Passenger> customer = customerRepository.findById(customerId);

        if (customer.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
        } else {
            // Find all flight reservation for the passenger
            List<Reservation> reservation =
                    reservationRepository.findAllForCustomerPaginated(
                            customerId, PageRequest.of(page, size)).getContent();
            return populateReservationResponse(reservation, customer.get());
        }
    }

    /**
     * Method to save reservation for passenger
     *
     * @param customerId who make reservation
     * @param request    to make reservation
     * @return flight reservation DTO as a response
     */
    @Override
    public ResponseEntity<ResponseDTO<ReservationResponseDTO>> saveReservation(Long customerId, ReservationRequestDTO request) {
        // Find passenger by ID
        Optional<Passenger> customer = customerRepository.findById(customerId);
        if (customer.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
        } else {
            // Find route by route ID
            Optional<Route> route = routeRepository.findById(request.getRouteId());
            if (route.isEmpty()) {
                return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
            } else {
                Reservation reservation = new Reservation();
                reservation.setPassenger(customer.get());
                reservation.setRoute(route.get());
                reservation.setReservationDate(request.getReservationDate());

                Reservation savedReservation = reservationRepository.save(reservation);
                ReservationResponseDTO savedReservationResponseDTO = reservationDTOMapper.apply(savedReservation);

                ResponseDTO<ReservationResponseDTO> response = new ResponseDTO<>();
                response.setErrorList(List.of());
                response.setResponse(List.of(savedReservationResponseDTO));

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
    }

    /**
     * Update reservation details
     *
     * @param customerId    passenger who made reservation
     * @param reservationId reservation ID to be patched
     * @param params        Field(-s) to be patched
     * @return patched reservation
     */
    @Override
    public ResponseEntity<ResponseDTO<ReservationResponseDTO>> updateReservation(Long customerId,
                                                                                 Long reservationId, Map<String, Object> params) {
        Passenger passenger = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(Constants.CUSTOMER_NOT_FOUND)
                );
        Reservation reservation = reservationRepository.findOneForCustomer(customerId, reservationId)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.RESERVATION_NOT_FOUND));
        reservation.setPassenger(passenger);
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
//        ReservationResponseDTO reservationResponseDTO = map(reservationRepository.save(reservation), ReservationResponseDTO.class);
//        reservationResponseDTO.setRouteId(reservation.getRoute().getId());
//        reservationResponseDTO.setPassengerId(passenger.getId());
//        return reservationResponseDTO;
        return null;
    }


    /**
     * Delete reservation by ID and passenger ID
     *
     * @param customerId    passenger who made reservation
     * @param reservationId reservation ID to be deleted
     * @return response entity
     */
    @Override
    public ResponseEntity<ResponseDTO<ReservationResponseDTO>> deleteReservation(Long customerId, Long reservationId) {
        Optional<Passenger> customer = customerRepository.findById(customerId);

        if (customer.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
        } else {
            Optional<Reservation> reservation = reservationRepository.findById(reservationId);
            if (reservation.isEmpty()) {
                return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
            } else {
                reservationRepository.deleteByCustomerIdAndReservationId(customer.get(), reservation.get());
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }
    }

    /**
     * Helper method to populate reservations response and perform more functionality
     *
     * @param reservations collection os reservations
     * @param passenger     who made reservation
     * @return response entity
     */
    private ResponseEntity<ResponseDTO<ReservationResponseDTO>>
    populateReservationResponse(List<Reservation> reservations, Passenger passenger) {
        if (reservations.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
        } else {
            List<ReservationResponseDTO> reservationResponseList = reservationDTOListMapper.apply(reservations);
            int counter = 0;
            // For each DTO set passenger ID, route extended DTO
            for (ReservationResponseDTO reservationResponseDTO : reservationResponseList) {
                // Set passenger ID in DTO response
                reservationResponseDTO.setCustomerId(passenger.getId());
                // Find route by ID
                Optional<Route> route = routeRepository.findById(reservations.get(counter).getRoute().getId());

                if (route.isEmpty()) {
                    return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
                } else {
                    RouteResponseDTO routeResponseDTO = routeDTOMapper.apply(route.get());
                    reservationResponseDTO.setRoutes(routeResponseDTO);
                }
                counter++;
            }

            ResponseDTO<ReservationResponseDTO> response = new ResponseDTO<>();
            response.setErrorList(List.of());
            response.setResponse(reservationResponseList);

            // Returns extended flight reservations DTO
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }
}
