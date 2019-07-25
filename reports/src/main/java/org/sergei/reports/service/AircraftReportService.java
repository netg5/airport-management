package org.sergei.reports.service;

import org.sergei.reports.rest.dto.AircraftReportDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * @author Sergei Visotsky
 */
@Service
public interface AircraftReportService {
    Page<AircraftReportDTO> findAll(int page, int size);

    AircraftReportDTO findById(Long aircraftId);
}
