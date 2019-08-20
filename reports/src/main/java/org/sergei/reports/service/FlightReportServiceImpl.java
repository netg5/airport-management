package org.sergei.reports.service;

import org.sergei.reports.rest.dto.FlightDTO;
import org.sergei.reports.service.interfaces.FlightReportService;
import org.springframework.stereotype.Service;

/**
 * @author Sergei Visotsky
 */
@Service
public class FlightReportServiceImpl implements FlightReportService {
    @Override
    public FlightDTO makeFlightReportByFlightId(Long flightId) {
        return null;
    }
}
