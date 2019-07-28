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
import org.sergei.reservation.jpa.repository.CustomerRepository;
import org.sergei.reservation.rest.dto.PassengerResponseDTO;
import org.sergei.reservation.rest.dto.PassengerUpdateRequestDTO;
import org.sergei.reservation.rest.dto.mappers.PassengerDTOListMapper;
import org.sergei.reservation.rest.dto.mappers.PassengerDTOMapper;
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
public class PassengerServiceImpl implements PassengerService {

    private final CustomerRepository customerRepository;
    private final PassengerDTOMapper passengerDTOMapper;
    private final PassengerDTOListMapper passengerDTOListMapper;

    @Autowired
    public PassengerServiceImpl(CustomerRepository customerRepository,
                                PassengerDTOMapper passengerDTOMapper,
                                PassengerDTOListMapper passengerDTOListMapper) {
        this.customerRepository = customerRepository;
        this.passengerDTOMapper = passengerDTOMapper;
        this.passengerDTOListMapper = passengerDTOListMapper;
    }

    /**
     * Find passenger by ID
     *
     * @param passengerId get passenger ID as a parameter
     * @return passenger
     */
    @Override
    public ResponseEntity<ResponseDTO<PassengerResponseDTO>> findOne(Long passengerId) {
        Optional<Passenger> customer = customerRepository.findById(passengerId);
        if (customer.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
        } else {
            PassengerResponseDTO passengerResponseDTO = passengerDTOMapper.apply(customer.get());
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
    public ResponseEntity<ResponseDTO<PassengerResponseDTO>> findAll() {
        List<Passenger> passengerList = customerRepository.findAll();
        if (passengerList.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
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
        List<String> passengerIds = customerRepository.findIdsOfAllCustomers();
        if (passengerIds.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
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
        Page<Passenger> customersPage = customerRepository.findAll(PageRequest.of(page, size));
        if (customersPage.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
        } else {
            List<PassengerResponseDTO> passengerResponseDTOList = passengerDTOListMapper.apply(customersPage.getContent());
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

        Optional<Passenger> customer = customerRepository.findById(request.getPassengerId());
        if (customer.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
        } else {
            customer.get().setFirstName(request.getCustomer().getFirstName());
            customer.get().setLastName(request.getCustomer().getLastName());
            customer.get().setAge(request.getCustomer().getAge());
            Passenger updatedPassenger = customerRepository.save(customer.get());

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
        Passenger savedPassenger = customerRepository.save(passenger);

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
        Optional<Passenger> customer = customerRepository.findById(passengerId);
        if (customer.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
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

            Passenger savedPassenger = customerRepository.save(customer.get());
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
        Optional<Passenger> customer = customerRepository.findById(passengerId);
        if (customer.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
        } else {
            customerRepository.delete(customer.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
