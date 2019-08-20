package org.sergei.reports.service.interfaces;

import org.sergei.reports.rest.dto.FlightReportDTO;
import org.sergei.reports.rest.dto.response.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author Sergei Visotsky
 */
@Service
public interface FlightReportService {

    ResponseEntity<ResponseDTO<FlightReportDTO>> makeFlightReportByFlightId(Long flightId);
}
