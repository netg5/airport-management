package org.sergei.ticketservice.repository;

import org.sergei.ticketservice.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.NamedNativeQuery;
import java.util.List;

/**
 * @author Sergei Visotsky, 2018
 */
@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findAllByCustomerId(Long customerId);
}
