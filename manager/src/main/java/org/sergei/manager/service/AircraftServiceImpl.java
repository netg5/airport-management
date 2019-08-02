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

import org.sergei.manager.jpa.model.Aircraft;
import org.sergei.manager.jpa.model.Manufacturer;
import org.sergei.manager.jpa.repository.AircraftRepository;
import org.sergei.manager.rest.dto.AircraftDTO;
import org.sergei.manager.rest.dto.ManufacturerDTO;
import org.sergei.manager.rest.dto.mappers.AircraftDTOListMapper;
import org.sergei.manager.rest.dto.mappers.AircraftDTOMapper;
import org.sergei.manager.rest.dto.request.AircraftRequestDTO;
import org.sergei.manager.rest.dto.response.ResponseDTO;
import org.sergei.manager.rest.dto.response.ResponseErrorDTO;
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
    private final MessageService messageService;
    private final ManufacturerService manufacturerService;

    @Autowired
    public AircraftServiceImpl(AircraftRepository aircraftRepository,
                               AircraftDTOMapper aircraftDTOMapper,
                               AircraftDTOListMapper aircraftDTOListMapper,
                               MessageService messageService,
                               ManufacturerService manufacturerService) {
        this.aircraftRepository = aircraftRepository;
        this.aircraftDTOMapper = aircraftDTOMapper;
        this.aircraftDTOListMapper = aircraftDTOListMapper;
        this.messageService = messageService;
        this.manufacturerService = manufacturerService;
    }

    /**
     * Find aircraftId by ID
     *
     * @param request gets aircraftId ID as parameter
     * @return aircraftId DTO
     */
    @Override
    public ResponseEntity<ResponseDTO<AircraftDTO>> findById(AircraftRequestDTO request) {
        Long aircraftId = request.getAircraftId();
        if (aircraftId == null) {
            List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("RP-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
        } else {
            Optional<Aircraft> aircraft = aircraftRepository.findById(aircraftId);
            if (aircraft.isEmpty()) {
                List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("AIR-001");
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
            List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("AIR-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
        } else {
            List<AircraftDTO> aircraftDTOList = aircraftDTOListMapper.apply(aircraftList);

            ResponseDTO<AircraftDTO> response = new ResponseDTO<>();
            response.setErrorList(List.of());
            response.setResponse(aircraftDTOList);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    /**
     * Save aircraftId
     *
     * @param request get aircraftId DTO as a parameter
     * @return Aircraft DTO
     */
    @Override
    public ResponseEntity<ResponseDTO<AircraftDTO>> save(AircraftDTO request) {
        Integer exploitationPeriod = request.getExploitationPeriod();
        if (exploitationPeriod >= 10) {
            List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("AEP-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.OK);
        } else {
            Aircraft aircraft = aircraftModelMapper(request);
            Aircraft savedAircraft = aircraftRepository.save(aircraft);
            AircraftDTO aircraftDTOResponse = aircraftDTOMapper.apply(savedAircraft);
            return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of(aircraftDTOResponse)), HttpStatus.CREATED);
        }
    }

    /**
     * Update aircraftId by its ID
     *
     * @param request aircraftId DTO request with ID
     * @return response with updated aircraftId
     */
    @Override
    public ResponseEntity<ResponseDTO<AircraftDTO>> update(AircraftDTO request) {
        Long aircraftId = request.getAircraftId();
        Integer exploitationPeriod = request.getExploitationPeriod();
        if (aircraftId == null) {
            List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("RP-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
        } else {
            if (exploitationPeriod >= 10) {
                List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("AEP-001");
                return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.OK);
            } else {
                Optional<Aircraft> aircraft = aircraftRepository.findById(aircraftId);
                if (aircraft.isEmpty()) {
                    List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("AIR-001");
                    return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
                } else {
                    Aircraft aircraftUpdated = aircraftModelMapper(request);
                    Aircraft savedAircraft = aircraftRepository.save(aircraftUpdated);
                    AircraftDTO aircraftDTOResponse = aircraftDTOMapper.apply(savedAircraft);
                    return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of(aircraftDTOResponse)), HttpStatus.OK);
                }
            }
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
        if (aircraftId == null) {
            List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("RP-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
        } else {
            Optional<Aircraft> aircraft = aircraftRepository.findById(aircraftId);
            if (aircraft.isEmpty()) {
                List<ResponseErrorDTO> responseErrorList = messageService.responseErrorListByCode("AIR-001");
                return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
            } else {
                aircraftRepository.delete(aircraft.get());
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }
    }

    private Aircraft aircraftModelMapper(AircraftDTO aircraftDTO) {
        return Aircraft.builder()
                .id(aircraftDTO.getAircraftId())
                .registrationNumber(aircraftDTO.getRegistrationNumber())
                .modelNumber(aircraftDTO.getModelNumber())
                .aircraftName(aircraftDTO.getAircraftName())
                .capacity(aircraftDTO.getCapacity())
                .weight(aircraftDTO.getWeight())
                .exploitationPeriod(aircraftDTO.getExploitationPeriod())
                .manufacturer(manufacturerModelMapper(aircraftDTO.getManufacturer()))
                .build();
    }

    private Manufacturer manufacturerModelMapper(ManufacturerDTO request) {
        return Manufacturer.builder()
                .id(request.getId())
                .manufacturerName(request.getManufacturerName())
                .manufacturerCode(request.getManufacturerCode())
                .location(request.getLocation())
                .build();
    }
}