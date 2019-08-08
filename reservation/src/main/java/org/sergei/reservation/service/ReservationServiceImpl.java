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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.opentracing.Tracer;
import lombok.extern.slf4j.Slf4j;
import org.sergei.reservation.jpa.model.Aircraft;
import org.sergei.reservation.jpa.model.Passenger;
import org.sergei.reservation.jpa.model.Reservation;
import org.sergei.reservation.jpa.repository.PassengerRepository;
import org.sergei.reservation.jpa.repository.ReservationRepository;
import org.sergei.reservation.rest.dto.*;
import org.sergei.reservation.rest.dto.mappers.AircraftDTOMapper;
import org.sergei.reservation.rest.dto.mappers.ReservationDTOListMapper;
import org.sergei.reservation.rest.dto.mappers.ReservationDTOMapper;
import org.sergei.reservation.rest.dto.response.ResponseDTO;
import org.sergei.reservation.rest.dto.response.ResponseErrorDTO;
import org.sergei.reservation.rest.exceptions.FlightRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Sergei Visotsky
 */
@Slf4j
@Service
public class ReservationServiceImpl implements ReservationService {

    @Value("${manager.aircraft-uri}")
    private String managerAircraftUri;

    private final PassengerRepository passengerRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationDTOMapper reservationDTOMapper;
    private final ReservationDTOListMapper reservationDTOListMapper;
    private final AircraftDTOMapper aircraftDTOMapper;
    private final ResponseMessageService responseMessageService;
    private final Tracer tracer;

    @Autowired
    public ReservationServiceImpl(PassengerRepository passengerRepository,
                                  ReservationRepository reservationRepository,
                                  ReservationDTOMapper reservationDTOMapper,
                                  ReservationDTOListMapper reservationDTOListMapper,
                                  AircraftDTOMapper aircraftDTOMapper,
                                  ResponseMessageService responseMessageService,
                                  Tracer tracer) {
        this.passengerRepository = passengerRepository;
        this.reservationRepository = reservationRepository;
        this.reservationDTOMapper = reservationDTOMapper;
        this.reservationDTOListMapper = reservationDTOListMapper;
        this.aircraftDTOMapper = aircraftDTOMapper;
        this.responseMessageService = responseMessageService;
        this.tracer = tracer;
    }

