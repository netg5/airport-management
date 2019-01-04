package org.sergei.reservationservice.service;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
public interface IRouteService<E, T> extends IService<E> {

    /**
     * Find one route
     *
     * @param aLong ID of the route to be found
     * @return route body to be returned
     */
    T findOneRoute(Long aLong);

    /**
     * Find list of routes
     *
     * @return list of all routes found
     */
    List<T> findAllRoutes();

    /**
     * Find all routed paginated
     *
     * @param page number of page to return
     * @param size number of elements per page
     * @return page of entities
     */
    Page<T> findAllRoutesPaginated(int page, int size);
}
