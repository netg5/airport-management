/*
 * Copyright 2018-2019 the original author.
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

package org.sergei.reservationservice.repository;

import org.sergei.reservationservice.model.Customer;
import org.sergei.reservationservice.model.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author Sergei Visotsky
 */
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    /**
     * Find reservation for customer by ID
     *
     * @param customerId    whose reservation should be found
     * @param reservationId reservation which should be found
     * @return list of reservations
     */
    @Query("SELECT f FROM Reservation f WHERE f.customer.customerId = :customerId AND f.reservationId = :reservationId")
    Optional<Reservation> findOneForCustomer(@Param("customerId") Long customerId,
                                             @Param("reservationId") Long reservationId);

    /**
     * Find all reservations by customer ID
     *
     * @param customerId whose reservation should be found
     * @return list of reservations
     */
    @Query("SELECT f FROM Reservation f WHERE f.customer.customerId = :customerId")
    Optional<List<Reservation>> findAllForCustomer(@Param("customerId") Long customerId);

    /**
     * Find all reservations by customer ID paginated
     *
     * @param customerId whose reservations should be found
     * @param pageable   page size and record quantity per page
     * @return list of reservations
     */
    @Query("SELECT f FROM Reservation f WHERE f.customer.customerId = :customerId")
    Optional<Page<Reservation>> findAllForCustomerPaginated(@Param("customerId") Long customerId, Pageable pageable);

    /**
     * Method to delete reservation by customer and reservation found
     *
     * @param customer    customer found and given as a parameter
     * @param reservation reservation found and given as a parameter
     */
    @Transactional
    @Modifying
    @Query("DELETE FROM Reservation r WHERE r.customer = ?1 AND r = ?2")
    void deleteByCustomerIdAndReservationId(Customer customer, Reservation reservation);
}
