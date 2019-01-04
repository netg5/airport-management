package org.sergei.ticketservice.service;

import org.sergei.ticketservice.model.Ticket;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
public interface TicketService {

    /**
     * Method to find tickets for customer
     *
     * @param customerId whose tickets should be found
     * @param place      Where customer should fly
     * @param distance   Up to the point
     * @return collection of tickets
     */
    List<Ticket> findByCustomerIdPlaceOrDistance(Long customerId, String place, Double distance);

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
    Page<Ticket> findByCustomerIdPlaceOrDistancePageable(Long customerId, String place, Double distance, int page, int size);
}
