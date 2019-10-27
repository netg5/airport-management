package org.sergei.reports.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.sergei.reports.rest.dto.FlightReportDTO;
import org.sergei.reports.rest.dto.response.ResponseDTO;
import org.sergei.reports.service.interfaces.FlightReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

/**
 * @author Sergei Visotsky
 */
@Disabled
@Slf4j
@ActiveProfiles("test")
@SpringBootTest
public class FlightReportServiceTest {

    @Autowired
    private FlightReportService flightReportService;

    @Test
    public void makeFlightReportByFlightIdTest() {
        ResponseEntity<ResponseDTO<FlightReportDTO>> flightReport = flightReportService.makeFlightReportByFlightId(1L);
        log.info("Flight report as string: {}", flightReport.getBody().toString());
    }
}
