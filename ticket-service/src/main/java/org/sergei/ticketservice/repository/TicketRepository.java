package org.sergei.ticketservice.repository;

import org.sergei.ticketservice.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Sergei Visotsky, 2018
 */
@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query(value = "SELECT * FROM ticket_view WHERE customer_id = :customerId", nativeQuery = true)
    Ticket findOneForCustomer(@Param("customerId") Long customerId);
}
