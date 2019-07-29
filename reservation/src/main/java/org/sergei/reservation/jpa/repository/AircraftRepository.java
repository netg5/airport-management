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

package org.sergei.reservation.jpa.repository;

import org.sergei.reservation.jpa.model.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Sergei Visotsky
 */
@Repository
public interface AircraftRepository extends JpaRepository<Aircraft, Long> {

    /**
     * Method to get aircraftId by multiple parameters
     *
     * @param aircraftName  aircraftId name
     * @param weight        aircraftId weight
     * @param maxPassengers maximum passengers per aircraftId
     * @param model         aircraftId model
     * @return aircraftId entity
     */
    @Query("SELECT \n" +
            "    a\n" +
            "FROM\n" +
            "    Aircraft a\n" +
            "WHERE\n" +
            "    aircraftName = ?1 AND aircraftWeight = ?2\n" +
            "        AND maxPassengers = ?3\n" +
            "        AND model = ?4")
    Optional<Aircraft> findAircraftByMultipleParams(String aircraftName, Double weight,
                                                    Integer maxPassengers, String model);
}
