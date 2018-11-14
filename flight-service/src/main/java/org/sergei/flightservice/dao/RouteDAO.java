/*
 * Copyright (c) 2018 Sergei Visotsky
 */

package org.sergei.flightservice.dao;

import org.sergei.flightservice.dao.generic.AbstractJpaDAO;
import org.sergei.flightservice.model.Route;
import org.springframework.stereotype.Repository;

/**
 * @author Sergei Visotsky, 2018
 */
@Repository
public class RouteDAO extends AbstractJpaDAO<Route> {
    public RouteDAO() {
        setPersistentClass(Route.class);
    }
}
