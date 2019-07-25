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

import org.sergei.tickets.jpa.model.Ticket;
import org.sergei.tickets.jpa.repository.CustomerRepository;
import org.sergei.tickets.jpa.repository.TicketRepository;
import org.sergei.tickets.rest.dto.TicketDTO;
import org.sergei.tickets.rest.dto.TicketRequestDTO;
import org.sergei.tickets.rest.dto.mappers.TicketDTOListMapper;
import org.sergei.tickets.rest.dto.response.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final CustomerRepository customerRepository;
    private final TicketDTOListMapper ticketDTOListMapper;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository,
                             CustomerRepository customerRepository,
                             TicketDTOListMapper ticketDTOListMapper) {
        this.ticketRepository = ticketRepository;
        this.customerRepository = customerRepository;
        this.ticketDTOListMapper = ticketDTOListMapper;
    }

    /**
     * Method to find tickets for customer
     *
     * @param request Request payload with params to find tickets
     * @return collection of tickets
     */
    @Override
    public ResponseEntity<ResponseDTO<TicketDTO>> findAllTickets(TicketRequestDTO request) {
        Long customerId = request.getCustomerId();
        String place = request.getPlace();
        Double distance = request.getDistance();
        if (customerRepository.findById(customerId).isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
        } else {
            List<Ticket> ticketList = ticketRepository.findAllTickets(customerId, place, distance);
            if (ticketList.isEmpty()) {
                return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
            } else {
                List<TicketDTO> ticketDTOList = ticketDTOListMapper.apply(ticketList);
                ResponseDTO<TicketDTO> response = new ResponseDTO<>();
                response.setErrorList(List.of());
                response.setResponse(ticketDTOList);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
    }

    /**
     * Method to find tickets for customer paginated
     *
     * @param request request Request payload with params to find tickets
     * @param page    number of page to show
     * @param size    element quantity per page
     * @return Collection of tickets
     */
    @Override
    public ResponseEntity<ResponseDTO<TicketDTO>> findAllTicketsPageable(TicketRequestDTO request, int page, int size) {
        Long customerId = request.getCustomerId();
        String place = request.getPlace();
        Double distance = request.getDistance();
        if (customerRepository.findById(customerId).isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
        } else {
            Page<Ticket> ticketList = ticketRepository
                    .findAllTicketsPageable(customerId, place, distance, PageRequest.of(page, size));
            if (ticketList.isEmpty()) {
                return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
            } else {
                List<TicketDTO> ticketDTOList = ticketDTOListMapper.apply(ticketList.getContent());
                ResponseDTO<TicketDTO> response = new ResponseDTO<>();
                response.setErrorList(List.of());
                response.setResponse(ticketDTOList);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
    }
}
