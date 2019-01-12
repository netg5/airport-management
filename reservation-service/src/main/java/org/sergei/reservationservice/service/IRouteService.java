/*
 * Copyright 2018-2019 Sergei Visotsky
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sergei.reservationservice.service;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @param <D> Simple DTO
 * @param <E> Extended DTO
 * @author Sergei Visotsky
 */
public interface IRouteService<D, E> extends IService<D> {

    /**
     * Find one route
     *
     * @param aLong ID of the route to be found
     * @return route body to be returned
     */
    E findOneRoute(Long aLong);

    /**
     * Find list of routes
     *
     * @return list of all routes found
     */
    List<E> findAllRoutes();

    /**
     * Find all routed paginated
     *
     * @param page number of page to return
     * @param size number of elements per page
     * @return page of entities
     */
    Page<E> findAllRoutesPaginated(int page, int size);
}
