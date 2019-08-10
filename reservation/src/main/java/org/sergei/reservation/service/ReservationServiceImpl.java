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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.opentracing.Span;
import io.opentracing.Tracer;
import lombok.extern.slf4j.Slf4j;
import org.sergei.reservation.jpa.model.Passenger;
import org.sergei.reservation.jpa.model.Reservation;
import org.sergei.reservation.jpa.model.mapper.PassengerModelMapper;
import org.sergei.reservation.jpa.model.mapper.RouteModelMapper;
import org.sergei.reservation.jpa.repository.PassengerRepository;
import org.sergei.reservation.jpa.repository.ReservationRepository;
import org.sergei.reservation.rest.dto.PassengerDTO;
import org.sergei.reservation.rest.dto.ReservationDTO;
import org.sergei.reservation.rest.dto.RouteDTO;
import org.sergei.reservation.rest.dto.mappers.ReservationDTOListMapper;
import org.sergei.reservation.rest.dto.mappers.ReservationDTOMapper;
import org.sergei.reservation.rest.dto.request.ReservationDTORequest;
import org.sergei.reservation.rest.dto.response.ResponseDTO;
import org.sergei.reservation.rest.dto.response.ResponseErrorDTO;
import org.sergei.reservation.rest.dto.response.RouteDTOExchangeResponse;
import org.sergei.reservation.rest.exceptions.FlightRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * @author Sergei Visotsky
 */
@Slf4j
@Service
@Transactional
public class ReservationServiceImpl implements ReservationService {

    @Value("${manager.route-uri}")
    private String managerRouteUri;

    private final PassengerRepository passengerRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationDTOMapper reservationDTOMapper;
    private final ReservationDTOListMapper reservationDTOListMapper;
    private final RouteModelMapper routeModelMapper;
    private final PassengerModelMapper passengerModelMapper;
    private final ResponseMessageService responseMessageService;
    private final Tracer tracer;

    @Autowired
    public ReservationServiceImpl(PassengerRepository passengerRepository,
                                  ReservationRepository reservationRepository,
                                  ReservationDTOMapper reservationDTOMapper,
                                  ReservationDTOListMapper reservationDTOListMapper,
                                  RouteModelMapper routeModelMapper,
                                  PassengerModelMapper passengerModelMapper,
                                  ResponseMessageService responseMessageService,
                                  Tracer tracer) {
        this.passengerRepository = passengerRepository;
        this.reservationRepository = reservationRepository;
        this.reservationDTOMapper = reservationDTOMapper;
        this.reservationDTOListMapper = reservationDTOListMapper;
        this.routeModelMapper = routeModelMapper;
        this.passengerModelMapper = passengerModelMapper;
        this.responseMessageService = responseMessageService;
        this.tracer = tracer;
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
            List<ResponseErrorDTO> responseErrorList = responseMessageService.responseErrorListByCode("PAS-001");
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
    public ResponseEntity<ResponseDTO<ReservationDTO>> saveReservation(ReservationDTORequest request) {
        Long routeId = request.getRouteId();
        ResponseDTO<ReservationDTO> response = new ResponseDTO<>();
        try {
            RestTemplate restTemplate = new RestTemplate();

            Span span = tracer.buildSpan("restTemplate.getForEntity(managerRouteUri, String.class)").start();
            span.setTag("managerRouteUri", managerRouteUri);
            ResponseEntity<String> routeResponse = restTemplate.getForEntity(managerRouteUri + "/" + routeId, String.class);
            span.finish();

            if (routeResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
                List<ResponseErrorDTO> responseErrorList = responseMessageService.responseErrorListByCode("RT-001");
                return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
            } else {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());
                RouteDTOExchangeResponse routeDTOExchangeResponse = objectMapper.readValue(routeResponse.getBody(), RouteDTOExchangeResponse.class);
                RouteDTO routeDTO = routeDTOExchangeResponse.getResponse().get(0);

                PassengerDTO passengerDTO = request.getPassenger();

                Reservation reservation = Reservation.builder()
                        .departureTime(request.getDepartureTime())
                        .arrivalTime(request.getArrivalTime())
                        .dateOfFlying(request.getDateOfFlying())
                        .hoursFlying(request.getHoursFlying())
                        .passenger(passengerModelMapper.apply(passengerDTO))
                        .route(routeModelMapper.apply(routeDTO))
                        .build();
                Reservation savedReservation = reservationRepository.save(reservation);

                ReservationDTO savedReservationDTO = reservationDTOMapper.apply(savedReservation);

                response.setErrorList(List.of());
                response.setResponse(List.of(savedReservationDTO));

            }
        } catch (IOException e) {
            throw new FlightRuntimeException(e);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Update reservation details
     *
     * @param reservationDTO Payload with all the necessary data
     * @return patched reservation
     */
    @Override
    public ResponseEntity<ResponseDTO<ReservationDTO>> updateReservation(ReservationDTO reservationDTO) {
        return null;
    }


    /**
     * Delete reservation by ID and passenger ID
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
