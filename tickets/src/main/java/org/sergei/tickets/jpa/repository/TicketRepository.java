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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
     * @return list of the found tickets
     */
    @Query("SELECT \n" +
            "    t\n" +
            "FROM\n" +
            "    Ticket t\n" +
            "WHERE\n" +
            "    t.passengerId = ?1\n" +
            "        AND (?2 IS NULL OR t.dateOfFlying = ?2)")
    List<Ticket> findAllTickets(Long passengerId);

    /**
     * Find ticket by passenger ID, place or/and distance with pagination
     *
     * @param passengerId whose ticket should be found
     * @param pageable    page number and element quantity per page
     * @return list of found tickets
     */
    @Query("SELECT \n" +
            "    t\n" +
            "FROM\n" +
            "    Ticket t\n" +
            "WHERE\n" +
            "    t.passengerId = ?1\n" +
            "        AND (?2 IS NULL OR t.dateOfFlying = ?2)")
    Page<Ticket> findAllTicketsPageable(Long passengerId, Pageable pageable);
}
