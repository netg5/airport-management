package org.sergei.reports.service;

import org.sergei.reports.rest.dto.CustomerReportDTO;
import org.sergei.reports.rest.dto.response.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author Sergei Visotsky
 */
@Service
public interface CustomerReportService {
    ResponseEntity<ResponseDTO<CustomerReportDTO>> findById(Long id);

    ResponseEntity<ResponseDTO<CustomerReportDTO>> findAll(int page, int size);
}
