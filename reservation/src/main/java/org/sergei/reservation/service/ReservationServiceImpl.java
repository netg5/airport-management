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

import io.opentracing.Span;
import io.opentracing.Tracer;
import lombok.extern.slf4j.Slf4j;
import org.sergei.reservation.jpa.model.Passenger;
import org.sergei.reservation.jpa.model.Reservation;
import org.sergei.reservation.jpa.model.Route;
import org.sergei.reservation.jpa.model.mapper.PassengerModelMapper;
import org.sergei.reservation.jpa.model.mapper.ReservationModelMapper;
import org.sergei.reservation.jpa.model.mapper.RouteModelMapper;
import org.sergei.reservation.jpa.repository.PassengerRepository;
import org.sergei.reservation.jpa.repository.ReservationRepository;
import org.sergei.reservation.jpa.repository.RouteRepository;
import org.sergei.reservation.rest.dto.PassengerDTO;
import org.sergei.reservation.rest.dto.ReservationDTO;
import org.sergei.reservation.rest.dto.RouteDTO;
import org.sergei.reservation.rest.dto.mappers.ReservationDTOListMapper;
import org.sergei.reservation.rest.dto.mappers.ReservationDTOMapper;
import org.sergei.reservation.rest.dto.mappers.RouteDTOMapper;
import org.sergei.reservation.rest.dto.request.ReservationRequestDTO;
import org.sergei.reservation.rest.dto.response.ResponseDTO;
import org.sergei.reservation.rest.dto.response.ResponseErrorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Sergei Visotsky
 */
@Slf4j
@Service
public class ReservationServiceImpl implements ReservationService {

    private final PassengerRepository passengerRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationDTOMapper reservationDTOMapper;
    private final ReservationDTOListMapper reservationDTOListMapper;
    private final RouteModelMapper routeModelMapper;
    private final PassengerModelMapper passengerModelMapper;
    private final ReservationModelMapper reservationModelMapper;
    private final ResponseMessageService responseMessageService;
    private final RouteRepository routeRepository;
    private final Tracer tracer;
    private final RouteDTOMapper routeDTOMapper;

    @Autowired
    public ReservationServiceImpl(PassengerRepository passengerRepository,
                                  ReservationRepository reservationRepository,
                                  ReservationDTOMapper reservationDTOMapper,
                                  ReservationDTOListMapper reservationDTOListMapper,
                                  RouteModelMapper routeModelMapper,
                                  PassengerModelMapper passengerModelMapper,
                                  ReservationModelMapper reservationModelMapper,
                                  ResponseMessageService responseMessageService,
                                  RouteRepository routeRepository, Tracer tracer,
                                  RouteDTOMapper routeDTOMapper) {
        this.passengerRepository = passengerRepository;
        this.reservationRepository = reservationRepository;
        this.reservationDTOMapper = reservationDTOMapper;
        this.reservationDTOListMapper = reservationDTOListMapper;
        this.routeModelMapper = routeModelMapper;
        this.passengerModelMapper = passengerModelMapper;
        this.reservationModelMapper = reservationModelMapper;
        this.responseMessageService = responseMessageService;
        this.routeRepository = routeRepository;
        this.tracer = tracer;
        this.routeDTOMapper = routeDTOMapper;
    }

