package org.sergei.processor.jdbc.repository;

import org.sergei.processor.jdbc.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Sergei Visotsky
 */
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
