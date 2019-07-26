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

package org.sergei.reports.service;

import org.sergei.reports.jpa.model.CustomerReport;
import org.sergei.reports.jpa.model.Reservation;
import org.sergei.reports.jpa.repository.CustomerReportRepository;
import org.sergei.reports.jpa.repository.ReservationRepository;
import org.sergei.reports.rest.dto.CustomerReportDTO;
import org.sergei.reports.rest.dto.mappers.CustomerReportDTOMapper;
import org.sergei.reports.rest.dto.mappers.ReservationDTOListMapper;
import org.sergei.reports.rest.dto.response.ResponseDTO;
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
public class CustomerReportServiceImpl implements CustomerReportService {

    private final CustomerReportRepository customerReportRepository;
    private final ReservationRepository reservationRepository;
    private final CustomerReportDTOMapper customerReportDTOMapper;
    private final ReservationDTOListMapper reservationDTOListMapper;

    @Autowired
    public CustomerReportServiceImpl(CustomerReportRepository customerReportRepository,
                                     ReservationRepository reservationRepository,
                                     CustomerReportDTOMapper customerReportDTOMapper,
                                     ReservationDTOListMapper reservationDTOListMapper) {
        this.customerReportRepository = customerReportRepository;
        this.reservationRepository = reservationRepository;
        this.customerReportDTOMapper = customerReportDTOMapper;
        this.reservationDTOListMapper = reservationDTOListMapper;
    }

    @Override
    public ResponseEntity<ResponseDTO<CustomerReportDTO>> findById(Long id) {
        Optional<CustomerReport> customerReport = customerReportRepository.findById(id);
        if (customerReport.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
        } else {
            CustomerReportDTO customerReportDTO = customerReportDTOMapper.apply(customerReport.get());
            List<Reservation> reservations =
                    reservationRepository.findAllByCustomerId(customerReportDTO.getCustomerId());
            if (reservations.isEmpty()) {
                return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
            } else {
                customerReportDTO.setReservations(reservationDTOListMapper.apply(reservations));

                ResponseDTO<CustomerReportDTO> response = new ResponseDTO<>();
                response.setErrorList(List.of());
                response.setResponse(List.of(customerReportDTO));

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
    }

    @Override
    public ResponseEntity<ResponseDTO<CustomerReportDTO>> findAll(int page, int size) {
        return null;
    }
}
