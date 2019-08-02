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

package org.sergei.manager.service;

import lombok.extern.slf4j.Slf4j;
import org.sergei.manager.jpa.model.Aircraft;
import org.sergei.manager.jpa.model.Route;
import org.sergei.manager.jpa.model.mappers.RouteModelMapper;
import org.sergei.manager.jpa.repository.AircraftRepository;
import org.sergei.manager.jpa.repository.RouteRepository;
import org.sergei.manager.rest.dto.AircraftDTO;
import org.sergei.manager.rest.dto.RouteDTO;
import org.sergei.manager.rest.dto.mappers.AircraftDTOMapper;
import org.sergei.manager.rest.dto.mappers.RouteDTOMapper;
import org.sergei.manager.rest.dto.mappers.RouteListDTOMapper;
import org.sergei.manager.rest.dto.response.ResponseDTO;
import org.sergei.manager.rest.dto.response.ResponseErrorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Sergei Visotsky
 */
@Slf4j
@Service
public class RouteServiceImpl implements RouteService {

    private final AircraftRepository aircraftRepository;
    private final RouteRepository routeRepository;
    private final RouteModelMapper routeModelMapper;
    private final RouteDTOMapper routeDTOMapper;
    private final RouteListDTOMapper routeDTOListMapper;
    private final AircraftDTOMapper aircraftDTOMapper;
    private final MessageService messageService;

    @Autowired
    public RouteServiceImpl(AircraftRepository aircraftRepository,
                            RouteRepository routeRepository,
                            RouteModelMapper routeModelMapper,
                            RouteDTOMapper routeDTOMapper,
                            RouteListDTOMapper routeDTOListMapper,
                            AircraftDTOMapper aircraftDTOMapper,
                            MessageService messageService) {
        this.aircraftRepository = aircraftRepository;
        this.routeRepository = routeRepository;
        this.routeModelMapper = routeModelMapper;
        this.routeDTOMapper = routeDTOMapper;
        this.routeDTOListMapper = routeDTOListMapper;
        this.aircraftDTOMapper = aircraftDTOMapper;
        this.messageService = messageService;
    }

    /**
     * Method to find one route
     *
     * @param routeId as an input argument from controller
     * @return route extended DTO
     */
    @Override
    public ResponseEntity<ResponseDTO<RouteDTO>> findOneRoute(Long routeId) {
        // Find route and map into the extended DTO
        Optional<Route> route = routeRepository.findById(routeId);
        if (route.isEmpty()) {
            List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("RT-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
        } else {
            RouteDTO routeDTO = routeDTOMapper.apply(route.get());

            // Find aircraftId by ID taken from the entity
            Optional<Aircraft> aircraft = aircraftRepository.findById(route.get().getAircraft().getId());
            if (aircraft.isEmpty()) {
                List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("AIR-001");
                return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
            } else {
                AircraftDTO aircraftDTO = aircraftDTOMapper.apply(aircraft.get());
                // Set aircraftId DTO to the flight reservation extended DTO
                routeDTO.setAircraftDTO(aircraftDTO);

                ResponseDTO<RouteDTO> response = new ResponseDTO<>();
                response.setErrorList(List.of());
                response.setResponse(List.of(routeDTO));

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
    public ResponseEntity<ResponseDTO<RouteDTO>> findAllRoutes() {
        List<Route> routeList = routeRepository.findAll();

        if (routeList.isEmpty()) {
            List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("RT-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
        } else {

            List<RouteDTO> routeDTOList = routeDTOListMapper.apply(routeList);

            int counter = 0;
            for (RouteDTO routeDTO : routeDTOList) {
                Optional<Aircraft> aircraft = aircraftRepository.findById(routeList.get(counter).getAircraft().getId());
                if (aircraft.isEmpty()) {
                    List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("AIR-001");
                    return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
                } else {
                    AircraftDTO aircraftDTO = aircraftDTOMapper.apply(aircraft.get());
                    routeDTO.setAircraftDTO(aircraftDTO);
                    counter++;
                }
            }

            ResponseDTO<RouteDTO> response = new ResponseDTO<>();
            response.setErrorList(List.of());
            response.setResponse(routeDTOList);

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
    public ResponseEntity<ResponseDTO<RouteDTO>> findAllRoutesPaginated(int page, int size) {
        Page<Route> routePage = routeRepository.findAll(PageRequest.of(page, size));
        if (routePage.isEmpty()) {
            List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("RT-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
        } else {
            List<RouteDTO> routeDTOList = routeDTOListMapper.apply(routePage.getContent());

            int counter = 0;
            for (RouteDTO routeDTO : routeDTOList) {
                Optional<Aircraft> aircraft = aircraftRepository.findById(routePage.getContent().get(counter).getAircraft().getId());
                if (aircraft.isEmpty()) {
                    List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("AIR-001");
                    return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
                } else {
                    AircraftDTO aircraftDTO = aircraftDTOMapper.apply(aircraft.get());
                    routeDTO.setAircraftDTO(aircraftDTO);
                    counter++;
                }
            }

            ResponseDTO<RouteDTO> response = new ResponseDTO<>();
            response.setErrorList(List.of());
            response.setResponse(routeDTOList);

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
    public ResponseEntity<ResponseDTO<RouteDTO>> save(RouteDTO request) {
        Optional<Aircraft> aircraft = aircraftRepository.findById(request.getRouteId());
        if (aircraft.isEmpty()) {
            List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("AIR-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
        } else {
            Route route = routeModelMapper.apply(request);

            Route savedRoute = routeRepository.save(route);
            RouteDTO savedRouteDTO = routeDTOMapper.apply(savedRoute);

            ResponseDTO<RouteDTO> response = new ResponseDTO<>();
            response.setErrorList(List.of());
            response.setResponse(List.of(savedRouteDTO));

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
    public ResponseEntity<ResponseDTO<RouteDTO>> update(RouteDTO request) {
        Optional<Route> route = routeRepository.findById(request.getRouteId());
        if (route.isEmpty()) {
            List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("RT-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
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
                routeRepository.save(route.get());

                return setRouteResponse(route.get());
            }
        }
    }

    /**
     * Delete route by ID
     *
     * @param routeId gets route ID as an input argument
     * @return Route DTO asa response
     */
    @Override
    public ResponseEntity<ResponseDTO<RouteDTO>> delete(Long routeId) {
        Optional<Route> route = routeRepository.findById(routeId);
        if (route.isEmpty()) {
            List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("RT-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
        } else {
            routeRepository.delete(route.get());
            return setRouteResponse(route.get());
        }
    }

    private ResponseEntity<ResponseDTO<RouteDTO>> setRouteResponse(Route route) {
        RouteDTO routeDTO = routeDTOMapper.apply(route);

        ResponseDTO<RouteDTO> response = new ResponseDTO<>();
        response.setErrorList(List.of());
        response.setResponse(List.of(routeDTO));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
