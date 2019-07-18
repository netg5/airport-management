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

package org.sergei.reservation.service;

import lombok.extern.slf4j.Slf4j;
import org.sergei.reservation.jpa.model.Aircraft;
import org.sergei.reservation.jpa.model.Route;
import org.sergei.reservation.jpa.repository.AircraftRepository;
import org.sergei.reservation.jpa.repository.RouteRepository;
import org.sergei.reservation.rest.dto.AircraftDTO;
import org.sergei.reservation.rest.dto.RouteDTO;
import org.sergei.reservation.rest.dto.RouteExtendedDTO;
import org.sergei.reservation.rest.dto.mappers.RouteDTOMapper;
import org.sergei.reservation.rest.dto.mappers.RouteListDTOMapper;
import org.sergei.reservation.rest.dto.response.ResponseDTO;
import org.sergei.reservation.rest.exceptions.ResourceNotFoundException;
import org.sergei.reservation.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.sergei.reservation.utils.ObjectMapperUtil.map;
import static org.sergei.reservation.utils.ObjectMapperUtil.mapAllPages;

/**
 * @author Sergei Visotsky
 */
@Slf4j
@Service
public class RouteServiceImpl implements RouteService {

    private final AircraftRepository aircraftRepository;
    private final RouteRepository routeRepository;
    private final RouteDTOMapper routeDTOMapper;
    private final RouteListDTOMapper routeDTOListMapper;

    @Autowired
    public RouteServiceImpl(AircraftRepository aircraftRepository,
                            RouteRepository routeRepository,
                            RouteDTOMapper routeDTOMapper,
                            RouteListDTOMapper routeDTOListMapper) {
        this.aircraftRepository = aircraftRepository;
        this.routeRepository = routeRepository;
        this.routeDTOMapper = routeDTOMapper;
        this.routeDTOListMapper = routeDTOListMapper;
    }

