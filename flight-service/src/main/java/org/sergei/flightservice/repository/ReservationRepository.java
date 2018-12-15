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
 * @author Sergei Visotsky, 2018
 */
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT f FROM Reservation f WHERE f.customer.customerId = :customerId AND f.reservationId = :reservationId")
    Optional<Reservation> findOneForCustomer(@Param("customerId") Long customerId,
                                             @Param("reservationId") Long reservationId);

    @Query("SELECT f FROM Reservation f WHERE f.customer.customerId = :customerId")
    Optional<List<Reservation>> findAllForCustomer(@Param("customerId") Long customerId);

    @Query("SELECT f FROM Reservation f WHERE f.customer.customerId = :customerId")
    Optional<Page<Reservation>> findAllForCustomerPaginated(@Param("customerId") Long customerId, Pageable pageable);
}
