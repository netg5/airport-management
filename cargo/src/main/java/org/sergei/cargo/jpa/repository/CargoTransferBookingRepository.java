package org.sergei.cargo.jpa.repository;

import org.sergei.cargo.jpa.model.CargoTransferBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Sergei Visotsky
 */
@Repository
public interface CargoTransferBookingRepository extends JpaRepository<CargoTransferBooking, Long> {
}
