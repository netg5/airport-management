package org.sergei.reservationservice.repository;

import org.sergei.reservationservice.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Sergei Visotsky
 */
@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
}