/*
 * Copyright 2018-2019 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.sergei.reportservice.service;

import org.sergei.reportservice.dto.AircraftReportDTO;
import org.sergei.reportservice.exceptions.ResourceNotFoundException;
import org.sergei.reportservice.model.AircraftReport;
import org.sergei.reportservice.model.Reservation;
import org.sergei.reportservice.repository.AircraftReportRepository;
import org.sergei.reportservice.repository.ReservationRepository;
import org.sergei.reportservice.util.ObjectMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<AircraftReportDTO> findAll() {
        return null;
    }

    @Override
    public AircraftReportDTO findById(Long aircraftId) {
        AircraftReport aircraftReport = aircraftReportRepository.findById(aircraftId)
                .orElseThrow(
                        () -> new ResourceNotFoundException(Constants.AIRCRAFT_NOT_FOUND)
                );
        List<Reservation> reservationList = reservationRepository.findByRouteId(aircraftReport.getRouteId());
        AircraftReportDTO aircraftReportDTO = ObjectMapperUtil.map(aircraftReport, AircraftReportDTO.class);
        aircraftReportDTO.setReservationList(reservationList);
        return aircraftReportDTO;
    }
}
