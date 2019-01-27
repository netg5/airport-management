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

import org.sergei.reservationservice.dto.CustomerIdsDTO;
import org.sergei.reservationservice.model.Customer;
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
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**
     * Find ID of each customer in one JSON response as a list
     * <p>
     * new keyword is used to create projection into the DTO,
     * e.g. to extract just only IDs from the model object in this case.
     *
     * @return list of IDs
     */
    @Query("SELECT NEW org.sergei.reservationservice.dto.CustomerIdsDTO(c.customerId) FROM Customer c")
    List<CustomerIdsDTO> findIdsOfAllCustomers();

    /**
     * Find ID of each customer in one JSON response as a list paginated
     * <p>
     * countQuery is used for create pageable response creation and to count element quantity
     *
     * @param pageable number of the page and number of the elements per page
     * @return Collection of customer IDs
     */
    @Query(value = "SELECT NEW org.sergei.reservationservice.dto.CustomerIdsDTO(c.customerId) FROM Customer c",
            countQuery = "SELECT count(c) FROM Customer c")
    Page<CustomerIdsDTO> findIdsOfAllCustomersPaginated(Pageable pageable);
}
