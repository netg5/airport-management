/*
 * Copyright 2018-2019 the original author.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sergei.reports.rest.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.sergei.reports.rest.dto.AircraftReportDTO;
import org.sergei.reports.rest.dto.response.ResponseDTO;
import org.sergei.reports.service.AircraftReportService;
import org.sergei.reports.service.AircraftReportServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Sergei Visotsky
 */
@Api(
        value = "/report-rest/aircraft",
        produces = "application/json"
)
@RestController
@RequestMapping("/aircrafts")
public class AircraftReportController {

    private final AircraftReportService aircraftReportService;

    @Autowired
    public AircraftReportController(AircraftReportServiceImpl aircraftReportService) {
        this.aircraftReportService = aircraftReportService;
    }

    @ApiOperation("Get all existing reports in paginated way")
    @GetMapping(params = {"page", "size"})
    public ResponseEntity<ResponseDTO<AircraftReportDTO>> findAllReports(@ApiParam("Number of the page to show")
                                                                         @RequestParam("page") int page,
                                                                         @ApiParam("Number of elements per page")
                                                                         @RequestParam("size") int size) {
        return aircraftReportService.findAll(page, size);
    }

    @ApiOperation("Get report for the aircraft by ID")
    @GetMapping("/{aircraftId}")
    public ResponseEntity<ResponseDTO<AircraftReportDTO>> findByAircraftId(@ApiParam("Aircraft ID to find the report")
                                                                           @PathVariable("aircraftId") Long aircraftId) {
        return aircraftReportService.findById(aircraftId);
    }
}
