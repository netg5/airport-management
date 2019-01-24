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

package org.sergei.reservationservice.service;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @param <D> Simple DTO
 * @param <I> DTO with customer IDs
 * @author Sergei Visotsky
 */
public interface ICustomerService<D, I> extends IService<D> {
    /**
     * Find ID of each customer in one JSON response as a list
     *
     * @return list of IDs
     */
    List<I> findIdsOfAllCustomers();

    /**
     * Find ID of each customer in one JSON response as a list paginated
     *
     * @param page how many pages to show
     * @param size number of elements per page
     * @return page of the IDs
     */
    Page<I> findIdsOfAllCustomersPaginated(int page, int size);
}