    /**
     * Method to get flight reservation for the passenger by reservation ID
     *
     * @param passengerId   gets passenger ID as a parameter
     * @param reservationId gets flight reservation ID as a parameter
     * @return Flight reservation extended DTO
     */
    @Override
    public ResponseEntity<ResponseDTO<ReservationResponseDTO>> findOneForPassenger(Long passengerId, Long reservationId) {
        Optional<Passenger> passenger = passengerRepository.findById(passengerId);

        try {
            if (passenger.isEmpty()) {
                List<ResponseErrorDTO> responseErrorList = responseMessageService.responseErrorListByCode("PAS-001");
                return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
            } else {
                Optional<Reservation> reservation = reservationRepository.findOneForPassenger(passengerId, reservationId);
                if (reservation.isEmpty()) {
                    List<ResponseErrorDTO> responseErrorList = responseMessageService.responseErrorListByCode("RES-001");
                    return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
                } else {
                    ReservationResponseDTO reservationResponseDTO = reservationDTOMapper.apply(reservation.get());
                    // Find aircraftId by ID
//                Optional<Aircraft> aircraft = aircraftRepository.findById(reservation.get().getAircraft().getId());

                    RestTemplate restTemplate = new RestTemplate();
                    ResponseEntity<String> responseEntity = restTemplate.getForEntity(managerAircraftUri, String.class);

                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode rootNode = objectMapper.readTree(responseEntity.getBody());
                    AircraftDTO aircraftResponseDTO = AircraftDTO.builder()
                            .aircraftId(rootNode.path("aircraftId").asLong())
                            .registrationNumber(rootNode.path("registrationNumber").asText())
                            .modelNumber(rootNode.path("modelNumber").asText())
                            .aircraftName(rootNode.path("aircraftName").asText())
                            .capacity(rootNode.path("capacity").asInt())
                            .weight(rootNode.path("weight").asDouble())
                            .exploitationPeriod(rootNode.path("exploitationPeriod").asInt())
                            .hangar(HangarDTO.builder()
                                    .id(rootNode.get("hangarId").asLong())
                                    .capacity(rootNode.get("capacity").asInt())
                                    .hangarLocation(rootNode.get("location").asText())
                                    .hangarNumber(rootNode.get("hangarNumber").asText())
                                    .build())
                            .manufacturer(ManufacturerDTO.builder()
                                    .id(rootNode.get("manufacturerId").asLong())
                                    .location(rootNode.get("location").asText())
                                    .manufacturerCode(rootNode.get("manufacturerCode").asText())
                                    .manufacturerName(rootNode.get("manufacturerName").asText())
                                    .build())
                            .build();
                    if (responseEntity.getStatusCode().value() == 404) {
                        List<ResponseErrorDTO> responseErrorList = responseMessageService.responseErrorListByCode("AIR-001");
                        return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
                    } else {
//                        reservationResponseDTO.setAircraftId(aircraftResponseDTO.getAircraftId());

                        ResponseDTO<ReservationResponseDTO> response = new ResponseDTO<>();
                        response.setErrorList(List.of());
                        response.setResponse(List.of(reservationResponseDTO));

                        return new ResponseEntity<>(response, HttpStatus.OK);
                    }
                }

            }
        } catch (Exception e) {
            throw new FlightRuntimeException(e);
        }

    }

