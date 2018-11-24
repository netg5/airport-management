package org.sergei.flightservice.repository;

import org.sergei.flightservice.model.FlightReservation;
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
public interface FlightReservationRepository extends JpaRepository<FlightReservation, Long> {

    @Query("SELECT f FROM FlightReservation f WHERE f.customer.customerId = :customerId AND f.reservationId = :reservationId")
    Optional<FlightReservation> findOneForCustomer(@Param("customerId") Long customerId,
                                                   @Param("reservationId") Long reservationId);

    @Query("SELECT f FROM FlightReservation f WHERE f.customer.customerId = :customerId")
    Optional<List<FlightReservation>> findAllForCustomer(@Param("customerId") Long customerId);

}
