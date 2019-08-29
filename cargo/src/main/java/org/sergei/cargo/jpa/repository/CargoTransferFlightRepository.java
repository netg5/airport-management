package org.sergei.cargo.jpa.repository;

import org.sergei.cargo.jpa.model.CargoTransferFlight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Sergei Visotsky
 */
@Repository
public interface CargoTransferFlightRepository extends JpaRepository<CargoTransferFlight, Long> {
}
