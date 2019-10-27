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

package org.sergei.manager.service;

import com.google.common.collect.ImmutableList;
import io.opentracing.Span;
import io.opentracing.Tracer;
import lombok.extern.slf4j.Slf4j;
import org.sergei.manager.jpa.model.Flight;
import org.sergei.manager.jpa.model.mappers.FlightModelMapper;
import org.sergei.manager.jpa.repository.FlightRepository;
import org.sergei.manager.rest.dto.FlightDTO;
import org.sergei.manager.rest.dto.mappers.FlightDTOMapper;
import org.sergei.manager.rest.dto.mappers.FlightListDTOMapper;
import org.sergei.manager.rest.dto.response.ResponseDTO;
import org.sergei.manager.rest.dto.response.ResponseErrorDTO;
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
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;
    private final FlightModelMapper flightModelMapper;
    private final FlightDTOMapper flightDTOMapper;
    private final FlightListDTOMapper flightDTOListMapper;
    private final MessageService messageService;
    private final Tracer tracer;

    @Autowired
    public FlightServiceImpl(FlightRepository flightRepository,
                             FlightModelMapper flightModelMapper,
                             FlightDTOMapper flightDTOMapper,
                             FlightListDTOMapper flightDTOListMapper,
                             MessageService messageService,
                             Tracer tracer) {
        this.flightRepository = flightRepository;
        this.flightModelMapper = flightModelMapper;
        this.flightDTOMapper = flightDTOMapper;
        this.flightDTOListMapper = flightDTOListMapper;
        this.messageService = messageService;
        this.tracer = tracer;
    }

    /**
     * Method to find one flight
     *
     * @param flightId as an input argument from controller
     * @return flight extended DTO
     */
    @Override
    public ResponseEntity<ResponseDTO<FlightDTO>> findOneFlight(Long flightId) {
        if (flightId == null) {
            List<ResponseErrorDTO> errorMessageList = messageService.responseErrorListByCode("RP-001");
            return new ResponseEntity<>(new ResponseDTO<>(errorMessageList, ImmutableList.of()), HttpStatus.NOT_FOUND);
        } else {
            Span span = tracer.buildSpan("flightRepository.findById() started").start();
            span.setTag("flightId", flightId);
            Optional<Flight> flight = flightRepository.findById(flightId);
            span.finish();
            if (flight.isEmpty()) {
                log.debug("Flight with ID: {} not found", flightId);
                List<ResponseErrorDTO> errorMessageList = messageService.responseErrorListByCode("RT-001");
                return new ResponseEntity<>(new ResponseDTO<>(errorMessageList, ImmutableList.of()), HttpStatus.NOT_FOUND);
            } else {
                FlightDTO flightDTO = flightDTOMapper.apply(flight.get());

                ResponseDTO<FlightDTO> response = new ResponseDTO<>();
                response.setErrorList(ImmutableList.of());
                response.setResponse(List.of(flightDTO));

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
    }

    /**
     * Find all flights
     *
     * @return list of flight extended DTO
     */
    @Override
    public ResponseEntity<ResponseDTO<FlightDTO>> findAllFlights() {
        List<Flight> flightList = flightRepository.findAll();

        if (flightList.isEmpty()) {
            List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("RT-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, ImmutableList.of()), HttpStatus.NOT_FOUND);
        } else {
            List<FlightDTO> flightDTOList = flightDTOListMapper.apply(flightList);

            ResponseDTO<FlightDTO> response = new ResponseDTO<>();
            response.setErrorList(ImmutableList.of());
            response.setResponse(flightDTOList);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    /**
     * Save flight
     *
     * @param request gets flight DTO as an input argument
     * @return flight DTO as a response
     */
    @Override
    public ResponseEntity<ResponseDTO<FlightDTO>> save(FlightDTO request) {
        Flight flight = flightModelMapper.apply(request);

        Flight savedFlight = flightRepository.save(flight);
        FlightDTO savedFlightDTO = flightDTOMapper.apply(savedFlight);

        ResponseDTO<FlightDTO> response = new ResponseDTO<>();
        response.setErrorList(ImmutableList.of());
        response.setResponse(List.of(savedFlightDTO));

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Update flight
     *
     * @param request
     * @return
     */
    @Override
    public ResponseEntity<ResponseDTO<FlightDTO>> update(FlightDTO request) {
        Long flightId = request.getFlightId();
        if (flightId == null) {
            List<ResponseErrorDTO> errorMessageList = messageService.responseErrorListByCode("RP-001");
            return new ResponseEntity<>(new ResponseDTO<>(errorMessageList, ImmutableList.of()), HttpStatus.NOT_FOUND);
        } else {
            Optional<Flight> flight = flightRepository.findById(flightId);
            if (flight.isEmpty()) {
                log.debug("Route with ID: {} not found", flightId);
                List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("RT-001");
                return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, ImmutableList.of()), HttpStatus.NOT_FOUND);
            } else {
                Flight savedFlight = flightRepository.save(flight.get());
                FlightDTO flightDTO = flightDTOMapper.apply(savedFlight);

                ResponseDTO<FlightDTO> response = new ResponseDTO<>();
                response.setErrorList(ImmutableList.of());
                response.setResponse(List.of(flightDTO));

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
    }

    /**
     * Delete flight by ID
     *
     * @param flightId gets flight ID as an input argument
     * @return Flight DTO asa response
     */
    @Override
    public ResponseEntity<ResponseDTO<FlightDTO>> delete(Long flightId) {
        Optional<Flight> flight = flightRepository.findById(flightId);
        if (flight.isEmpty()) {
            log.debug("Flight with ID: {} not found", flightId);
            List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("RT-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, ImmutableList.of()), HttpStatus.NOT_FOUND);
        } else {
            flightRepository.delete(flight.get());
            return new ResponseEntity<>(new ResponseDTO<>(ImmutableList.of(), ImmutableList.of()), HttpStatus.OK);
        }
    }
}
