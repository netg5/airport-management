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

import org.sergei.reservation.jpa.model.Aircraft;
import org.sergei.reservation.jpa.model.Passenger;
import org.sergei.reservation.jpa.model.Reservation;
import org.sergei.reservation.jpa.repository.AircraftRepository;
import org.sergei.reservation.jpa.repository.PassengerRepository;
import org.sergei.reservation.jpa.repository.ReservationRepository;
import org.sergei.reservation.rest.dto.AircraftResponseDTO;
import org.sergei.reservation.rest.dto.ReservationRequestDTO;
import org.sergei.reservation.rest.dto.ReservationResponseDTO;
import org.sergei.reservation.rest.dto.mappers.AircraftDTOMapper;
import org.sergei.reservation.rest.dto.mappers.ReservationDTOListMapper;
import org.sergei.reservation.rest.dto.mappers.ReservationDTOMapper;
import org.sergei.reservation.rest.dto.response.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Sergei Visotsky
 */
@Service
public class ReservationServiceImpl implements ReservationService {

    private final PassengerRepository passengerRepository;
    private final AircraftRepository aircraftRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationDTOMapper reservationDTOMapper;
    private final ReservationDTOListMapper reservationDTOListMapper;
    private final AircraftDTOMapper aircraftDTOMapper;

    @Autowired
    public ReservationServiceImpl(PassengerRepository passengerRepository,
                                  AircraftRepository aircraftRepository,
                                  ReservationRepository reservationRepository,
                                  ReservationDTOMapper reservationDTOMapper,
                                  ReservationDTOListMapper reservationDTOListMapper,
                                  AircraftDTOMapper aircraftDTOMapper) {
        this.passengerRepository = passengerRepository;
        this.aircraftRepository = aircraftRepository;
        this.reservationRepository = reservationRepository;
        this.reservationDTOMapper = reservationDTOMapper;
        this.reservationDTOListMapper = reservationDTOListMapper;
        this.aircraftDTOMapper = aircraftDTOMapper;
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
        Optional<Passenger> customer = passengerRepository.findById(passengerId);

        if (customer.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
        } else {
            Optional<Reservation> reservation = reservationRepository.findOneForPassenger(passengerId, reservationId);
            if (reservation.isEmpty()) {
                return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
            } else {
                ReservationResponseDTO reservationResponseDTO = reservationDTOMapper.apply(reservation.get());
                // Find aircraftId by ID
                Optional<Aircraft> aircraft = aircraftRepository.findById(reservation.get().getAircraft().getId());

                if (aircraft.isEmpty()) {
                    return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
                } else {
                    AircraftResponseDTO aircraftResponseDTO = aircraftDTOMapper.apply(aircraft.get());
                    reservationResponseDTO.setAircraftId(aircraftResponseDTO.getAircraftId());

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
     * @param passengerId gets passenger ID
     * @return extended flight reservation DTO
     */
    @Override
    public ResponseEntity<ResponseDTO<ReservationResponseDTO>> findAllForPassenger(Long passengerId) {
        // Find passenger
        Optional<Passenger> passenger = passengerRepository.findById(passengerId);

        if (passenger.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
        } else {
            // Find all flight reservation for the passenger
            List<Reservation> reservation = reservationRepository.findAllForPassenger(passengerId);
            return populateReservationResponse(reservation, passenger.get());
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
    public ResponseEntity<ResponseDTO<ReservationResponseDTO>> findAllForPassengerPaginated(Long passengerId, int page, int size) {
        // Find passenger
        Optional<Passenger> passenger = passengerRepository.findById(passengerId);

        if (passenger.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
        } else {
            // Find all flight reservation for the passenger
            List<Reservation> reservation =
                    reservationRepository.findAllForPassengerPaginated(
                            passengerId, PageRequest.of(page, size)).getContent();
            return populateReservationResponse(reservation, passenger.get());
        }
    }

    /**
     * Method to save reservation for passenger
     *
     * @param passengerId who make reservation
     * @param request     to make reservation
     * @return flight reservation DTO as a response
     */
    @Override
    public ResponseEntity<ResponseDTO<ReservationResponseDTO>> saveReservation(Long passengerId, ReservationRequestDTO request) {
        // Find passenger by ID
        Optional<Passenger> passenger = passengerRepository.findById(passengerId);
        if (passenger.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
        } else {
            // Find aircraftId by aircraftId ID
            Optional<Aircraft> aircraft = aircraftRepository.findById(request.getRouteId());
            if (aircraft.isEmpty()) {
                return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
            } else {
                Reservation reservation = new Reservation();

                reservation.setPassenger(passenger.get());
                reservation.setAircraft(aircraft.get());
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
            return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
        } else {
            Optional<Reservation> reservation = reservationRepository.findOneForPassenger(passengerId, reservationId);
            if (reservation.isEmpty()) {
//                reservation.get().getAircraft(passenger);
//                if (params.get("id") != null) {
//                    reservation.setRoute(
//                            routeRepository.findById(Long.valueOf(String.valueOf(params.get("id"))))
//                                    .orElseThrow(() ->
//                                            new ResourceNotFoundException(Constants.ROUTE_NOT_FOUND)
//                                    ));
//                }
//                if (params.get("dateOfFlying") != null) {
//                    reservation.setReservationDate(LocalDateTime.parse(String.valueOf(params.get("dateOfFlying"))));
//                }
//                ReservationResponseDTO reservationResponseDTO = map(reservationRepository.save(reservation), ReservationResponseDTO.class);
//                reservationResponseDTO.setRouteId(reservation.getRoute().getCustomerId());
//                reservationResponseDTO.setPassengerId(passenger.getCustomerId());
//                return reservationResponseDTO;
//            }
            }
            return null;
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
        Optional<Passenger> customer = passengerRepository.findById(passengerId);

        if (customer.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
        } else {
            Optional<Reservation> reservation = reservationRepository.findById(reservationId);
            if (reservation.isEmpty()) {
                return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
            } else {
                reservationRepository.deleteByPassengerIdAndReservationId(customer.get(), reservation.get());
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
            return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
        } else {
            List<ReservationResponseDTO> reservationResponseList = reservationDTOListMapper.apply(reservations);
            int counter = 0;
            // For each DTO set passenger ID, route extended DTO
            for (ReservationResponseDTO reservationResponseDTO : reservationResponseList) {
                // Set passenger ID in DTO response
                reservationResponseDTO.setAircraftId(passenger.getId());
                // Find aircraft by ID
                Optional<Aircraft> aircraft = aircraftRepository.findById(reservations.get(counter).getAircraft().getId());

                if (aircraft.isEmpty()) {
                    return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
                } else {
                    AircraftResponseDTO aircraftResponseDTO = aircraftDTOMapper.apply(aircraft.get());
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
