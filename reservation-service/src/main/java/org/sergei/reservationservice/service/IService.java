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
 * @author Sergei Visotsky
 */
public interface IService<DTO> {

    /**
     * Find one entity by ID
     *
     * @param aLong entity ID
     * @return entity found
     */
    DTO findOne(Long aLong);

    /**
     * Find all entities
     *
     * @return list of entities
     */
    List<DTO> findAll();

    /**
     * Find all entities paginated
     *
     * @param page number of page
     * @param size quantity of elements per page
     * @return page of entities
     */
    Page<DTO> findAllPaginated(int page, int size);

    /**
     * Save entity
     *
     * @param entityDTO entity data t o be saved
     * @return saved entity data
     */
    DTO save(DTO entityDTO);

    /**
     * Update all data fro entity
     *
     * @param aLong     entity ID to beupdated
     * @param entityDTO updated entity body
     * @return updated entity
     */
    DTO update(Long aLong, DTO entityDTO);

    /**
     * Patch one or multiple fields of one entity
     *
     * @param aLong  ID of the entity to be patched
     * @param params fields to be patched
     * @return patched entity
     */
    DTO patch(Long aLong, Map<String, Object> params);

    /**
     * Delete entity by ID
     *
     * @param aLong ID of the entity to be deleted
     * @return deleted entity body
     */
    DTO delete(Long aLong);
}
