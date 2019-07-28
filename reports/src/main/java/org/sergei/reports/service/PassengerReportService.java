package org.sergei.reports.service;

import org.sergei.reports.rest.dto.PassengerReportDTO;
import org.sergei.reports.rest.dto.response.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author Sergei Visotsky
 */
@Service
public interface PassengerReportService {
    ResponseEntity<ResponseDTO<PassengerReportDTO>> findById(Long id);

    ResponseEntity<ResponseDTO<PassengerReportDTO>> findAll(int page, int size);
}
