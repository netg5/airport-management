package org.sergei.flightreservation.dao;

import org.sergei.flightreservation.model.Aircraft;
import org.springframework.stereotype.Repository;

@Repository
public class AircraftDAO extends AbstractHibernateDAO<Aircraft> {
    public AircraftDAO() {
        setPersistentClass(Aircraft.class);
    }
}
