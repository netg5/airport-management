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

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sergei Visotsky
 */
public interface IAircraftService<D> extends IService<D> {

    /**
     * Find aircraft by multiple parameters
     * NOTE: There id no optional parameters
     *
     * @param request of all requested parameters
     * @return DTO of found entity
     */
    D findOneByMultipleParams(HttpServletRequest request);
}
