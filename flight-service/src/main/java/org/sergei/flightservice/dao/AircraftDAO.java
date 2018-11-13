/*
 * Copyright (c) 2018 Sergei Visotsky
 */

package org.sergei.flightservice.dao;

import org.sergei.flightservice.dao.generic.AbstractJpaDAO;
import org.sergei.flightservice.model.Aircraft;
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