    /**
     * Get all existing reservations just only for FeignClients
     *
     * @return all existing reservations
     */
    @Override
    public ResponseEntity<ResponseDTO<ReservationDTO>> findAll() {
        List<Reservation> reservationList = reservationRepository.findAll();
        if (reservationList.isEmpty()) {
            List<ResponseErrorDTO> responseErrorList = responseMessageService.responseErrorListByCode("RES-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
        } else {
            List<ReservationDTO> reservationDTOList = reservationDTOListMapper.apply(reservationList);

            ResponseDTO<ReservationDTO> response = new ResponseDTO<>();
            response.setErrorList(List.of());
            response.setResponse(reservationDTOList);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    /**
     * Method to get all flight reservations for the passenger
     *
     * @param passengerId gets passenger ID
     * @return extended flight reservation DTO
     */
    @Override
    public ResponseEntity<ResponseDTO<ReservationDTO>> findAllForPassenger(Long passengerId) {
        // Find passenger
        Optional<Passenger> passenger = passengerRepository.findById(passengerId);
        if (passenger.isEmpty()) {
            List<ResponseErrorDTO> responseErrorList = responseMessageService.responseErrorListByCode("PAS-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
        } else {
            // Find all flight reservation for the passenger
            List<Reservation> reservation = reservationRepository.findAllForPassenger(passengerId);
            List<ReservationDTO> reservationDTOList = reservationDTOListMapper.apply(reservation);

            ResponseDTO<ReservationDTO> response = new ResponseDTO<>();
            response.setErrorList(List.of());
            response.setResponse(reservationDTOList);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    /**
     * Method to get flight reservation for the passenger by reservation ID
     *
     * @param passengerId   gets passenger ID as a parameter
     * @param reservationId gets flight reservation ID as a parameter
     * @return Flight reservation extended DTO
     */
    @Override
    public ResponseEntity<ResponseDTO<ReservationDTO>> findOneForPassenger(Long passengerId, Long reservationId) {
        if (passengerId == null) {
            List<ResponseErrorDTO> responseErrorList = responseMessageService.responseErrorListByCode("RP-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
        } else {
            if (reservationId == null) {
                List<ResponseErrorDTO> responseErrorList = responseMessageService.responseErrorListByCode("RES-001");
                return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
            } else {
                Optional<Reservation> reservation = reservationRepository.findById(reservationId);
                if (reservation.isEmpty()) {
                    List<ResponseErrorDTO> responseErrorList = responseMessageService.responseErrorListByCode("RES-001");
                    return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
                } else {
                    ReservationDTO reservationDTO = reservationDTOMapper.apply(reservation.get());

                    ResponseDTO<ReservationDTO> response = new ResponseDTO<>();
                    response.setErrorList(List.of());
                    response.setResponse(List.of(reservationDTO));

                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
            }
        }
    }

    /**
     * Method to save reservation for passenger
     *
     * @param request to make reservation
     * @return flight reservation DTO as a response
     */
    @Override
    public ResponseEntity<ResponseDTO<ReservationDTO>> saveReservation(ReservationRequestDTO request) {
        Long routeId = request.getRouteId();
        Optional<Route> route = routeRepository.findById(routeId);
        if (route.isEmpty()) {
            List<ResponseErrorDTO> responseErrorList = responseMessageService.responseErrorListByCode("RT-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
        } else {
            RouteDTO routeDTO = routeDTOMapper.apply(route.get());
            PassengerDTO passengerDTO = request.getPassenger();

            Reservation reservation = Reservation.builder()
                    .departureTime(request.getDepartureTime())
                    .arrivalTime(request.getArrivalTime())
                    .dateOfFlying(request.getDateOfFlying())
                    .hoursFlying(request.getHoursFlying())
                    .passenger(passengerModelMapper.apply(passengerDTO))
                    .route(routeModelMapper.apply(routeDTO))
                    .build();
            Span span = tracer.buildSpan("reservationRepository.save()").start();
            span.setTag("reservation", reservation.toString());

            Reservation savedReservation = reservationRepository.save(reservation);

            span.finish();
            ReservationDTO savedReservationDTO = reservationDTOMapper.apply(savedReservation);

            ResponseDTO<ReservationDTO> response = new ResponseDTO<>();
            response.setErrorList(List.of());
            response.setResponse(List.of(savedReservationDTO));

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    /**
     * Update reservation details
     *
     * @param request Payload with all the necessary data
     * @return patched reservation
     */
    @Override
    public ResponseEntity<ResponseDTO<ReservationDTO>> updateReservation(ReservationDTO request) {
        Long reservationId = request.getReservationId();
        if (reservationId == null) {
            List<ResponseErrorDTO> responseErrorList = responseMessageService.responseErrorListByCode("RP-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
        } else {
            Optional<Reservation> reservation = reservationRepository.findById(reservationId);
            if (reservation.isEmpty()) {
                List<ResponseErrorDTO> responseErrorList = responseMessageService.responseErrorListByCode("RES-001");
                return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
            } else {
                Reservation savedReservation = reservationRepository.save(reservationModelMapper.apply(request));
                ReservationDTO savedReservationDTO = reservationDTOMapper.apply(savedReservation);

                ResponseDTO<ReservationDTO> response = new ResponseDTO<>();
                response.setErrorList(List.of());
                response.setResponse(List.of(savedReservationDTO));

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
    }


    /**
     * Discard reservation by its ID and passenger ID
     *
     * @param passengerId   passenger who made reservation
     * @param reservationId reservation ID to be deleted
     * @return response entity
     */
    @Override
    public ResponseEntity<ResponseDTO<ReservationDTO>> discardReservation(Long passengerId, Long reservationId) {
        Optional<Passenger> passenger = passengerRepository.findById(passengerId);

        if (passenger.isEmpty()) {
            List<ResponseErrorDTO> responseErrorList = responseMessageService.responseErrorListByCode("PAS-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
        } else {
            Optional<Reservation> reservation = reservationRepository.findById(reservationId);
            if (reservation.isEmpty()) {
                List<ResponseErrorDTO> responseErrorList = responseMessageService.responseErrorListByCode("RES-001");
                return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
            } else {
                reservationRepository.discardByPassengerIdAndReservationId(passenger.get(), reservation.get());
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }
    }
}
