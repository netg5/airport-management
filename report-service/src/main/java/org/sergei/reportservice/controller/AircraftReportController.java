/*
 * Copyright 2018-2019 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.sergei.reportservice.controller;

import io.swagger.annotations.*;
import org.sergei.reportservice.dto.AircraftReportDTO;
import org.sergei.reportservice.service.AircraftReportService;
import org.sergei.reportservice.service.AircraftReportServiceImpl;
import org.sergei.reportservice.service.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.sergei.reportservice.controller.util.LinkUtil.setLinksForAircraftReport;

/**
 * @author Sergei Visotsky
 */
@Api(
        value = "/report-api/aircraft",
        produces = "application/json"
)
@RestController
@RequestMapping("/aircraft")
public class AircraftReportController {

    private final AircraftReportService<AircraftReportDTO> aircraftReportService;

    @Autowired
    public AircraftReportController(AircraftReportServiceImpl aircraftReportService) {
        this.aircraftReportService = aircraftReportService;
    }

    @ApiOperation("Get all existing reports in paginated way")
    @GetMapping(params = {"page", "size"})
    public ResponseEntity<Page<AircraftReportDTO>> findAllReports(@ApiParam("Number of the page to show")
                                                                  @RequestParam("page") int page,
                                                                  @ApiParam("Number of elements per page")
                                                                  @RequestParam("size") int size) {
        return new ResponseEntity<>(aircraftReportService.findAll(page, size), HttpStatus.OK);
    }

    @ApiOperation("Get report for the aircraft by ID")
    @ApiResponses(
            @ApiResponse(code = 404, message = Constants.AIRCRAFT_NOT_FOUND)
    )
    @GetMapping("/{aircraftId}")
    public ResponseEntity<AircraftReportDTO> findByAircraftId(@ApiParam("Aircraft ID to find the report")
                                                              @PathVariable("aircraftId") Long aircraftId) {
        AircraftReportDTO aircraftReportDTO = aircraftReportService.findById(aircraftId);
        return new ResponseEntity<>(setLinksForAircraftReport(aircraftReportDTO), HttpStatus.OK);
    }
}
