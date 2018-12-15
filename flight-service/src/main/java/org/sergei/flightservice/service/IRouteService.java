package org.sergei.flightservice.service;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author Sergei Visotsky
 * @since 12/10/2018
 */
public interface IRouteService<E, T> extends IService<E> {
    T findOneRoute(Long aLong);

    List<T> findAllRoutes();

    Page<T> findAllRoutesPaginated(int page, int size);
}
