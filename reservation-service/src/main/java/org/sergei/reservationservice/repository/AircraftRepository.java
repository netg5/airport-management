package org.sergei.reservationservice.repository;

import org.sergei.reservationservice.model.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Sergei Visotsky
 */
@Repository
public interface AircraftRepository extends JpaRepository<Aircraft, Long> {
}
