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
import org.sergei.reservation.rest.dto.RouteRequestDTO;
import org.sergei.reservation.rest.dto.RouteResponseDTO;
import org.sergei.reservation.rest.dto.RouteUpdateRequestDTO;
import org.sergei.reservation.rest.dto.mappers.AircraftDTOMapper;
import org.sergei.reservation.rest.dto.mappers.RouteDTOMapper;
import org.sergei.reservation.rest.dto.mappers.RouteListDTOMapper;
import org.sergei.reservation.rest.dto.response.ResponseDTO;
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
    private final AircraftDTOMapper aircraftDTOMapper;

    @Autowired
    public RouteServiceImpl(AircraftRepository aircraftRepository,
                            RouteRepository routeRepository,
                            RouteDTOMapper routeDTOMapper,
                            RouteListDTOMapper routeDTOListMapper,
                            AircraftDTOMapper aircraftDTOMapper) {
        this.aircraftRepository = aircraftRepository;
        this.routeRepository = routeRepository;
        this.routeDTOMapper = routeDTOMapper;
        this.routeDTOListMapper = routeDTOListMapper;
        this.aircraftDTOMapper = aircraftDTOMapper;
    }

    /**
     * Method to find one route
     *
     * @param routeId as an input argument from controller
     * @return route extended DTO
     */
    @Override
    public ResponseEntity<ResponseDTO<RouteResponseDTO>> findOneRoute(Long routeId) {
        // Find route and map into the extended DTO
        Optional<Route> route = routeRepository.findById(routeId);
        if (route.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
        } else {
            RouteResponseDTO routeResponseDTO = routeDTOMapper.apply(route.get());

            // Find aircraftId by ID taken from the entity
            Optional<Aircraft> aircraft = aircraftRepository.findById(route.get().getAircraft().getId());
            if (aircraft.isEmpty()) {
                return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
            } else {
                AircraftDTO aircraftDTO = aircraftDTOMapper.apply(aircraft.get());
                // Set aircraftId DTO to the flight reservation extended DTO
                routeResponseDTO.setAircraftDTOList(List.of(aircraftDTO));

                ResponseDTO<RouteResponseDTO> response = new ResponseDTO<>();
                response.setErrorList(List.of());
                response.setResponse(List.of(routeResponseDTO));

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
    public ResponseEntity<ResponseDTO<RouteResponseDTO>> findAllRoutes() {
        List<Route> routeList = routeRepository.findAll();

        if (routeList.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
        } else {

            List<RouteResponseDTO> routeResponseDTOList = routeDTOListMapper.apply(routeList);

            int counter = 0;
            for (RouteResponseDTO routeResponseDTO : routeResponseDTOList) {
                Optional<Aircraft> aircraft = aircraftRepository.findById(routeList.get(counter).getAircraft().getId());
                if (aircraft.isEmpty()) {
                    return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
                } else {
                    AircraftDTO aircraftDTO = aircraftDTOMapper.apply(aircraft.get());
                    routeResponseDTO.setAircraftDTOList(List.of(aircraftDTO));
                    counter++;
                }
            }

            ResponseDTO<RouteResponseDTO> response = new ResponseDTO<>();
            response.setErrorList(List.of());
            response.setResponse(routeResponseDTOList);

            return new ResponseEntity<>(response, HttpStatus.OK);
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
    public ResponseEntity<ResponseDTO<RouteResponseDTO>> findAllRoutesPaginated(int page, int size) {
        Page<Route> routePage = routeRepository.findAll(PageRequest.of(page, size));
        if (routePage.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
        } else {
            List<RouteResponseDTO> routeResponseDTOList = routeDTOListMapper.apply(routePage.getContent());

            int counter = 0;
            for (RouteResponseDTO routeResponseDTO : routeResponseDTOList) {
                Optional<Aircraft> aircraft = aircraftRepository.findById(routePage.getContent().get(counter).getAircraft().getId());
                if (aircraft.isEmpty()) {
                    return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
                } else {
                    AircraftDTO aircraftDTO = aircraftDTOMapper.apply(aircraft.get());
                    routeResponseDTO.setAircraftDTOList(List.of(aircraftDTO));
                    counter++;
                }
            }

            ResponseDTO<RouteResponseDTO> response = new ResponseDTO<>();
            response.setErrorList(List.of());
            response.setResponse(routeResponseDTOList);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    /**
     * Save route
     *
     * @param request gets route DTO as an input argument
     * @return route DTO as a response
     */
    @Override
    public ResponseEntity<ResponseDTO<RouteResponseDTO>> save(RouteRequestDTO request) {
        Optional<Aircraft> aircraft = aircraftRepository.findById(request.getRouteId());
        if (aircraft.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
        } else {
            Route route = new Route();

            route.setId(request.getRouteId());
            route.setDistance(request.getDistance());
            route.setDepartureTime(request.getDepartureTime());
            route.setArrivalTime(request.getArrivalTime());
            route.setPlace(request.getPlace());
            route.setPrice(request.getPrice());
            route.setAircraft(aircraft.get());

            Route savedRoute = routeRepository.save(route);
            RouteResponseDTO savedRouteResponseDTO = routeDTOMapper.apply(savedRoute);

            ResponseDTO<RouteResponseDTO> response = new ResponseDTO<>();
            response.setErrorList(List.of());
            response.setResponse(List.of(savedRouteResponseDTO));

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
    }

    /**
     * Update route
     *
     * @param request
     * @return
     */
    @Override
    public ResponseEntity<ResponseDTO<RouteResponseDTO>> update(RouteUpdateRequestDTO request) {
        Optional<Route> route = routeRepository.findById(request.getRouteId());
        if (route.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
        } else {
            Optional<Aircraft> aircraft = aircraftRepository.findById(request.getRouteRequest().getAircraftId());
            if (aircraft.isEmpty()) {
                return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
            } else {
                request.getRouteRequest().setDistance(request.getRouteRequest().getDistance());
                request.getRouteRequest().setDepartureTime(request.getRouteRequest().getDepartureTime());
                request.getRouteRequest().setArrivalTime(request.getRouteRequest().getArrivalTime());
                request.getRouteRequest().setPrice(request.getRouteRequest().getPrice());
                request.getRouteRequest().setPrice(request.getRouteRequest().getPrice());
                request.getRouteRequest().setPlace(request.getRouteRequest().getPlace());
                request.getRouteRequest().setAircraftId(aircraft.get().getId());
                routeRepository.save(route.get());

                return setRouteResponse(route.get());
            }
        }
    }

    /**
     * Method to update one field of the route
     *
     * @param routeId ID of the route which should be updated
     * @param params  list of params that should be updated
     * @return updated route
     */
    @Override
    public ResponseEntity<ResponseDTO<RouteResponseDTO>> patch(Long routeId, Map<String, Object> params) {
        Optional<Route> route = routeRepository.findById(routeId);
        ResponseDTO<RouteResponseDTO> response = new ResponseDTO<>();
        if (route.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
        } else {
            if (params.get("distance") != null) {
                route.get().setDistance(Double.valueOf(String.valueOf(params.get("distance"))));
            }
            if (params.get("departureTime") != null) {
                route.get().setDepartureTime(LocalDateTime.parse(String.valueOf(params.get("departureTime"))));
            }
            if (params.get("arrivalTime") != null) {
                route.get().setArrivalTime(LocalDateTime.parse(String.valueOf(params.get("arrivalTime"))));
            }
            if (params.get("price") != null) {
                route.get().setPrice(BigDecimal.valueOf(Long.parseLong(String.valueOf(params.get("price")))));
            }
            if (params.get("place") != null) {
                route.get().setPlace(String.valueOf(params.get("place")));
            }
            if (params.get("id") != null) {
                Optional<Aircraft> aircraft = aircraftRepository.findById(Long.valueOf(String.valueOf(params.get("id"))));
                if (aircraft.isEmpty()) {
                    return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
                } else {
                    route.get().setAircraft(aircraft.get());

                    Route savedRoute = routeRepository.save(route.get());
                    RouteResponseDTO routeResponseDTO = routeDTOMapper.apply(savedRoute);
                    AircraftDTO aircraftDTO = aircraftDTOMapper.apply(aircraft.get());
                    routeResponseDTO.setAircraftDTOList(List.of(aircraftDTO));

                    response.setErrorList(List.of());
                    response.setResponse(List.of(routeResponseDTO));
                }
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }


    /**
     * Delete route by ID
     *
     * @param routeId gets route ID as an input argument
     * @return Route DTO asa response
     */
    @Override
    public ResponseEntity<ResponseDTO<RouteResponseDTO>> delete(Long routeId) {
        Optional<Route> route = routeRepository.findById(routeId);
        if (route.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
        } else {
            routeRepository.delete(route.get());
            return setRouteResponse(route.get());
        }
    }

    private ResponseEntity<ResponseDTO<RouteResponseDTO>> setRouteResponse(Route route) {
        RouteResponseDTO routeResponseDTO = routeDTOMapper.apply(route);

        ResponseDTO<RouteResponseDTO> response = new ResponseDTO<>();
        response.setErrorList(List.of());
        response.setResponse(List.of(routeResponseDTO));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
