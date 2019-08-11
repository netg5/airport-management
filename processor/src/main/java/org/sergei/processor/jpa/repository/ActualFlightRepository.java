package org.sergei.processor.jpa.repository;

import org.sergei.processor.jpa.model.ActualFlight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Sergei Visotsky
 */
@Repository
public interface ActualFlightRepository extends JpaRepository<ActualFlight, Long> {
}
