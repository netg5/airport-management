package org.sergei.ticketservice.service;

import org.sergei.ticketservice.model.Ticket;
import org.sergei.ticketservice.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Sergei Visotsky, 2018
 */
@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public Ticket findAllForCustomer(Long customerId) {
        return ticketRepository.findOneForCustomer(customerId);
    }

}
