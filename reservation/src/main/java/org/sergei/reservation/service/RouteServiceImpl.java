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
import org.sergei.reservation.rest.dto.RouteResponseDTO;
import org.sergei.reservation.rest.dto.mappers.AircraftDTOMapper;
import org.sergei.reservation.rest.dto.mappers.RouteDTOMapper;
import org.sergei.reservation.rest.dto.mappers.RouteListDTOMapper;
import org.sergei.reservation.rest.dto.response.ResponseDTO;
import org.sergei.reservation.rest.dto.response.ResponseErrorDTO;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final RouteDTOMapper routeDTOMapper;
    private final RouteListDTOMapper routeDTOListMapper;
    private final AircraftDTOMapper aircraftDTOMapper;
    private final ResponseMessageService responseMessageService;

    @Autowired
    public RouteServiceImpl(AircraftRepository aircraftRepository,
                            RouteRepository routeRepository,
                            RouteDTOMapper routeDTOMapper,
                            RouteListDTOMapper routeDTOListMapper,
                            AircraftDTOMapper aircraftDTOMapper,
                            ResponseMessageService responseMessageService) {
        this.aircraftRepository = aircraftRepository;
        this.routeRepository = routeRepository;
        this.routeDTOMapper = routeDTOMapper;
        this.routeDTOListMapper = routeDTOListMapper;
        this.aircraftDTOMapper = aircraftDTOMapper;
        this.responseMessageService = responseMessageService;
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
            List<ResponseErrorDTO> responseErrorList = responseMessageService.responseErrorListByCode("RT-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
        } else {
            RouteResponseDTO routeResponseDTO = routeDTOMapper.apply(route.get());

            // Find aircraftId by ID taken from the entity
            Optional<Aircraft> aircraft = aircraftRepository.findById(route.get().getAircraft().getId());
            if (aircraft.isEmpty()) {
                List<ResponseErrorDTO> responseErrorList = responseMessageService.responseErrorListByCode("AIR-001");
                return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
            } else {
                AircraftDTO aircraftDTO = aircraftDTOMapper.apply(aircraft.get());
                // Set aircraftId DTO to the flight reservation extended DTO
                routeResponseDTO.setAircraftDTO(aircraftDTO);

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
            List<ResponseErrorDTO> responseErrorList = responseMessageService.responseErrorListByCode("RT-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
        } else {

            List<RouteResponseDTO> routeResponseDTOList = routeDTOListMapper.apply(routeList);

            int counter = 0;
            for (RouteResponseDTO routeResponseDTO : routeResponseDTOList) {
                Optional<Aircraft> aircraft = aircraftRepository.findById(routeList.get(counter).getAircraft().getId());
                if (aircraft.isEmpty()) {
                    List<ResponseErrorDTO> responseErrorList = responseMessageService.responseErrorListByCode("AIR-001");
                    return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
                } else {
                    AircraftDTO aircraftDTO = aircraftDTOMapper.apply(aircraft.get());
                    routeResponseDTO.setAircraftDTO(aircraftDTO);
                    counter++;
                }
            }

            ResponseDTO<RouteResponseDTO> response = new ResponseDTO<>();
            response.setErrorList(List.of());
            response.setResponse(routeResponseDTOList);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }
}
