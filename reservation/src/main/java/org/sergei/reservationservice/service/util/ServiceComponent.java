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

package org.sergei.reservationservice.service.util;

import org.sergei.reservationservice.exceptions.ResourceNotFoundException;
import org.sergei.reservationservice.jpa.model.Aircraft;
import org.sergei.reservationservice.jpa.model.Route;
import org.sergei.reservationservice.jpa.repository.AircraftRepository;
import org.sergei.reservationservice.rest.AircraftController;
import org.sergei.reservationservice.rest.RouteController;
import org.sergei.reservationservice.rest.dto.AircraftDTO;
import org.sergei.reservationservice.rest.dto.RouteExtendedDTO;
import org.sergei.reservationservice.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import static org.sergei.reservationservice.utils.ObjectMapperUtil.map;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Component to set extended route to the reservations and routes responses
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

        // Set links for the aircraft object in reservation JSON response
        Link aircraftSelfLink = linkTo(methodOn(AircraftController.class)
                .getAircraftById(aircraft.getAircraftId())).withRel("aircraftSelf");
        aircraftDTO.add(aircraftSelfLink);

        // Set aircraftDTO DTO to the flight reservation extended DTO
        routeExtendedDTO.setAircraftDTO(aircraftDTO);

        // Set links for the route object in reservation JSON response
        Link routeSelfLink = linkTo(methodOn(RouteController.class)
                .getRouteById(route.getRouteId())).withRel("routeSelf");

        routeExtendedDTO.add(routeSelfLink);

        return routeExtendedDTO;
    }
}
