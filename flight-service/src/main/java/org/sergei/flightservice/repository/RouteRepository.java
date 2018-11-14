package org.sergei.flightservice.repository;

import org.sergei.flightservice.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Sergei Visotsky, 2018
 */
@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
}