    /**
     * Method to find one route
     *
     * @param routeId as an input argument from controller
     * @return route extended DTO
     */
    @Override
    public ResponseEntity<ResponseDTO<RouteExtendedDTO>> findOneRoute(Long routeId) {
        // Find route and map into the extended DTO
        Optional<Route> route = routeRepository.findById(routeId);
        if (route.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            RouteExtendedDTO routeExtendedDTO = new RouteExtendedDTO();
            RouteDTO routeDTO = routeDTOMapper.apply(route.get());

            // Find aircraftDTO by ID taken from the entity
            Optional<Aircraft> aircraft = aircraftRepository.findById(route.get().getAircraft().getId());
            if (aircraft.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                AircraftDTO aircraftDTO = map(aircraft, AircraftDTO.class);
                // Set aircraftDTO DTO to the flight reservation extended DTO
                routeExtendedDTO.setAircraftDTO(aircraftDTO);

                ResponseDTO<RouteExtendedDTO> response = new ResponseDTO<>();
                response.setErrorList(List.of());
                response.setResponse(List.of(routeExtendedDTO));

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
    }

    /**
     * Find all routes
     *
     * @return list of route extended DTO
     */
    @Override
    public ResponseEntity<ResponseDTO<RouteExtendedDTO>> findAllRoutes() {
        List<Route> routeList = routeRepository.findAll();

        if (routeList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {

            List<RouteExtendedDTO> routeExtendedDTOList = routeDTOListMapper.applyList(routeList);

            int counter = 0;
            for (RouteExtendedDTO routeExtendedDTO : routeExtendedDTOList) {
                Aircraft aircraft = aircraftRepository.findById(routeList.get(counter).getAircraft().getId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(Constants.AIRCRAFT_NOT_FOUND)
                        );

                AircraftDTO aircraftDTO = map(aircraft, AircraftDTO.class);
                routeExtendedDTO.setAircraftDTO(aircraftDTO);
                counter++;
            }

            return routeExtendedDTOList;
        }
    }

    /**
     * Find paginated routes
     *
     * @param page number of pages shown
     * @param size number of elements on the page
     * @return list of entities
     */
    @Override
    public ResponseEntity<ResponseDTO<RouteExtendedDTO>> findAllRoutesPaginated(int page, int size) {
        Page<Route> routePage = routeRepository.findAll(PageRequest.of(page, size));
        Page<RouteExtendedDTO> routeExtendedDTOList = mapAllPages(routePage, RouteExtendedDTO.class);
        int counter = 0;
        for (RouteExtendedDTO routeExtendedDTO : routeExtendedDTOList) {
            Aircraft aircraft = aircraftRepository.findById(routePage.getContent().get(counter).getAircraft().getId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(Constants.AIRCRAFT_NOT_FOUND)
                    );

            AircraftDTO aircraftDTO = map(aircraft, AircraftDTO.class);
            routeExtendedDTO.setAircraftDTO(aircraftDTO);
            counter++;
        }
        return null;
//        return routeExtendedDTOList;
    }

    /**
     * Save route
     *
     * @param routeDTO gets route DTO as an input argument
     * @return route DTO as a response
     */
    @Override
    public ResponseEntity<ResponseDTO<RouteDTO>> save(RouteDTO routeDTO) {
        Route route = map(routeDTO, Route.class);

        // Find aircraftDTO required in request body
        Aircraft aircraft = aircraftRepository.findById(routeDTO.getRouteId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(Constants.AIRCRAFT_NOT_FOUND)
                );
        log.debug("Found aircraft ID: {}", aircraft.getId());
        route.setAircraft(aircraft);
        Route savedRoute = routeRepository.save(route);
        log.debug("Aircraft ID in saved route: {}", savedRoute.getAircraft().getId());
        RouteDTO savedRouteDTO = map(savedRoute, RouteDTO.class);
        savedRouteDTO.setAircraftId(aircraft.getId());
        return savedRouteDTO;
    }

    /**
     * Update route data
     *
     * @param routeId  get route ID as an input argument
     * @param routeDTO Route DTO with updated data
     * @return Route DTO as a response
     */
    @Override
    public ResponseEntity<ResponseDTO<RouteDTO>> update(Long routeId, RouteDTO routeDTO) {
        routeDTO.setRouteId(routeId);

        Route route = routeRepository.findById(routeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(Constants.ROUTE_NOT_FOUND)
                );
        route.setDistance(routeDTO.getDistance());
        route.setDepartureTime(routeDTO.getDepartureTime());
        route.setArrivalTime(routeDTO.getArrivalTime());
        route.setPrice(routeDTO.getPrice());
        route.setPrice(routeDTO.getPrice());
        route.setPlace(routeDTO.getPlace());
        route.setAircraft(
                aircraftRepository.findById(routeDTO.getRouteId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(Constants.AIRCRAFT_NOT_FOUND)
                        )
        );
        routeRepository.save(route);

        return routeDTO;
    }

    /**
     * Method to update one field of the route
     *
     * @param routeId ID of the route which should be updated
     * @param params  list of params that should be updated
     * @return updated route
     */
    @Override
    public ResponseEntity<ResponseDTO<RouteDTO>> patch(Long routeId, Map<String, Object> params) {
        Route route = routeRepository.findById(routeId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(Constants.AIRCRAFT_NOT_FOUND)
                );
        if (params.get("distance") != null) {
            route.setDistance(Double.valueOf(String.valueOf(params.get("distance"))));
        }
        if (params.get("departureTime") != null) {
            route.setDepartureTime(LocalDateTime.parse(String.valueOf(params.get("departureTime"))));
        }
        if (params.get("arrivalTime") != null) {
            route.setArrivalTime(LocalDateTime.parse(String.valueOf(params.get("arrivalTime"))));
        }
        if (params.get("price") != null) {
            route.setPrice(BigDecimal.valueOf(Long.parseLong(String.valueOf(params.get("price")))));
        }
        if (params.get("place") != null) {
            route.setPlace(String.valueOf(params.get("place")));
        }
        if (params.get("id") != null) {
            route.setAircraft(aircraftRepository.findById(Long.valueOf(String.valueOf(params.get("id"))))
                    .orElseThrow(() ->
                            new ResourceNotFoundException(Constants.AIRCRAFT_NOT_FOUND)
                    ));
        }
        RouteDTO routeDTO = map(routeRepository.save(route), RouteDTO.class);
        routeDTO.setAircraftId(route.getAircraft().getId());
        return routeDTO;
    }

    /**
     * Delete route by ID
     *
     * @param routeId gets route ID as an input argument
     * @return Route DTO asa response
     */
    @Override
    public ResponseEntity<ResponseDTO<RouteDTO>> delete(Long routeId) {
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(Constants.ROUTE_NOT_FOUND)
                );
        routeRepository.delete(route);
        return map(route, RouteDTO.class);
    }
}
