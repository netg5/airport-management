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

package org.sergei.tickets.service;

import org.sergei.tickets.rest.dto.TicketDTO;
import org.sergei.tickets.rest.dto.response.ResponseDTO;
import org.springframework.http.ResponseEntity;

/**
 * @author Sergei Visotsky
 */
public interface TicketService {

    /**
     * Method to find tickets for passenger
     *
     * @param passengerId Request payload with params to find tickets
     * @return collection of tickets
     */
    ResponseEntity<ResponseDTO<TicketDTO>> findAllTickets(Long passengerId);
}
