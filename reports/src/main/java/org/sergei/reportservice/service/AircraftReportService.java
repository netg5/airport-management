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

package org.sergei.reportservice.service;

import org.sergei.common.Constants;
import org.sergei.common.exceptions.ResourceNotFoundException;
import org.sergei.reportservice.jpa.model.AircraftReport;
import org.sergei.reportservice.jpa.model.Reservation;
import org.sergei.reportservice.jpa.repository.AircraftReportRepository;
import org.sergei.reportservice.jpa.repository.ReservationRepository;
import org.sergei.reportservice.rest.dto.AircraftReportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.sergei.common.ObjectMapperUtil.*;

/**
 * @author Sergei Visotsky
 */
@Service
public class AircraftReportService implements IReportService<AircraftReportDTO> {

    private final AircraftReportRepository aircraftReportRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public AircraftReportService(AircraftReportRepository aircraftReportRepository,
                                 ReservationRepository reservationRepository) {
        this.aircraftReportRepository = aircraftReportRepository;
        this.reservationRepository = reservationRepository;
    }

    /**
     * Find all existing reports
     *
     * @param page number of page to ssow
     * @param size number of elements per page
     * @return list of existing reports
     */
    @Override
    public Page<AircraftReportDTO> findAll(int page, int size) {
        Page<AircraftReport> aircraftReports =
                aircraftReportRepository.findAll(PageRequest.of(page, size));

        Page<AircraftReportDTO> aircraftReportDTOS =
                mapAllPages(aircraftReports, AircraftReportDTO.class);

        aircraftReportDTOS.forEach(reportDTO -> {
            List<Reservation> reservationList =
                    reservationRepository.findAllByRouteId(reportDTO.getRouteId());
            reportDTO.setReservations(reservationList);
        });
        return aircraftReportDTOS;
    }

    /**
     * Find one report by ID
     *
     * @param aircraftId identity of the report that should be found
     * @return Report entity
     */
    @Override
    public AircraftReportDTO findById(Long aircraftId) {
        AircraftReport aircraftReport = aircraftReportRepository.findById(aircraftId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(Constants.AIRCRAFT_NOT_FOUND)
                );
        List<Reservation> reservationList = reservationRepository.findAllByRouteId(aircraftReport.getRouteId());
        AircraftReportDTO aircraftReportDTO = map(aircraftReport, AircraftReportDTO.class);
        aircraftReportDTO.setReservations(reservationList);
        return aircraftReportDTO;
    }
}
