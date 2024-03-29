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

package org.sergei.tickets.service;

import com.google.common.collect.ImmutableList;
import org.sergei.tickets.jpa.model.Passenger;
import org.sergei.tickets.jpa.model.Ticket;
import org.sergei.tickets.jpa.repository.PassengerRepository;
import org.sergei.tickets.jpa.repository.TicketRepository;
import org.sergei.tickets.rest.dto.TicketDTO;
import org.sergei.tickets.rest.dto.mappers.TicketDTOListMapper;
import org.sergei.tickets.rest.dto.response.ResponseDTO;
import org.sergei.tickets.rest.dto.response.ResponseErrorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Sergei Visotsky
 */
@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final PassengerRepository passengerRepository;
    private final TicketDTOListMapper ticketDTOListMapper;
    private final ResponseMessageService responseMessageService;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository,
                             PassengerRepository passengerRepository,
                             TicketDTOListMapper ticketDTOListMapper,
                             ResponseMessageService responseMessageService) {
        this.ticketRepository = ticketRepository;
        this.passengerRepository = passengerRepository;
        this.ticketDTOListMapper = ticketDTOListMapper;
        this.responseMessageService = responseMessageService;
    }

    /**
     * Method to find tickets for passenger
     *
     * @param passengerId Request payload with params to find tickets
     * @return collection of tickets
     */
    @Override
    public ResponseEntity<ResponseDTO<TicketDTO>> findAllTickets(Long passengerId, String currency) {
        Optional<Passenger> passenger = passengerRepository.findById(passengerId);
        if (passenger.isEmpty()) {
            List<ResponseErrorDTO> responseErrorList = responseMessageService.responseErrorListByCode("PAS-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, ImmutableList.of()), HttpStatus.NOT_FOUND);
        } else {
            List<Ticket> ticketList = ticketRepository.findAllTickets(passengerId, currency);
            if (ticketList.isEmpty()) {
                List<ResponseErrorDTO> responseErrorList = responseMessageService.responseErrorListByCode("PAS-001");
                return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, ImmutableList.of()), HttpStatus.NOT_FOUND);
            } else {
                List<TicketDTO> ticketDTOList = ticketDTOListMapper.apply(ticketList);
                ResponseDTO<TicketDTO> response = new ResponseDTO<>();
                response.setErrorList(ImmutableList.of());
                response.setResponse(ticketDTOList);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
    }
}
