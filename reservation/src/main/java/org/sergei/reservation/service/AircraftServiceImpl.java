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
import org.sergei.reservation.rest.dto.AircraftRequestDTO;
import org.sergei.reservation.rest.dto.mappers.AircraftDTOListMapper;
import org.sergei.reservation.rest.dto.mappers.AircraftDTOMapper;
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
@Service
public class AircraftServiceImpl implements AircraftService {

    private final AircraftRepository aircraftRepository;
    private final AircraftDTOMapper aircraftDTOMapper;
    private final AircraftDTOListMapper aircraftDTOListMapper;
    private final ResponseMessageService responseMessageService;

    @Autowired
    public AircraftServiceImpl(AircraftRepository aircraftRepository,
                               AircraftDTOMapper aircraftDTOMapper,
                               AircraftDTOListMapper aircraftDTOListMapper,
                               ResponseMessageService responseMessageService) {
        this.aircraftRepository = aircraftRepository;
        this.aircraftDTOMapper = aircraftDTOMapper;
        this.aircraftDTOListMapper = aircraftDTOListMapper;
        this.responseMessageService = responseMessageService;
    }

    /**
     * Find aircraftId by ID
     *
     * @param request gets aircraftId ID as parameter
     * @return aircraftId DTO
     */
    @Override
    public ResponseEntity<ResponseDTO<AircraftDTO>> findOne(AircraftRequestDTO request) {
        Long aircraftId = request.getAircraftId();
        if (aircraftId == null) {
            List<ResponseErrorDTO> responseErrorList = responseMessageService.responseErrorListByCode("RP-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
        } else {
            Optional<Aircraft> aircraft = aircraftRepository.findById(aircraftId);
            if (aircraft.isEmpty()) {
                List<ResponseErrorDTO> responseErrorList = responseMessageService.responseErrorListByCode("AIR-001");
                return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
            } else {
                AircraftDTO aircraftDTO = aircraftDTOMapper.apply(aircraft.get());
                ResponseDTO<AircraftDTO> response = new ResponseDTO<>();
                response.setErrorList(List.of());
                response.setResponse(List.of(aircraftDTO));
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
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
            List<ResponseErrorDTO> responseErrorList = responseMessageService.responseErrorListByCode("AIR-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
        } else {
            List<AircraftDTO> aircraftDTOList = aircraftDTOListMapper.apply(aircraftList);

            ResponseDTO<AircraftDTO> response = new ResponseDTO<>();
            response.setErrorList(List.of());
            response.setResponse(aircraftDTOList);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }
}