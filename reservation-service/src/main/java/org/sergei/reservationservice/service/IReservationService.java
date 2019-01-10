/*
 * Copyright 2018-2019 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.sergei.reservationservice.service;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * @param <E> Extended DTO
 * @param <D> Simple DTO
 * @author Sergei Visotsky
 */
public interface IReservationService<E, D> {

    /**
     * Find one reservation for customer
     *
     * @param customerId    Customer ID
     * @param reservationId reservation ID
     * @return reservation entity
     */
    E findOneForCustomer(Long customerId, Long reservationId);

    /**
     * Find all reservations for customer
     *
     * @param customerId customer ID to find reservations
     * @return list of reservations
     */
    List<E> findAllForCustomer(Long customerId);

    /**
     * Find all reservations for customer paginated
     *
     * @param customerId Customer ID whoose reservations should be found
     * @param page       number of page to show
     * @param size       number of elements per page
     * @return collection of entities
     */
    Page<E> findAllForCustomerPaginated(Long customerId, int page, int size);

    /**
     * Save reservation for customer
     *
     * @param aLong   Customer ID who made reservation
     * @param entityD reservation body
     * @return saved reservation
     */
    D saveReservation(Long aLong, D entityD);

    /**
     * Update reservation details
     *
     * @param customerId    customer who made reservation
     * @param reservationId reservation ID to be patched
     * @param params        Field(-s) to be patched
     * @return patched reservation
     */
    D updateReservation(Long customerId, Long reservationId, Map<String, Object> params);

    /**
     * Method to delete reservation
     *
     * @param customerId    customer who made reservations
     * @param reservationId made reservation ID
     * @return deleted reservation entity
     */
    D deleteReservation(Long customerId, Long reservationId);
}
