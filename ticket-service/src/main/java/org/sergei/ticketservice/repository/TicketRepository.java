package org.sergei.ticketservice.repository;

import org.sergei.ticketservice.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author Sergei Visotsky, 2018
 */
@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query(value = "", nativeQuery = true)
    Ticket findOneForCustomer(Long customerId);
}
