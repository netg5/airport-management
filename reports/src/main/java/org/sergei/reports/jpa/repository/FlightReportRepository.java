package org.sergei.reports.jpa.repository;

import org.sergei.reports.jpa.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Sergei Visotsky
 */
@Repository
public interface FlightReportRepository extends JpaRepository<Flight, Long> {

    @Query("SELECT f FROM Flight f" +
            " LEFT JOIN FETCH f.aircraft a" +
            " WHERE f.id = :flightId")
    Optional<Flight> makeFlightReportByFlightId(@Param("flightId") Long flightId);
}
