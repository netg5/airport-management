package org.sergei.flightservice.repository;

import org.sergei.flightservice.model.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Sergei Visotsky
 */
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    /**
     * Find reservation for customer by ID
     *
     * @param customerId    whose reservation should be found
     * @param reservationId reservation which should be found
     * @return list of reservations
     */
    @Query("SELECT f FROM Reservation f WHERE f.customer.customerId = :customerId AND f.reservationId = :reservationId")
    Optional<Reservation> findOneForCustomer(@Param("customerId") Long customerId,
                                             @Param("reservationId") Long reservationId);

    /**
     * Find all reservations by customer ID
     *
     * @param customerId whose reservation should be found
     * @return list of reservations
     */
    @Query("SELECT f FROM Reservation f WHERE f.customer.customerId = :customerId")
    Optional<List<Reservation>> findAllForCustomer(@Param("customerId") Long customerId);

    /**
     * Find all reservations by customer ID paginated
     *
     * @param customerId whose reservations should be found
     * @param pageable   page size and record quantity per page
     * @return list of reservations
     */
    @Query("SELECT f FROM Reservation f WHERE f.customer.customerId = :customerId")
    Optional<Page<Reservation>> findAllForCustomerPaginated(@Param("customerId") Long customerId, Pageable pageable);
}
