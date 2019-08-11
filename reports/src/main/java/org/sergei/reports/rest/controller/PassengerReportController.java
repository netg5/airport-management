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
import org.sergei.reports.rest.dto.PassengerReportDTO;
import org.sergei.reports.rest.dto.response.ResponseDTO;
import org.sergei.reports.service.PassengerReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sergei Visotsky
 */
@RestController
@Api(tags = {"passengerReports"})
public class PassengerReportController {

    private final PassengerReportService passengerReportService;

    @Autowired
    public PassengerReportController(PassengerReportService passengerReportService) {
        this.passengerReportService = passengerReportService;
    }

    @ApiOperation("Get report for a specific passenger")
    @GetMapping(value = "/findReportByPassengerId/{passengerId}")
    public ResponseEntity<ResponseDTO<PassengerReportDTO>> findReportByPassengerId(@PathVariable Long passengerId) {
        return passengerReportService.findById(passengerId);
    }
}
