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

import org.sergei.reports.jpa.model.AircraftReport;
import org.sergei.reports.jpa.model.Reservation;
import org.sergei.reports.jpa.repository.AircraftReportRepository;
import org.sergei.reports.jpa.repository.ReservationRepository;
import org.sergei.reports.rest.dto.AircraftReportDTO;
import org.sergei.reports.rest.dto.ReservationDTO;
import org.sergei.reports.rest.dto.mappers.AircraftReportDTOListMapper;
import org.sergei.reports.rest.dto.mappers.AircraftReportDTOMapper;
import org.sergei.reports.rest.dto.mappers.ReservationDTOListMapper;
import org.sergei.reports.rest.dto.response.ResponseDTO;
import org.sergei.reports.rest.dto.response.ResponseErrorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Sergei Visotsky
 */
@Service
public class AircraftReportServiceImpl implements AircraftReportService {

    private final AircraftReportRepository aircraftReportRepository;
    private final ReservationRepository reservationRepository;
    private final AircraftReportDTOMapper aircraftReportDTOMapper;
    private final AircraftReportDTOListMapper aircraftReportDTOListMapper;
    private final ReservationDTOListMapper reservationDTOListMapper;
    private final ResponseMessageService responseMessageService;

    @Autowired
    public AircraftReportServiceImpl(AircraftReportRepository aircraftReportRepository,
                                     ReservationRepository reservationRepository,
                                     AircraftReportDTOMapper aircraftReportDTOMapper,
                                     AircraftReportDTOListMapper aircraftReportDTOListMapper,
                                     ReservationDTOListMapper reservationDTOListMapper,
                                     ResponseMessageService responseMessageService) {
        this.aircraftReportRepository = aircraftReportRepository;
        this.reservationRepository = reservationRepository;
        this.aircraftReportDTOMapper = aircraftReportDTOMapper;
        this.aircraftReportDTOListMapper = aircraftReportDTOListMapper;
        this.reservationDTOListMapper = reservationDTOListMapper;
        this.responseMessageService = responseMessageService;
    }

    /**
     * Find all existing reports
     *
     * @param page number of page to ssow
     * @param size number of elements per page
     * @return list of existing reports
     */
    @Override
    public ResponseEntity<ResponseDTO<AircraftReportDTO>> findAll(int page, int size) {
        Page<AircraftReport> aircraftReports =
                aircraftReportRepository.findAll(PageRequest.of(page, size));
        if (aircraftReports.isEmpty()) {
            List<ResponseErrorDTO> responseErrorList = responseMessageService.responseErrorListByCode("AIR-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
        } else {
            List<AircraftReportDTO> aircraftReportDTOList = aircraftReportDTOListMapper.apply(aircraftReports.getContent());
            for (AircraftReportDTO aircraftReport : aircraftReportDTOList) {
                List<Reservation> reservationList =
                        reservationRepository.findAllByRouteId(aircraftReport.getRouteId());
                if (reservationList.isEmpty()) {
                    List<ResponseErrorDTO> responseErrorList = responseMessageService.responseErrorListByCode("RES-001");
                    return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
                } else {
                    List<ReservationDTO> reservationDTOList = reservationDTOListMapper.apply(reservationList);
                    aircraftReport.setReservations(reservationDTOList);
                }
            }

            ResponseDTO<AircraftReportDTO> response = new ResponseDTO<>();
            response.setErrorList(List.of());
            response.setResponse(aircraftReportDTOList);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    /**
     * Find one report by ID
     *
     * @param aircraftId identity of the report that should be found
     * @return Report entity
     */
    @Override
    public ResponseEntity<ResponseDTO<AircraftReportDTO>> findById(Long aircraftId) {
        Optional<AircraftReport> aircraftReport = aircraftReportRepository.findById(aircraftId);
        if (aircraftReport.isEmpty()) {
            List<ResponseErrorDTO> responseErrorList = responseMessageService.responseErrorListByCode("AIR-001");
            return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
        } else {
            AircraftReportDTO aircraftReportDTO = aircraftReportDTOMapper.apply(aircraftReport.get());
            List<Reservation> reservationList = reservationRepository.findAllByRouteId(aircraftReport.get().getRouteId());
            if (reservationList.isEmpty()) {
                List<ResponseErrorDTO> responseErrorList = responseMessageService.responseErrorListByCode("RES-001");
                return new ResponseEntity<>(new ResponseDTO<>(responseErrorList, List.of()), HttpStatus.NOT_FOUND);
            } else {
                List<ReservationDTO> reservationDTOList = reservationDTOListMapper.apply(reservationList);
                aircraftReportDTO.setReservations(reservationDTOList);

                ResponseDTO<AircraftReportDTO> response = new ResponseDTO<>();
                response.setErrorList(List.of());
                response.setResponse(List.of(aircraftReportDTO));

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
    }
}
