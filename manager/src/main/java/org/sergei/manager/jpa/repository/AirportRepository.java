package org.sergei.manager.jpa.repository;

import org.sergei.manager.jpa.model.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Sergei Visotsky
 */
@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {

    @Query("SELECT a FROM Airport a WHERE a.airportName = :airportName")
    Optional<Airport> getAirportByName(@Param("airportName") String airportName);

}