    /**
     * Method to get all flight reservations for the passenger
     *
     * @param passengerId gets passenger ID
     * @return extended flight reservation DTO
     */
    @Override
    public ResponseEntity<ResponseDTO<ReservationResponseDTO>> findAllForPassenger(Long passengerId) {
        // Find passenger
        Optional<Passenger> passenger = passengerRepository.findById(passengerId);

        if (passenger.isEmpty()) {
            List<ResponseErrorDTO> responseErrorList = responseMessageService.responseErrorListByCode("PAS-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
        } else {
            // Find all flight reservation for the passenger
            List<Reservation> reservation = reservationRepository.findAllForPassenger(passengerId);
            return populateReservationResponse(reservation, passenger.get());
        }
    }

    /**
     * Method to save reservation for passenger
     *
     * @param request to make reservation
     * @return flight reservation DTO as a response
     */
    @Override
    public ResponseEntity<ResponseDTO<ReservationResponseDTO>> saveReservation(ReservationDTO request) {
        // Find aircraftId by aircraftId ID
        Optional<Aircraft> aircraft = aircraftRepository.findById(request.getAircraftId());
        if (aircraft.isEmpty()) {
            List<ResponseErrorDTO> responseErrorList = responseMessageService.responseErrorListByCode("AIR-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
        } else {
            Reservation reservation = new Reservation();

            reservation.setPassenger(passenger.get());
            reservation.setRoute(aircraft.get());
            reservation.setDateOfFlying(request.getDateOfFlying());
            reservation.setDepartureTime(request.getDepartureTime());
            reservation.setArrivalTime(request.getArrivalTime());
            reservation.setHoursFlying(request.getHoursFlying());

            Reservation savedReservation = reservationRepository.save(reservation);
            ReservationResponseDTO savedReservationResponseDTO = reservationDTOMapper.apply(savedReservation);

            ResponseDTO<ReservationResponseDTO> response = new ResponseDTO<>();
            response.setErrorList(List.of());
            response.setResponse(List.of(savedReservationResponseDTO));

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    /**
     * Update reservation details
     *
     * @param passengerId   passenger who made reservation
     * @param reservationId reservation ID to be patched
     * @param params        Field(-s) to be patched
     * @return patched reservation
     */
    @Override
    public ResponseEntity<ResponseDTO<ReservationResponseDTO>> updateReservation(Long passengerId,
                                                                                 Long reservationId, Map<String, Object> params) {
        // Find passenger by ID
        Optional<Passenger> passenger = passengerRepository.findById(passengerId);
        if (passenger.isEmpty()) {
            List<ResponseErrorDTO> responseErrorList = responseMessageService.responseErrorListByCode("PAS-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
        } else {
            Optional<Reservation> reservation = reservationRepository.findOneForPassenger(passengerId, reservationId);
            if (reservation.isEmpty()) {
                List<ResponseErrorDTO> responseErrorList = responseMessageService.responseErrorListByCode("RES-001");
                return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
            } else {
                if (params.get("aircraftId") != null) {
                    Optional<Aircraft> aircraft = aircraftRepository.findById(Long.parseLong(String.valueOf(params.get("aircraftId"))));
                    if (aircraft.isEmpty()) {
                        return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
                    } else {
                        reservation.get().setRoute(aircraft.get());
                    }
                }
                if (params.get("departureTime") != null) {
                    reservation.get().setDepartureTime(LocalDateTime.parse(String.valueOf(params.get("departureTime"))));
                }
                if (params.get("dateOfFlying") != null) {
                    reservation.get().setDateOfFlying(LocalDateTime.parse(String.valueOf(params.get("dateOfFlying"))));
                }
                if (params.get("hoursFlying") != null) {
                    reservation.get().setHoursFlying(Integer.parseInt(String.valueOf(params.get("hoursFlying"))));
                }
                Reservation savedReservation = reservationRepository.save(reservation.get());
                ReservationResponseDTO reservationResponseDTO = reservationDTOMapper.apply(savedReservation);

                ResponseDTO<ReservationResponseDTO> response = new ResponseDTO<>();
                response.setErrorList(List.of());
                response.setResponse(List.of(reservationResponseDTO));

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
    }


    /**
     * Delete reservation by ID and passenger ID
     *
     * @param passengerId   passenger who made reservation
     * @param reservationId reservation ID to be deleted
     * @return response entity
     */
    @Override
    public ResponseEntity<ResponseDTO<ReservationResponseDTO>> deleteReservation(Long passengerId, Long reservationId) {
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
                reservationRepository.deleteByPassengerIdAndReservationId(passenger.get(), reservation.get());
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }
    }

    /**
     * Helper method to populate reservations response and perform more functionality
     *
     * @param reservations collection os reservations
     * @param passenger    who made reservation
     * @return response entity
     */
    private ResponseEntity<ResponseDTO<ReservationResponseDTO>>
    populateReservationResponse(List<Reservation> reservations, Passenger passenger) {
        if (reservations.isEmpty()) {
            List<ResponseErrorDTO> responseErrorList = responseMessageService.responseErrorListByCode("RES-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
        } else {
            List<ReservationResponseDTO> reservationResponseList = reservationDTOListMapper.apply(reservations);
            int counter = 0;
            // For each DTO set passenger ID, route extended DTO
            for (ReservationResponseDTO reservationResponseDTO : reservationResponseList) {
                // Set passenger ID in DTO response
                reservationResponseDTO.setAircraftId(passenger.getId());
                // Find aircraft by ID
                Optional<Aircraft> aircraft = aircraftRepository.findById(reservations.get(counter).getRoute().getId());

                if (aircraft.isEmpty()) {
                    List<ResponseErrorDTO> responseErrorList = responseMessageService.responseErrorListByCode("AIR-001");
                    return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
                } else {
                    AircraftDTO aircraftResponseDTO = aircraftDTOMapper.apply(aircraft.get());
                    reservationResponseDTO.setAircraftId(aircraftResponseDTO.getAircraftId());
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
