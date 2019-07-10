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

package org.sergei.reportservice.jpa.repository;

import org.sergei.reportservice.jpa.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query(
            value = "SELECT \n" +
                    "    *\n" +
                    "FROM\n" +
                    "    reservation r\n" +
                    "        LEFT JOIN\n" +
                    "    aircraft_report_view ar ON r.route_id = ar.route_id\n" +
                    "WHERE\n" +
                    "    ar.route_id = ?1",
            nativeQuery = true
    )
    List<Reservation> findAllByRouteId(Long routeId);

    @Query(
            value = "SELECT \n" +
                    "    *\n" +
                    "FROM\n" +
                    "    reservation r\n" +
                    "        LEFT JOIN\n" +
                    "    customer c ON r.customer_id = c.customer_id\n" +
                    "WHERE\n" +
                    "    r.customer_id = ?1",
            nativeQuery = true
    )
    List<Reservation> findAllByCustomerId(Long customerId);
}