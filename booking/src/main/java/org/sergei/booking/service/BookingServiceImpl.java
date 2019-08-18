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

package org.sergei.booking.service;

import io.opentracing.Span;
import io.opentracing.Tracer;
import lombok.extern.slf4j.Slf4j;
import org.sergei.booking.jpa.model.Booking;
import org.sergei.booking.jpa.model.Flight;
import org.sergei.booking.jpa.model.FlyMode;
import org.sergei.booking.jpa.model.Passenger;
import org.sergei.booking.jpa.model.mapper.BookingModelMapper;
import org.sergei.booking.jpa.model.mapper.PassengerModelMapper;
import org.sergei.booking.jpa.repository.BookingRepository;
import org.sergei.booking.jpa.repository.FlightRepository;
import org.sergei.booking.jpa.repository.FlyModeRepository;
import org.sergei.booking.jpa.repository.PassengerRepository;
import org.sergei.booking.rest.dto.BookingDTO;
import org.sergei.booking.rest.dto.PassengerDTO;
import org.sergei.booking.rest.dto.mappers.BookingDTOListMapper;
import org.sergei.booking.rest.dto.mappers.BookingDTOMapper;
import org.sergei.booking.rest.dto.request.BookingRequestDTO;
import org.sergei.booking.rest.dto.response.ResponseDTO;
import org.sergei.booking.rest.dto.response.ResponseErrorDTO;
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
public class BookingServiceImpl implements BookingService {

    private final PassengerRepository passengerRepository;
    private final BookingRepository bookingRepository;
    private final BookingDTOMapper bookingDTOMapper;
    private final BookingDTOListMapper bookingDTOListMapper;
    private final PassengerModelMapper passengerModelMapper;
    private final BookingModelMapper bookingModelMapper;
    private final ResponseMessageService responseMessageService;
    private final FlightRepository flightRepository;
    private final FlyModeRepository flyModeRepository;
    private final Tracer tracer;

    @Autowired
    public BookingServiceImpl(PassengerRepository passengerRepository,
                              BookingRepository bookingRepository,
                              BookingDTOMapper bookingDTOMapper,
                              BookingDTOListMapper bookingDTOListMapper,
                              PassengerModelMapper passengerModelMapper,
                              BookingModelMapper bookingModelMapper,
                              ResponseMessageService responseMessageService,
                              FlightRepository flightRepository,
                              FlyModeRepository flyModeRepository, Tracer tracer) {
        this.passengerRepository = passengerRepository;
        this.bookingRepository = bookingRepository;
        this.bookingDTOMapper = bookingDTOMapper;
        this.bookingDTOListMapper = bookingDTOListMapper;
        this.passengerModelMapper = passengerModelMapper;
        this.bookingModelMapper = bookingModelMapper;
        this.responseMessageService = responseMessageService;
        this.flightRepository = flightRepository;
        this.flyModeRepository = flyModeRepository;
        this.tracer = tracer;
    }

