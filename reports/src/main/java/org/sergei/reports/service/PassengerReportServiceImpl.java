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

import org.sergei.reports.jpa.model.PassengerReport;
import org.sergei.reports.jpa.model.Reservation;
import org.sergei.reports.jpa.repository.PassengerReportRepository;
import org.sergei.reports.jpa.repository.ReservationRepository;
import org.sergei.reports.rest.dto.PassengerReportDTO;
import org.sergei.reports.rest.dto.mappers.PassengerReportDTOMapper;
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
public class PassengerReportServiceImpl implements PassengerReportService {

    private final PassengerReportRepository passengerReportRepository;
    private final ReservationRepository reservationRepository;
    private final PassengerReportDTOMapper passengerReportDTOMapper;
    private final ReservationDTOListMapper reservationDTOListMapper;

    @Autowired
    public PassengerReportServiceImpl(PassengerReportRepository passengerReportRepository,
                                      ReservationRepository reservationRepository,
                                      PassengerReportDTOMapper passengerReportDTOMapper,
                                      ReservationDTOListMapper reservationDTOListMapper) {
        this.passengerReportRepository = passengerReportRepository;
        this.reservationRepository = reservationRepository;
        this.passengerReportDTOMapper = passengerReportDTOMapper;
        this.reservationDTOListMapper = reservationDTOListMapper;
    }

    @Override
    public ResponseEntity<ResponseDTO<PassengerReportDTO>> findById(Long id) {
        Optional<PassengerReport> passengerReport = passengerReportRepository.findById(id);
        if (passengerReport.isEmpty()) {
            return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
        } else {
            PassengerReportDTO passengerReportDTO = passengerReportDTOMapper.apply(passengerReport.get());
            List<Reservation> reservations =
                    reservationRepository.findAllByCustomerId(passengerReportDTO.getPassengerId());
            if (reservations.isEmpty()) {
                return new ResponseEntity<>(new ResponseDTO<>(List.of(), List.of()), HttpStatus.NOT_FOUND);
            } else {
                passengerReportDTO.setReservations(reservationDTOListMapper.apply(reservations));

                ResponseDTO<PassengerReportDTO> response = new ResponseDTO<>();
                response.setErrorList(List.of());
                response.setResponse(List.of(passengerReportDTO));

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
    }

    @Override
    public ResponseEntity<ResponseDTO<PassengerReportDTO>> findAll(int page, int size) {
        return null;
    }
}
