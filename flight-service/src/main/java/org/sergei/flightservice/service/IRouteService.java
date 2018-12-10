package org.sergei.flightservice.service;

import java.util.List;

/**
 * @author Sergei Visotsky
 * @since 12/10/2018
 */
public interface IRouteService<E, T> extends IService<E> {
    T findOneRoute(Long aLong);

    List<T> findAllRoutes();
}
