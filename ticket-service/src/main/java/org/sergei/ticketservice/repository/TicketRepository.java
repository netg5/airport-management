package org.sergei.ticketservice.repository;

import org.sergei.ticketservice.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Sergei Visotsky, 2018
 */
@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query(value = "SELECT customer_id, first_name, last_name, route_id, place, distance, price, aircraft_name FROM ticket_view t WHERE t.customer_id = :customerId", nativeQuery = true)
    List<Ticket> findAllByCustomerId(@Param("customerId") Long customerId);
}
