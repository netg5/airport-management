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

package org.sergei.reservationservice.service.util;

import org.sergei.reservationservice.dto.AircraftDTO;
import org.sergei.reservationservice.dto.RouteExtendedDTO;
import org.sergei.reservationservice.exceptions.ResourceNotFoundException;
import org.sergei.reservationservice.model.Aircraft;
import org.sergei.reservationservice.model.Route;
import org.sergei.reservationservice.repository.AircraftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.sergei.reservationservice.util.ObjectMapperUtil.map;

/**
 * Component to set extended route to the reservations and routes
 *
 * @author Sergei Visotsky
 */
@Component
public class ServiceComponent {

    private final AircraftRepository aircraftRepository;

    @Autowired
    private ServiceComponent(AircraftRepository aircraftRepository) {
        this.aircraftRepository = aircraftRepository;
    }

    /**
     * Util method fo set extended route
     *
     * @param route model to be mapped into the DTO
     * @return Route extended DTO
     */
    public RouteExtendedDTO setExtendedRoute(Route route) {
        RouteExtendedDTO routeExtendedDTO = map(route, RouteExtendedDTO.class);

        // Find aircraftDTO by ID taken from the entity
        Aircraft aircraft = aircraftRepository.findById(route.getAircraft().getAircraftId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(Constants.AIRCRAFT_NOT_FOUND)
                );

        AircraftDTO aircraftDTO = map(aircraft, AircraftDTO.class);

        // Set aircraftDTO DTO to the flight reservation extended DTO
        routeExtendedDTO.setAircraftDTO(aircraftDTO);

        return routeExtendedDTO;
    }
}
