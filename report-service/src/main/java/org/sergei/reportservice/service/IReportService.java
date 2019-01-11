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

package org.sergei.reportservice.service;

import java.util.List;

/**
 * @param <D> Data Transfer Object
 * @author Sergei Visotsky
 */
public interface IReportService<D> {

    /**
     * Find all existing reports
     *
     * @return list of existing reports
     */
    List<D> findAll();

    /**
     * Find one report by ID
     *
     * @param id identity of the report that should be found
     * @return Report entity
     */
    D findById(Long id);
}