    /**
     * Get all existing bookings just only for FeignClients
     *
     * @return all existing bookings
     */
    @Override
    public ResponseEntity<ResponseDTO<BookingDTO>> findAll() {
        List<Booking> bookingList = bookingRepository.findAll();
        if (bookingList.isEmpty()) {
            List<ResponseErrorDTO> responseErrorList = responseMessageService.responseErrorListByCode("RES-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
        } else {
            List<BookingDTO> bookingDTOList = bookingDTOListMapper.apply(bookingList);

            ResponseDTO<BookingDTO> response = new ResponseDTO<>();
            response.setErrorList(List.of());
            response.setResponse(bookingDTOList);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    /**
     * Method to get all flight bookings for the passenger
     *
     * @param passengerId gets passenger ID
     * @return extended flight booking DTO
     */
    @Override
    public ResponseEntity<ResponseDTO<BookingDTO>> findAllForPassenger(Long passengerId) {
        // Find passenger
        Optional<Passenger> passenger = passengerRepository.findById(passengerId);
        if (passenger.isEmpty()) {
            List<ResponseErrorDTO> responseErrorList = responseMessageService.responseErrorListByCode("PAS-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
        } else {
            // Find all flight booking for the passenger
            List<Booking> booking = bookingRepository.findAllForPassenger(passengerId);
            List<BookingDTO> bookingDTOList = bookingDTOListMapper.apply(booking);

            ResponseDTO<BookingDTO> response = new ResponseDTO<>();
            response.setErrorList(List.of());
            response.setResponse(bookingDTOList);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    /**
     * Method to get flight booking for the passenger by booking ID
     *
     * @param passengerId gets passenger ID as a parameter
     * @param bookingId   gets flight booking ID as a parameter
     * @return Flight booking extended DTO
     */
    @Override
    public ResponseEntity<ResponseDTO<BookingDTO>> findOneForPassenger(Long passengerId, Long bookingId) {
        if (passengerId == null) {
            List<ResponseErrorDTO> responseErrorList = responseMessageService.responseErrorListByCode("RP-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
        } else {
            if (bookingId == null) {
                List<ResponseErrorDTO> responseErrorList = responseMessageService.responseErrorListByCode("RES-001");
                return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
            } else {
                Optional<Booking> booking = bookingRepository.findById(bookingId);
                if (booking.isEmpty()) {
                    List<ResponseErrorDTO> responseErrorList = responseMessageService.responseErrorListByCode("RES-001");
                    return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
                } else {
                    BookingDTO bookingDTO = bookingDTOMapper.apply(booking.get());

                    ResponseDTO<BookingDTO> response = new ResponseDTO<>();
                    response.setErrorList(List.of());
                    response.setResponse(List.of(bookingDTO));

                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
            }
        }
    }

    /**
     * Method to save booking for passenger
     *
     * @param request to make booking
     * @return flight booking DTO as a response
     */
    @Override
    public ResponseEntity<ResponseDTO<BookingDTO>> saveBooking(BookingRequestDTO request) {
        Long flightId = request.getFlightId();
        Optional<Flight> flight = flightRepository.findById(flightId);
        Optional<FlyMode> flyMode = flyModeRepository.findFlyModeByCode(request.getFlyModeCode());

        if (flight.isEmpty()) {
            List<ResponseErrorDTO> responseErrorList = responseMessageService.responseErrorListByCode("RT-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
        } else if (flyMode.isEmpty()) {
            List<ResponseErrorDTO> responseErrorList = responseMessageService.responseErrorListByCode("FLY_001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
        } else {
            PassengerDTO passengerDTO = request.getPassenger();
            Booking booking = Booking.builder()
                    .departureTime(request.getDepartureTime())
                    .arrivalTime(request.getArrivalTime())
                    .dateOfFlying(request.getDateOfFlying())
                    .hoursFlying(request.getHoursFlying())
                    .passenger(passengerModelMapper.apply(passengerDTO))
                    .flight(flight.get())
                    .flyMode(flyMode.get())
                    .build();
            Span span = tracer.buildSpan("bookingRepository.save()").start();
            span.setTag("booking", booking.toString());
            Booking savedBooking = bookingRepository.save(booking);
            span.finish();
            BookingDTO savedBookingDTO = bookingDTOMapper.apply(savedBooking);

            ResponseDTO<BookingDTO> response = new ResponseDTO<>();
            response.setErrorList(List.of());
            response.setResponse(List.of(savedBookingDTO));

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    /**
     * Update booking details
     *
     * @param request Payload with all the necessary data
     * @return patched booking
     */
    @Override
    public ResponseEntity<ResponseDTO<BookingDTO>> updateBooking(BookingDTO request) {
        Long bookingId = request.getBookingId();
        if (bookingId == null) {
            List<ResponseErrorDTO> responseErrorList = responseMessageService.responseErrorListByCode("RP-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
        } else {
            Optional<Booking> booking = bookingRepository.findById(bookingId);
            if (booking.isEmpty()) {
                List<ResponseErrorDTO> responseErrorList = responseMessageService.responseErrorListByCode("RES-001");
                return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
            } else {
                Booking savedBooking = bookingRepository.save(bookingModelMapper.apply(request));
                BookingDTO savedBookingDTO = bookingDTOMapper.apply(savedBooking);

                ResponseDTO<BookingDTO> response = new ResponseDTO<>();
                response.setErrorList(List.of());
                response.setResponse(List.of(savedBookingDTO));

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
    }


    /**
     * Discard booking by its ID and passenger ID
     *
     * @param passengerId passenger who made booking
     * @param bookingId   booking ID to be deleted
     * @return response entity
     */
    @Override
    public ResponseEntity<ResponseDTO<BookingDTO>> discardBooking(Long passengerId, Long bookingId) {
        Optional<Passenger> passenger = passengerRepository.findById(passengerId);

        if (passenger.isEmpty()) {
            List<ResponseErrorDTO> responseErrorList = responseMessageService.responseErrorListByCode("PAS-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
        } else {
            Optional<Booking> booking = bookingRepository.findById(bookingId);
            if (booking.isEmpty()) {
                List<ResponseErrorDTO> responseErrorList = responseMessageService.responseErrorListByCode("RES-001");
                return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
            } else {
                bookingRepository.discardByPassengerIdAndBookingId(passenger.get(), booking.get());
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }
    }
}
