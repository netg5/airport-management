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

import org.sergei.reportservice.rest.dto.CustomerReportDTO;
import org.sergei.reportservice.exceptions.ResourceNotFoundException;
import org.sergei.reportservice.model.CustomerReport;
import org.sergei.reportservice.model.Reservation;
import org.sergei.reportservice.repository.CustomerReportRepository;
import org.sergei.reportservice.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.sergei.reportservice.util.ObjectMapperUtil.map;

/**
 * @author Sergei Visotsky
 */
@Service
public class CustomerReportService implements IReportService<CustomerReportDTO> {

    private final CustomerReportRepository customerReportRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public CustomerReportService(CustomerReportRepository customerReportRepository,
                                 ReservationRepository reservationRepository) {
        this.customerReportRepository = customerReportRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public CustomerReportDTO findById(Long id) {
        CustomerReport customerReport = customerReportRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(Constants.CUSTOMER_NOT_FOUND)
                );
        CustomerReportDTO customerReportDTO = map(customerReport, CustomerReportDTO.class);
        List<Reservation> reservations =
                reservationRepository.findAllByCustomerId(customerReportDTO.getCustomerId());
        customerReportDTO.setReservations(reservations);
        return customerReportDTO;
    }

    @Override
    public Page<CustomerReportDTO> findAll(int page, int size) {
        return null;
    }
}
