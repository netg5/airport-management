package org.sergei.ticketservice.service;

import org.sergei.ticketservice.exceptions.ResourceNotFoundException;
import org.sergei.ticketservice.model.Ticket;
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

    private static final String TICKETS_NOT_FOUND = "Customer has no tickets";

    private final TicketRepository ticketRepository;

    @Autowired
    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
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
//        ticketList.isEmpty() ? throw new ResourceNotFoundException(TICKETS_NOT_FOUND) : return null;
        if (ticketList.isEmpty()) {
            throw new ResourceNotFoundException(TICKETS_NOT_FOUND);
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
            throw new ResourceNotFoundException(TICKETS_NOT_FOUND);
        }
        return ticketList;
    }
}
