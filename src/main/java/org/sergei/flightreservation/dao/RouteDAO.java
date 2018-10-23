package org.sergei.flightreservation.dao;

import org.sergei.flightreservation.model.Route;
import org.springframework.stereotype.Repository;

@Repository
public class RouteDAO extends AbstractHibernateDAO<Route> {
    public RouteDAO() {
        setPersistentClass(Route.class);
    }
}
