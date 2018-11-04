/*
 * Copyright (c) 2018 Sergei Visotsky
 */

package org.sergei.flightreservation.dao;

import org.sergei.flightreservation.dao.generic.AbstractJpaDAO;
import org.sergei.flightreservation.model.FlightReservation;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * @author Sergei Visotsky, 2018
 */
@Repository
@SuppressWarnings("unchecked")
public class FlightReservationDAO extends AbstractJpaDAO<FlightReservation> {
    public FlightReservationDAO() {
        setPersistentClass(FlightReservation.class);
    }

    public List<FlightReservation> findAllForCustomer(Long customerId) {
        Query query = entityManager.createQuery("SELECT f FROM FlightReservation f WHERE f.customer.customerId = :customerId");
        query.setParameter("customerId", customerId);
        return query.getResultList();
    }
}
