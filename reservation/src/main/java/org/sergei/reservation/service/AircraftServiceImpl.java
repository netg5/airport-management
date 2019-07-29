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
import org.sergei.reservation.rest.dto.AircraftResponseDTO;
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

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public ResponseEntity<ResponseDTO<AircraftResponseDTO>> findOne(Long aircraftId) {
        Optional<Aircraft> aircraft = aircraftRepository.findById(aircraftId);

        if (aircraft.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
        } else {
            AircraftResponseDTO aircraftDTO = aircraftDTOMapper.apply(aircraft.get());
            ResponseDTO<AircraftResponseDTO> response = new ResponseDTO<>();
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
    public ResponseEntity<ResponseDTO<AircraftResponseDTO>> findAll() {
        List<Aircraft> aircraftList = aircraftRepository.findAll();
        if (aircraftList.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
        } else {
            List<AircraftResponseDTO> aircraftDTOList = aircraftDTOListMapper.apply(aircraftList);

            ResponseDTO<AircraftResponseDTO> response = new ResponseDTO<>();
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
    public ResponseEntity<ResponseDTO<AircraftResponseDTO>> findAllPaginated(int page, int size) {
        Page<Aircraft> aircraftList = aircraftRepository.findAll(PageRequest.of(page, size));
        if (aircraftList.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
        } else {
            List<AircraftResponseDTO> aircraftDTOList = aircraftDTOListMapper.apply(aircraftList.getContent());

            ResponseDTO<AircraftResponseDTO> response = new ResponseDTO<>();
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
    public ResponseEntity<ResponseDTO<AircraftResponseDTO>> save(AircraftResponseDTO aircraftDTO) {
        Aircraft aircraft = new Aircraft();

        aircraft.setId(aircraftDTO.getAircraftId());
        aircraft.setManufacturerCode(aircraft.getManufacturerCode());
        aircraft.setModelNumber(aircraft.getModelNumber());
        aircraft.setAircraftName(aircraft.getAircraftName());
        aircraft.setCapacity(aircraft.getCapacity());
        aircraft.setWeight(aircraft.getWeight());
        aircraft.setExploitationPeriod(aircraft.getExploitationPeriod());

        Aircraft savedAircraft = aircraftRepository.save(aircraft);

        AircraftResponseDTO aircraftDTOResponse = aircraftDTOMapper.apply(savedAircraft);

        ResponseDTO<AircraftResponseDTO> response = new ResponseDTO<>();
        response.setErrorList(List.of());
        response.setResponse(List.of(aircraftDTOResponse));

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Update aircraftId by its ID
     *
     * @param request aircraftId DTO request with ID
     * @return response with updated aircraftId
     */
    @Override
    public ResponseEntity<ResponseDTO<AircraftResponseDTO>> update(AircraftUpdateRequestDTO request) {

        Optional<Aircraft> aircraft = aircraftRepository.findById(request.getAircraftId());

        if (aircraft.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
        } else {
            aircraft.get().setId(request.getAircraft().getAircraftId());
            aircraft.get().setManufacturerCode(request.getAircraft().getManufacturerCode());
            aircraft.get().setModelNumber(request.getAircraft().getModelNumber());
            aircraft.get().setAircraftName(request.getAircraft().getAircraftName());
            aircraft.get().setCapacity(request.getAircraft().getCapacity());
            aircraft.get().setWeight(request.getAircraft().getWeight());
            aircraft.get().setExploitationPeriod(request.getAircraft().getExploitationPeriod());

            Aircraft savedAircraft = aircraftRepository.save(aircraft.get());

            AircraftResponseDTO aircraftDTOResponse = aircraftDTOMapper.apply(savedAircraft);

            ResponseDTO<AircraftResponseDTO> response = new ResponseDTO<>();
            response.setErrorList(List.of());
            response.setResponse(List.of(aircraftDTOResponse));

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    /**
     * Method to update one field of the aircraftId
     *
     * @param aircraftId ID for aircraftId that should be updated
     * @param params     list of params that should be updated
     * @return updated aircraftId
     */
    @Override
    public ResponseEntity<ResponseDTO<AircraftResponseDTO>> patch(Long aircraftId, Map<String, Object> params) {
        Optional<Aircraft> aircraft = aircraftRepository.findById(aircraftId);

        if (aircraft.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
        } else {
            if (params.get("manufacturerCode") != null) {
                aircraft.get().setManufacturerCode(String.valueOf(params.get("manufacturerCode")));
            }
            if (params.get("ownerId") != null) {
//                aircraftId.get().(String.valueOf(params.get("modelNumber")));
            }
            if (params.get("registrationNumber") != null) {
                aircraft.get().setRegistrationNumber(String.valueOf(params.get("registrationNumber")));
            }
            if (params.get("modelNumber") != null) {
                aircraft.get().setModelNumber(String.valueOf(params.get("modelNumber")));
            }
            if (params.get("aircraftName") != null) {
                aircraft.get().setAircraftName(String.valueOf(params.get("aircraftName")));
            }
            if (params.get("capacity") != null) {
                aircraft.get().setCapacity(Integer.valueOf(String.valueOf(params.get("capacity"))));
            }
            if (params.get("weight") != null) {
                aircraft.get().setWeight(Double.valueOf(String.valueOf(params.get("weight"))));
            }
            if (params.get("exploitationPeriod") != null) {
                aircraft.get().setExploitationPeriod(Integer.valueOf(String.valueOf(params.get("exploitationPeriod"))));
            }
            Aircraft savedAircraft = aircraftRepository.save(aircraft.get());

            AircraftResponseDTO aircraftDTOResponse = aircraftDTOMapper.apply(savedAircraft);

            ResponseDTO<AircraftResponseDTO> response = new ResponseDTO<>();
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
    public ResponseEntity<ResponseDTO<AircraftResponseDTO>> delete(Long aircraftId) {
        Optional<Aircraft> aircraft = aircraftRepository.findById(aircraftId);

        if (aircraft.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
        } else {
            aircraftRepository.delete(aircraft.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}