package org.sergei.reports.service;

import org.sergei.reports.rest.dto.AircraftReportDTO;
import org.sergei.reports.rest.dto.response.ResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author Sergei Visotsky
 */
@Service
public interface AircraftReportService {
    ResponseEntity<ResponseDTO<AircraftReportDTO>> findAll(int page, int size);

    ResponseEntity<ResponseDTO<AircraftReportDTO>> findById(Long aircraftId);
}
