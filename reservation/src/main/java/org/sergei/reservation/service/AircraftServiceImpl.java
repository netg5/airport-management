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

import org.sergei.reservation.jpa.model.Aircraft;
import org.sergei.reservation.jpa.repository.AircraftRepository;
import org.sergei.reservation.rest.dto.AircraftDTO;
import org.sergei.reservation.rest.dto.mappers.AircraftDTOListMapper;
import org.sergei.reservation.rest.dto.mappers.AircraftDTOMapper;
import org.sergei.reservation.rest.exceptions.ResourceNotFoundException;
import org.sergei.reservation.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static org.sergei.reservation.utils.ObjectMapperUtil.map;
import static org.sergei.reservation.utils.ObjectMapperUtil.mapAllPages;

/**
 * @author Sergei Visotsky
 */
@Service
public class AircraftServiceImpl implements AircraftService {

    private final AircraftRepository aircraftRepository;
    private final AircraftDTOMapper aircraftDTOMapper;
    private final AircraftDTOListMapper aircraftDTOListMapper;

    @Autowired
    public AircraftServiceImpl(AircraftRepository aircraftRepository,
                               AircraftDTOMapper aircraftDTOMapper,
                               AircraftDTOListMapper aircraftDTOListMapper) {
        this.aircraftRepository = aircraftRepository;
        this.aircraftDTOMapper = aircraftDTOMapper;
        this.aircraftDTOListMapper = aircraftDTOListMapper;
    }

    /**
     * Find aircraftDTO by ID
     *
     * @param aircraftId gets aircraftDTO ID as parameter
     * @return aircraftDTO DTO
     */
    @Override
    public ResponseEntity<AircraftDTO> findOne(Long aircraftId) throws ResourceNotFoundException {
        Optional<Aircraft> aircraft = aircraftRepository.findById(aircraftId);

        if (aircraft.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            AircraftDTO aircraftDTO = aircraftDTOMapper.apply(aircraft.get());
            return new ResponseEntity<>(aircraftDTO, HttpStatus.OK);
        }
    }

    /**
     * Get aircraft by multiple parameters
     *
     * @param request Request of parameters
     * @return aircraft DTO
     */
    @Override
    public ResponseEntity<AircraftDTO> findOneByMultipleParams(HttpServletRequest request) throws ResourceNotFoundException {
        Enumeration enumeration = request.getParameterNames();
        Map<String, Object> requestParams = new HashMap<>();
        while (enumeration.hasMoreElements()) {
            String paramName = String.valueOf(enumeration.nextElement());
            requestParams.put(paramName, request.getParameter(paramName));
        }
        Optional<Aircraft> aircraft = aircraftRepository.findAircraftByMultipleParams(
                String.valueOf(requestParams.get("name")),
                Double.valueOf(String.valueOf(requestParams.get("weight"))),
                Integer.valueOf(String.valueOf(requestParams.get("passengers"))),
                String.valueOf(requestParams.get("model"))
        );

        if (aircraft.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            AircraftDTO aircraftDTO = aircraftDTOMapper.apply(aircraft.get());
            return new ResponseEntity<>(aircraftDTO, HttpStatus.OK);
        }
    }

    /**
     * Find all aircrafts
     *
     * @return list of Aircraft DTO
     */
    @Override
    public ResponseEntity<List<AircraftDTO>> findAll() throws ResourceNotFoundException {
        List<Aircraft> aircraftList = aircraftRepository.findAll();
        if (aircraftList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            List<AircraftDTO> aircraftDTOList = aircraftDTOListMapper.apply(aircraftList);
            return new ResponseEntity<>(aircraftDTOList, HttpStatus.OK);
        }
    }

    /**
     * Find all aircrafts paginated
     *
     * @param page which should be shown
     * @param size number of elements per page
     * @return collection of aircrafts
     */
    @Override
    public ResponseEntity<Page<AircraftDTO>> findAllPaginated(int page, int size) throws ResourceNotFoundException {
        Page<Aircraft> aircraftList = aircraftRepository.findAll(PageRequest.of(page, size));
        if (aircraftList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
//            Page<AircraftDTO> aircraftDTOList = aircraftDTOListMapper.apply(aircraftList);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    /**
     * Save aircraftDTO
     *
     * @param aircraftDTO get aircraftDTO DTO as a parameter
     * @return Aircraft DTO
     */
    @Override
    public ResponseEntity<AircraftDTO> save(AircraftDTO aircraftDTO) throws ResourceNotFoundException {
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
    public ResponseEntity<AircraftDTO> update(Long aircraftId, AircraftDTO aircraftDTO) throws ResourceNotFoundException {
        aircraftDTO.setAircraftId(aircraftId);

        Aircraft aircraft = aircraftRepository.findById(aircraftId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(Constants.AIRCRAFT_NOT_FOUND)
                );

        aircraft.setId(aircraftId);
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
    public ResponseEntity<AircraftDTO> patch(Long aircraftId, Map<String, Object> params) throws ResourceNotFoundException {
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
    public ResponseEntity<AircraftDTO> delete(Long aircraftId) throws ResourceNotFoundException {
        Aircraft aircraft = aircraftRepository.findById(aircraftId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(Constants.AIRCRAFT_NOT_FOUND)
                );
        aircraftRepository.delete(aircraft);
        return map(aircraft, AircraftDTO.class);
    }
}