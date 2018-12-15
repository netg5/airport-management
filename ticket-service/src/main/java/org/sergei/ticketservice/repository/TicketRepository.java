package org.sergei.ticketservice.repository;

import org.sergei.ticketservice.model.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Sergei Visotsky
 * @since 11/27/2018
 */
@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("SELECT t FROM Ticket t WHERE t.customerId = ?1 " +
            "AND (?2 IS NULL OR t.place = ?2) " +
            "AND (?3 IS NULL OR t.distance = ?3)")
    List<Ticket> findByCustomerIdPlaceOrDistance(Long customerId, String place, Double distance);

    @Query("SELECT t FROM Ticket t WHERE t.customerId = ?1 " +
            "AND (?2 IS NULL OR t.place = ?2) " +
            "AND (?3 IS NULL OR t.distance = ?3)")
    Page<Ticket> findByCustomerIdPlaceOrDistancePageable(Long customerId, String place,
                                                         Double distance, Pageable pageable);
}
