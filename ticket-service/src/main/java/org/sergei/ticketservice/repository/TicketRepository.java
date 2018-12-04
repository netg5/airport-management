package org.sergei.ticketservice.repository;

import org.sergei.ticketservice.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Sergei Visotsky
 * @since 11/27/2018
 */
@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findAllByCustomerId(Long customerId);

    @Query("SELECT tv FROM Ticket tv WHERE tv.customerId = ?1 AND tv.place = ?2 OR tv.distance = ?3")
    Optional<Ticket> findByCustomerIdPlace(Long customerId, String place, Double distance);
}
