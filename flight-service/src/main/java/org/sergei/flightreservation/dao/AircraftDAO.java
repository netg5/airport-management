/*
 * Copyright (c) 2018 Sergei Visotsky
 */

package org.sergei.flightreservation.dao;

import org.sergei.flightreservation.dao.generic.AbstractJpaDAO;
import org.sergei.flightreservation.model.Aircraft;
import org.springframework.stereotype.Repository;

/**
 * @author Sergei Visotsky, 2018
 */
@Repository
public class AircraftDAO extends AbstractJpaDAO<Aircraft> {
    public AircraftDAO() {
        setPersistentClass(Aircraft.class);
    }
}
