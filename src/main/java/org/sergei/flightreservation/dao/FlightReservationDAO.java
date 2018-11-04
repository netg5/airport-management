/*
 * Copyright (c) 2018 Sergei Visotsky
 */

package org.sergei.flightreservation.dao;

import org.sergei.flightreservation.dao.generic.AbstractJpaDAO;
import org.sergei.flightreservation.model.FlightReservation;
import org.springframework.stereotype.Repository;

/**
 * @author Sergei Visotsky, 2018
 */
@Repository
public class FlightReservationDAO extends AbstractJpaDAO<FlightReservation> {
    public FlightReservationDAO() {
        setPersistentClass(FlightReservation.class);
    }
}
