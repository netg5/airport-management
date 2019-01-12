/*
 * Copyright 2018-2019 Sergei Visotsky
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

package org.sergei.ticketservice.service;

import org.sergei.ticketservice.exceptions.ResourceNotFoundException;
import org.sergei.ticketservice.model.Ticket;
import org.sergei.ticketservice.repository.CustomerRepository;
import org.sergei.ticketservice.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository, CustomerRepository customerRepository) {
        this.ticketRepository = ticketRepository;
        this.customerRepository = customerRepository;
    }

    /**
     * Method to find tickets for customer
     *
     * @param customerId whose tickets should be found
     * @param place      Where customer should fly
     * @param distance   Up to the point
     * @return collection of tickets
     */
    @Override
    public List<Ticket> findAllTickets(Long customerId, String place, Double distance) {
        List<Ticket> ticketList = ticketRepository.findAllTickets(customerId, place, distance);
        if (customerRepository.findById(customerId).isPresent()) {
            if (ticketList.isEmpty()) {
                throw new ResourceNotFoundException(Constants.TICKETS_NOT_FOUND);
            }
        } else {
            throw new ResourceNotFoundException(Constants.CUSTOMER_NOT_FOUND);
        }
        return ticketList;
    }

    /**
     * Method to find tickets for customer paginated
     *
     * @param customerId whose tickets should be found
     * @param place      Where customer should fly
     * @param distance   Up to the point
     * @param page       number of page to show
     * @param size       element quantity per page
     * @return Collection of tickets
     */
    @Override
    public Page<Ticket> findAllTicketsPageable(Long customerId, String place, Double distance, int page, int size) {
        Page<Ticket> ticketList = ticketRepository.findAllTicketsPageable(customerId, place, distance, PageRequest.of(page, size));
        if (ticketList.isEmpty()) {
            throw new ResourceNotFoundException(Constants.TICKETS_NOT_FOUND);
        }
        return ticketList;
    }
}
