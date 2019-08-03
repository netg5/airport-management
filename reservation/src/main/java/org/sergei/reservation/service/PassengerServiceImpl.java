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

import org.sergei.reservation.jpa.model.Passenger;
import org.sergei.reservation.jpa.repository.PassengerRepository;
import org.sergei.reservation.rest.dto.PassengerResponseDTO;
import org.sergei.reservation.rest.dto.PassengerUpdateRequestDTO;
import org.sergei.reservation.rest.dto.mappers.PassengerDTOListMapper;
import org.sergei.reservation.rest.dto.mappers.PassengerDTOMapper;
import org.sergei.reservation.rest.dto.response.ResponseDTO;
import org.sergei.reservation.rest.dto.response.ResponseErrorDTO;
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
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;
    private final PassengerDTOMapper passengerDTOMapper;
    private final PassengerDTOListMapper passengerDTOListMapper;
    private final ResponseMessageService responseMessageService;

    @Autowired
    public PassengerServiceImpl(PassengerRepository passengerRepository,
                                PassengerDTOMapper passengerDTOMapper,
                                PassengerDTOListMapper passengerDTOListMapper,
                                ResponseMessageService responseMessageService) {
        this.passengerRepository = passengerRepository;
        this.passengerDTOMapper = passengerDTOMapper;
        this.passengerDTOListMapper = passengerDTOListMapper;
        this.responseMessageService = responseMessageService;
    }

    /**
     * Find passenger by ID
     *
     * @param passengerId get passenger ID as a parameter
     * @return passenger
     */
    @Override
    public ResponseEntity<ResponseDTO<PassengerResponseDTO>> findOne(Long passengerId) {
        Optional<Passenger> passenger = passengerRepository.findById(passengerId);
        if (passenger.isEmpty()) {
            List<ResponseErrorDTO> responseErrorList = responseMessageService.responseErrorListByCode("PAS-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
        } else {
            PassengerResponseDTO passengerResponseDTO = passengerDTOMapper.apply(passenger.get());
            ResponseDTO<PassengerResponseDTO> response = new ResponseDTO<>();
            response.setErrorList(List.of());
            response.setResponse(List.of(passengerResponseDTO));
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    /**
     * Find all customers
     *
     * @return list of customers
     */
    @Override
    public ResponseEntity<ResponseDTO<PassengerResponseDTO>> findAll(int page, int size) {
        List<Passenger> passengerList = passengerRepository.findAll();
        if (passengerList.isEmpty()) {
            List<ResponseErrorDTO> responseErrorList = responseMessageService.responseErrorListByCode("PAS-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
        } else {
            List<PassengerResponseDTO> passengerResponseDTOList = passengerDTOListMapper.apply(passengerList);
            ResponseDTO<PassengerResponseDTO> response = new ResponseDTO<>();
            response.setErrorList(List.of());
            response.setResponse(passengerResponseDTOList);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    /**
     * Find ID of each passenger in one JSON response as a list
     *
     * @return list of IDs
     */
    @Override
    public ResponseEntity<ResponseDTO<String>> findIdsOfAllCustomers() {
        List<String> passengerIds = passengerRepository.findIdsOfAllCustomers();
        if (passengerIds.isEmpty()) {
            List<ResponseErrorDTO> responseErrorList = responseMessageService.responseErrorListByCode("PAS-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
        } else {
            ResponseDTO<String> response = new ResponseDTO<>();
            response.setErrorList(List.of());
            response.setResponse(passengerIds);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    /**
     * Find all customers with pagination
     *
     * @param page how many pages to show
     * @param size number of elements per page
     * @return list with entities
     */
    @Override
    public ResponseEntity<ResponseDTO<PassengerResponseDTO>> findAllPaginated(int page, int size) {
        Page<Passenger> passengersPage = passengerRepository.findAll(PageRequest.of(page, size));
        if (passengersPage.isEmpty()) {
            List<ResponseErrorDTO> responseErrorList = responseMessageService.responseErrorListByCode("PAS-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
        } else {
            List<PassengerResponseDTO> passengerResponseDTOList = passengerDTOListMapper.apply(passengersPage.getContent());
            ResponseDTO<PassengerResponseDTO> response = new ResponseDTO<>();
            response.setErrorList(List.of());
            response.setResponse(passengerResponseDTOList);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    /**
     * Method to update customerDTO details
     *
     * @param request with passenger ID and cutomer DTO
     * @return response with passenger DTO
     */
    @Override
    public ResponseEntity<ResponseDTO<PassengerResponseDTO>> update(PassengerUpdateRequestDTO request) {

        Optional<Passenger> passenger = passengerRepository.findById(request.getPassengerId());
        if (passenger.isEmpty()) {
            List<ResponseErrorDTO> responseErrorList = responseMessageService.responseErrorListByCode("PAS-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
        } else {
            passenger.get().setFirstName(request.getCustomer().getFirstName());
            passenger.get().setLastName(request.getCustomer().getLastName());
            passenger.get().setAge(request.getCustomer().getAge());
            Passenger updatedPassenger = passengerRepository.save(passenger.get());

            PassengerResponseDTO passengerResponseDTOResp = passengerDTOMapper.apply(updatedPassenger);
            ResponseDTO<PassengerResponseDTO> response = new ResponseDTO<>();
            response.setErrorList(List.of());
            response.setResponse(List.of(passengerResponseDTOResp));

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
    }

    /**
     * Save passengerResponseDTO
     *
     * @param passengerResponseDTO gets passengerResponseDTO DTO as a parameter
     * @return passengerResponseDTO DTO as a response
     */
    @Override
    public ResponseEntity<ResponseDTO<PassengerResponseDTO>> save(PassengerResponseDTO passengerResponseDTO) {
        Passenger passenger = new Passenger();

        passenger.setId(passengerResponseDTO.getPassengerId());
        passenger.setFirstName(passengerResponseDTO.getFirstName());
        passenger.setLastName(passengerResponseDTO.getLastName());
        passenger.setAge(passengerResponseDTO.getAge());
        Passenger savedPassenger = passengerRepository.save(passenger);

        PassengerResponseDTO passengerResponseDTOResp = passengerDTOMapper.apply(savedPassenger);
        ResponseDTO<PassengerResponseDTO> response = new ResponseDTO<>();
        response.setErrorList(List.of());
        response.setResponse(List.of(passengerResponseDTOResp));

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Patch specific field of the passenger
     *
     * @param passengerId passenger ID which field should be updated
     * @param params      List of params that should be updated
     * @return patched passenger DTO
     */
    @Override
    public ResponseEntity<ResponseDTO<PassengerResponseDTO>> patch(Long passengerId, Map<String, Object> params) {
        Optional<Passenger> customer = passengerRepository.findById(passengerId);
        if (customer.isEmpty()) {
            List<ResponseErrorDTO> responseErrorList = responseMessageService.responseErrorListByCode("PAS-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
        } else {
            if (params.get("firstName") != null) {
                customer.get().setFirstName(String.valueOf(params.get("firstName")));
            }
            if (params.get("lastName") != null) {
                customer.get().setLastName(String.valueOf(params.get("lastName")));
            }
            if (params.get("age") != null) {
                customer.get().setAge(Integer.valueOf(String.valueOf(params.get("age"))));
            }

            Passenger savedPassenger = passengerRepository.save(customer.get());
            PassengerResponseDTO passengerResponseDTOResp = passengerDTOMapper.apply(savedPassenger);
            ResponseDTO<PassengerResponseDTO> response = new ResponseDTO<>();
            response.setErrorList(List.of());
            response.setResponse(List.of(passengerResponseDTOResp));

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    /**
     * Delete passenger
     *
     * @param passengerId get passenger ID as a parameter
     * @return passenger DTO as a response
     */
    @Override
    public ResponseEntity<ResponseDTO<PassengerResponseDTO>> delete(Long passengerId) {
        Optional<Passenger> passenger = passengerRepository.findById(passengerId);
        if (passenger.isEmpty()) {
            List<ResponseErrorDTO> responseErrorList = responseMessageService.responseErrorListByCode("PAS-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
        } else {
            passengerRepository.delete(passenger.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
