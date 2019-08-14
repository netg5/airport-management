package org.sergei.processor.jdbc.dao;

import org.sergei.processor.jdbc.model.Aircraft;
import org.sergei.processor.jdbc.model.Reservation;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Repository
public interface ActualFlightDAO {

    List<Reservation> findAll();

    Long getAvailableAircraft();

    Long getAvailableRoute();
}
