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

import com.google.common.collect.ImmutableList;
import io.opentracing.Span;
import io.opentracing.Tracer;
import lombok.extern.slf4j.Slf4j;
import org.sergei.booking.feign.FlightFeignClient;
import org.sergei.booking.feign.FlyModeFeignClient;
import org.sergei.booking.jpa.model.Booking;
import org.sergei.booking.jpa.model.Passenger;
import org.sergei.booking.jpa.model.mapper.BookingModelMapper;
import org.sergei.booking.jpa.model.mapper.PassengerModelMapper;
import org.sergei.booking.jpa.repository.BookingRepository;
import org.sergei.booking.jpa.repository.PassengerRepository;
import org.sergei.booking.rest.dto.BookingDTO;
import org.sergei.booking.rest.dto.FlightDTO;
import org.sergei.booking.rest.dto.FlyModeDTO;
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
    private final FlightFeignClient flightFeignClient;
    private final FlyModeFeignClient flyModeFeignClient;
    private final Tracer tracer;

    @Autowired
    public BookingServiceImpl(PassengerRepository passengerRepository,
                              BookingRepository bookingRepository,
                              BookingDTOMapper bookingDTOMapper,
                              BookingDTOListMapper bookingDTOListMapper,
                              PassengerModelMapper passengerModelMapper,
                              BookingModelMapper bookingModelMapper,
                              ResponseMessageService responseMessageService,
                              FlightFeignClient flightFeignClient,
                              FlyModeFeignClient flyModeFeignClient,
                              Tracer tracer) {
        this.passengerRepository = passengerRepository;
        this.bookingRepository = bookingRepository;
        this.bookingDTOMapper = bookingDTOMapper;
        this.bookingDTOListMapper = bookingDTOListMapper;
        this.passengerModelMapper = passengerModelMapper;
        this.bookingModelMapper = bookingModelMapper;
        this.responseMessageService = responseMessageService;
        this.flightFeignClient = flightFeignClient;
        this.flyModeFeignClient = flyModeFeignClient;
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
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, ImmutableList.of()), HttpStatus.NOT_FOUND);
        } else {
            List<BookingDTO> bookingDTOList = bookingDTOListMapper.apply(bookingList);

            ResponseDTO<BookingDTO> response = new ResponseDTO<>();
            response.setErrorList(ImmutableList.of());
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
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, ImmutableList.of()), HttpStatus.NOT_FOUND);
        } else {
            // Find all flight booking for the passenger
            List<Booking> booking = bookingRepository.findAllForPassenger(passengerId);
            List<BookingDTO> bookingDTOList = bookingDTOListMapper.apply(booking);

            ResponseDTO<BookingDTO> response = new ResponseDTO<>();
            response.setErrorList(ImmutableList.of());
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
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, ImmutableList.of()), HttpStatus.NOT_FOUND);
        } else {
            if (bookingId == null) {
                List<ResponseErrorDTO> responseErrorList = responseMessageService.responseErrorListByCode("RES-001");
                return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, ImmutableList.of()), HttpStatus.NOT_FOUND);
            } else {
                Optional<Booking> booking = bookingRepository.findById(bookingId);
                if (booking.isEmpty()) {
                    List<ResponseErrorDTO> responseErrorList = responseMessageService.responseErrorListByCode("RES-001");
                    return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, ImmutableList.of()), HttpStatus.NOT_FOUND);
                } else {
                    BookingDTO bookingDTO = bookingDTOMapper.apply(booking.get());

                    ResponseDTO<BookingDTO> response = new ResponseDTO<>();
                    response.setErrorList(ImmutableList.of());
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

        ResponseEntity<ResponseDTO<FlightDTO>> flightResponse = flightFeignClient.getFlightById(flightId);
        ResponseEntity<ResponseDTO<FlyModeDTO>> flyModeResponse = flyModeFeignClient.getFlyModeByCode(request.getFlyModeCode());

        FlightDTO flightDTO = flightResponse.getBody().getResponse().get(0);
        FlyModeDTO flyModeDTO = flyModeResponse.getBody().getResponse().get(0);

        Booking booking = Booking.builder()
                .departureTime(request.getDepartureTime())
                .arrivalTime(request.getArrivalTime())
                .dateOfFlying(request.getDateOfFlying())
                .hoursFlying(request.getHoursFlying())
                .passenger(passengerModelMapper.apply(request.getPassenger()))
                .flightId(flightDTO.getFlightId())
                .flyModeCode(flyModeDTO.getCode())
                .build();
        Span span = tracer.buildSpan("bookingRepository.save()").start();
        span.setTag("booking", booking.toString());
        Booking savedBooking = bookingRepository.save(booking);
        span.finish();
        BookingDTO savedBookingDTO = bookingDTOMapper.apply(savedBooking);

        ResponseDTO<BookingDTO> response = new ResponseDTO<>();
        response.setErrorList(ImmutableList.of());
        response.setResponse(List.of(savedBookingDTO));

        return new ResponseEntity<>(response, HttpStatus.OK);
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
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, ImmutableList.of()), HttpStatus.NOT_FOUND);
        } else {
            Optional<Booking> booking = bookingRepository.findById(bookingId);
            if (booking.isEmpty()) {
                List<ResponseErrorDTO> responseErrorList = responseMessageService.responseErrorListByCode("RES-001");
                return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, ImmutableList.of()), HttpStatus.NOT_FOUND);
            } else {
                Booking savedBooking = bookingRepository.save(bookingModelMapper.apply(request));
                BookingDTO savedBookingDTO = bookingDTOMapper.apply(savedBooking);

                ResponseDTO<BookingDTO> response = new ResponseDTO<>();
                response.setErrorList(ImmutableList.of());
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
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, ImmutableList.of()), HttpStatus.NOT_FOUND);
        } else {
            Optional<Booking> booking = bookingRepository.findById(bookingId);
            if (booking.isEmpty()) {
                List<ResponseErrorDTO> responseErrorList = responseMessageService.responseErrorListByCode("RES-001");
                return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, ImmutableList.of()), HttpStatus.NOT_FOUND);
            } else {
                bookingRepository.discardByPassengerIdAndBookingId(passenger.get(), booking.get());
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }
    }
}
