package org.sergei.reports.rest.constoller;

import io.swagger.annotations.Api;
import org.sergei.reports.rest.dto.FlightReportDTO;
import org.sergei.reports.rest.dto.response.ResponseDTO;
import org.sergei.reports.service.interfaces.FlightReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sergei Visotsky
 */
@RestController
@Api(tags = {"flightReports"})
public class FlightReportController {

    private final FlightReportService flightReportService;

    @Autowired
    public FlightReportController(FlightReportService flightReportService) {
        this.flightReportService = flightReportService;
    }

    @GetMapping(value = "/makeFlightReportByFlightId/{flightId}")
    public ResponseEntity<ResponseDTO<FlightReportDTO>> makeFlightReportByFlightId(@PathVariable Long flightId) {
        return flightReportService.makeFlightReportByFlightId(flightId);
    }
}
