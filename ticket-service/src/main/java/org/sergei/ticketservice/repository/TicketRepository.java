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

    /**
     * Find ticket by customer ID, place or/and distance
     *
     * @param customerId whose ticket should be found
     * @param place      by which ticket should be found for a customer
     * @param distance   distance by which ticket should be found
     * @return list of the found tickets
     */
    @Query("SELECT t FROM Ticket t WHERE t.customerId = ?1 " +
            "AND (?2 IS NULL OR t.place = ?2) " +
            "AND (?3 IS NULL OR t.distance = ?3)")
    List<Ticket> findByCustomerIdPlaceOrDistance(Long customerId, String place, Double distance);

    /**
     * Find ticket by customer ID, place or/and distance with pagination
     *
     * @param customerId whose ticket should be found
     * @param place      by which ticket should be found for a customer
     * @param distance   distance by which ticket should be found
     * @param pageable   page number and element quantity per page
     * @return list of found tickets
     */
    @Query("SELECT t FROM Ticket t WHERE t.customerId = ?1 " +
            "AND (?2 IS NULL OR t.place = ?2) " +
            "AND (?3 IS NULL OR t.distance = ?3)")
    Page<Ticket> findByCustomerIdPlaceOrDistancePageable(Long customerId, String place,
                                                         Double distance, Pageable pageable);
}
