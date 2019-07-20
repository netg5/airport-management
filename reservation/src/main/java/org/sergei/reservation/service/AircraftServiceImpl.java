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
import org.sergei.reservation.rest.dto.AircraftUpdateRequestDTO;
import org.sergei.reservation.rest.dto.mappers.AircraftDTOListMapper;
import org.sergei.reservation.rest.dto.mappers.AircraftDTOMapper;
import org.sergei.reservation.rest.dto.response.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

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
     * Find aircraftId by ID
     *
     * @param aircraftId gets aircraftId ID as parameter
     * @return aircraftId DTO
     */
    @Override
    public ResponseEntity<ResponseDTO<AircraftDTO>> findOne(Long aircraftId) {
        Optional<Aircraft> aircraft = aircraftRepository.findById(aircraftId);

        if (aircraft.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            AircraftDTO aircraftDTO = aircraftDTOMapper.apply(aircraft.get());
            ResponseDTO<AircraftDTO> response = new ResponseDTO<>();
            response.setErrorList(List.of());
            response.setResponse(List.of(aircraftDTO));
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    /**
     * Get aircraft by multiple parameters
     *
     * @param request Request of parameters
     * @return aircraft DTO
     */
    @Override
    public ResponseEntity<ResponseDTO<AircraftDTO>> findOneByMultipleParams(HttpServletRequest request) {
        Enumeration<String> enumeration = request.getParameterNames();
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
            ResponseDTO<AircraftDTO> response = new ResponseDTO<>();
            response.setErrorList(List.of());
            response.setResponse(List.of(aircraftDTO));
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    /**
     * Find all aircrafts
     *
     * @return list of Aircraft DTO
     */
    @Override
    public ResponseEntity<ResponseDTO<AircraftDTO>> findAll() {
        List<Aircraft> aircraftList = aircraftRepository.findAll();
        if (aircraftList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            List<AircraftDTO> aircraftDTOList = aircraftDTOListMapper.apply(aircraftList);

            ResponseDTO<AircraftDTO> response = new ResponseDTO<>();
            response.setErrorList(List.of());
            response.setResponse(aircraftDTOList);

            return new ResponseEntity<>(response, HttpStatus.OK);
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
    public ResponseEntity<ResponseDTO<AircraftDTO>> findAllPaginated(int page, int size) {
        Page<Aircraft> aircraftList = aircraftRepository.findAll(PageRequest.of(page, size));
        if (aircraftList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            List<AircraftDTO> aircraftDTOList = aircraftDTOListMapper.apply(aircraftList.getContent());

            ResponseDTO<AircraftDTO> response = new ResponseDTO<>();
            response.setErrorList(List.of());
            response.setResponse(aircraftDTOList);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    /**
     * Save aircraftId
     *
     * @param aircraftDTO get aircraftId DTO as a parameter
     * @return Aircraft DTO
     */
    @Override
    public ResponseEntity<ResponseDTO<AircraftDTO>> save(AircraftDTO aircraftDTO) {
        Aircraft aircraft = new Aircraft();

        aircraft.setId(aircraftDTO.getAircraftId());
        aircraft.setAircraftName(aircraft.getAircraftName());
        aircraft.setAircraftWeight(aircraft.getAircraftWeight());
        aircraft.setMaxPassengers(aircraft.getMaxPassengers());
        aircraft.setModel(aircraft.getModel());

        Aircraft savedAircraft = aircraftRepository.save(aircraft);

        AircraftDTO aircraftDTOResponse = aircraftDTOMapper.apply(savedAircraft);

        ResponseDTO<AircraftDTO> response = new ResponseDTO<>();
        response.setErrorList(List.of());
        response.setResponse(List.of(aircraftDTOResponse));

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Update aircraft by its ID
     *
     * @param request aircraft DTO request with ID
     * @return response with updated aircraft
     */
    @Override
    public ResponseEntity<ResponseDTO<AircraftDTO>> update(AircraftUpdateRequestDTO request) {

        Optional<Aircraft> aircraft = aircraftRepository.findById(request.getAircraftId());

        if (aircraft.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            aircraft.get().setId(request.getAircraftId());
            aircraft.get().setAircraftName(request.getAircraft().getAircraftName());
            aircraft.get().setModel(request.getAircraft().getModel());
            aircraft.get().setAircraftWeight(request.getAircraft().getAircraftWeight());
            aircraft.get().setMaxPassengers(request.getAircraft().getMaxPassengers());

            Aircraft savedAircraft = aircraftRepository.save(aircraft.get());

            AircraftDTO aircraftDTOResponse = aircraftDTOMapper.apply(savedAircraft);

            ResponseDTO<AircraftDTO> response = new ResponseDTO<AircraftDTO>();
            response.setErrorList(List.of());
            response.setResponse(List.of(aircraftDTOResponse));

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    /**
     * Method to update one field of the aircraft
     *
     * @param aircraftId ID for aircraft that should be updated
     * @param params     list of params that should be updated
     * @return updated aircraft
     */
    @Override
    public ResponseEntity<ResponseDTO<AircraftDTO>> patch(Long aircraftId, Map<String, Object> params) {
        Optional<Aircraft> aircraft = aircraftRepository.findById(aircraftId);

        if (aircraft.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            if (params.get("model") != null) {
                aircraft.get().setModel(String.valueOf(params.get("model")));
            }
            if (params.get("aircraftName") != null) {
                aircraft.get().setAircraftName(String.valueOf(params.get("aircraftName")));
            }
            if (params.get("aircraftWeight") != null) {
                aircraft.get().setAircraftWeight(Double.valueOf(String.valueOf(params.get("aircraftWeight"))));
            }
            if (params.get("maxPassengers") != null) {
                aircraft.get().setMaxPassengers(Integer.valueOf(String.valueOf(params.get("maxPassengers"))));
            }
            Aircraft savedAircraft = aircraftRepository.save(aircraft.get());

            AircraftDTO aircraftDTOResponse = aircraftDTOMapper.apply(savedAircraft);

            ResponseDTO<AircraftDTO> response = new ResponseDTO<>();
            response.setErrorList(List.of());
            response.setResponse(List.of(aircraftDTOResponse));

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    /**
     * Delete aircraftId by ID
     *
     * @param aircraftId get aircraftId ID as a parameter
     * @return aircraftId DTO as a response
     */
    @Override
    public ResponseEntity<ResponseDTO<AircraftDTO>> delete(Long aircraftId) {
        Optional<Aircraft> aircraft = aircraftRepository.findById(aircraftId);

        if (aircraft.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            aircraftRepository.delete(aircraft.get());

            AircraftDTO aircraftDTOResponse = aircraftDTOMapper.apply(aircraft.get());

            ResponseDTO<AircraftDTO> response = new ResponseDTO<>();
            response.setErrorList(List.of());
            response.setResponse(List.of(aircraftDTOResponse));

            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);

        }
    }
}