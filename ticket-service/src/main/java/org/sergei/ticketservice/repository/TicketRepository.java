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

//    @Query("SELECT t FROM Ticket t WHERE t.customerId = :customerId")
    List<Ticket> findAllByCustomerId(Long customerId);
}
