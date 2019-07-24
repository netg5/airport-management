package org.sergei.reports.service;

import org.sergei.reports.rest.dto.CustomerReportDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * @author Sergei Visotsky
 */
@Service
public interface CustomerReportService {
    CustomerReportDTO findById(Long id);

    Page<CustomerReportDTO> findAll(int page, int size);
}
