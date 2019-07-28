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

import org.sergei.reservation.jpa.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Sergei Visotsky
 */
@Repository
public interface CustomerRepository extends JpaRepository<Passenger, Long> {

    /**
     * Find ID of each passenger in one JSON response as a list
     * <p>
     * new keyword is used to create projection into the DTO,
     * e.g. to extract just only IDs from the model object in this case.
     *
     * @return list of IDs
     */
    @Query(
            value = "SELECT c.customer_id FROM customer c",
            nativeQuery = true)
    List<String> findIdsOfAllCustomers();
}
