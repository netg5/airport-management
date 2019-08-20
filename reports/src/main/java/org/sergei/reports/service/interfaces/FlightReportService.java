package org.sergei.reports.service.interfaces;

import org.sergei.reports.rest.dto.FlightDTO;
import org.springframework.stereotype.Service;

/**
 * @author Sergei Visotsky
 */
@Service
public interface FlightReportService {

    FlightDTO makeFlightReportByFlightId(Long flightId);
}
