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

package org.sergei.tickets.jpa.repository;

import org.sergei.tickets.jpa.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    /**
     * Find ticket by passenger ID, place or/and distance
     *
     * @param passengerId whose ticket should be found
     * @param currency    currency (EUR/USD)
     * @return list of the found tickets
     */
    @Query("SELECT t FROM Ticket t" +
            " WHERE t.passengerId = :passengerId" +
            " AND t.currency = :currency")
    List<Ticket> findAllTickets(@Param("passengerId") Long passengerId,
                                @Param("currency") String currency);
}
