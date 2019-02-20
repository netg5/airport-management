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

import org.sergei.library.Constants;
import org.sergei.library.exceptions.ResourceNotFoundException;
import org.sergei.reservationservice.model.Aircraft;
import org.sergei.reservationservice.repository.AircraftRepository;
import org.sergei.reservationservice.rest.dto.AircraftDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.sergei.reservationservice.util.ObjectMapperUtil.*;

/**
 * @author Sergei Visotsky
 */
@Service
public class AircraftService implements IAircraftService<AircraftDTO> {

    private final AircraftRepository aircraftRepository;

    @Autowired
    public AircraftService(AircraftRepository aircraftRepository) {
        this.aircraftRepository = aircraftRepository;
    }

    /**
     * Find aircraftDTO by ID
     *
     * @param aircraftId gets aircraftDTO ID as parameter
     * @return aircraftDTO DTO
     */
    @Override
    public AircraftDTO findOne(Long aircraftId) {
        Aircraft aircraft = aircraftRepository.findById(aircraftId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(Constants.AIRCRAFT_NOT_FOUND)
                );
        return map(aircraft, AircraftDTO.class);
    }

    /**
     * Get aircraft by multiple parameters
     *
     * @param request Request of parameters
     * @return aircraft DTO
     */
    @Override
    public AircraftDTO findOneByMultipleParams(HttpServletRequest request) {
        Enumeration enumeration = request.getParameterNames();
        Map<String, Object> requestParams = new HashMap<>();
        while (enumeration.hasMoreElements()) {
            String paramName = String.valueOf(enumeration.nextElement());
            requestParams.put(paramName, request.getParameter(paramName));
        }
        Aircraft aircraft = aircraftRepository.findAircraftByMultipleParams(
                String.valueOf(requestParams.get("name")),
                Double.valueOf(String.valueOf(requestParams.get("weight"))),
                Integer.valueOf(String.valueOf(requestParams.get("passengers"))),
                String.valueOf(requestParams.get("model"))
        ).orElseThrow(
                () -> new ResourceNotFoundException(Constants.AIRCRAFT_NOT_FOUND)
        );
        return map(aircraft, AircraftDTO.class);
    }

    /**
     * Find all aircrafts
     *
     * @return list of Aircraft DTO
     */
    @Override
    public List<AircraftDTO> findAll() {
        List<Aircraft> aircraftList = aircraftRepository.findAll();
        return mapAll(aircraftList, AircraftDTO.class);
    }

    /**
     * Find all aircrafts paginated
     *
     * @param page which should be shown
     * @param size number of elements per page
     * @return collection of aircrafts
     */
    @Override
    public Page<AircraftDTO> findAllPaginated(int page, int size) {
        Page<Aircraft> aircraftList = aircraftRepository.findAll(PageRequest.of(page, size));
        return mapAllPages(aircraftList, AircraftDTO.class);
    }

    /**
     * Save aircraftDTO
     *
     * @param aircraftDTO get aircraftDTO DTO as a parameter
     * @return Aircraft DTO
     */
    @Override
    public AircraftDTO save(AircraftDTO aircraftDTO) {
        Aircraft aircraft = map(aircraftDTO, Aircraft.class);
        Aircraft savedAircraft = aircraftRepository.save(aircraft);
        return map(savedAircraft, AircraftDTO.class);
    }

    /**
     * Update aicraft by ID
     *
     * @param aircraftId  get aircraftDTO ID as a parameter
     * @param aircraftDTO get aircraftDTO DTO as a parameter
     * @return aircraftDTO DTO
     */
    @Override
    public AircraftDTO update(Long aircraftId, AircraftDTO aircraftDTO) {
        aircraftDTO.setAircraftId(aircraftId);

        Aircraft aircraft = aircraftRepository.findById(aircraftId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(Constants.AIRCRAFT_NOT_FOUND)
                );

        aircraft.setAircraftId(aircraftId);
        aircraft.setAircraftName(aircraftDTO.getAircraftName());
        aircraft.setModel(aircraftDTO.getModel());
        aircraft.setAircraftWeight(aircraftDTO.getAircraftWeight());
        aircraft.setMaxPassengers(aircraftDTO.getMaxPassengers());

        aircraftRepository.save(aircraft);

        return aircraftDTO;
    }

    /**
     * Method to update one field of the aircraft
     *
     * @param aircraftId ID for aircraft that should be updated
     * @param params     list of params that should be updated
     * @return updated aircraft
     */
    @Override
    public AircraftDTO patch(Long aircraftId, Map<String, Object> params) {
        Aircraft aircraft = aircraftRepository.findById(aircraftId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(Constants.AIRCRAFT_NOT_FOUND)
                );
        if (params.get("model") != null) {
            aircraft.setModel(String.valueOf(params.get("model")));
        }
        if (params.get("aircraftName") != null) {
            aircraft.setAircraftName(String.valueOf(params.get("aircraftName")));
        }
        if (params.get("aircraftWeight") != null) {
            aircraft.setAircraftWeight(Double.valueOf(String.valueOf(params.get("aircraftWeight"))));
        }
        if (params.get("maxPassengers") != null) {
            aircraft.setMaxPassengers(Integer.valueOf(String.valueOf(params.get("maxPassengers"))));
        }
        return map(aircraftRepository.save(aircraft), AircraftDTO.class);
    }

    /**
     * Delete aircraftDTO by ID
     *
     * @param aircraftId get aircraftDTO ID as a parameter
     * @return aircraftDTO DTO as a response
     */
    @Override
    public AircraftDTO delete(Long aircraftId) {
        Aircraft aircraft = aircraftRepository.findById(aircraftId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(Constants.AIRCRAFT_NOT_FOUND)
                );
        aircraftRepository.delete(aircraft);
        return map(aircraft, AircraftDTO.class);
    }
}