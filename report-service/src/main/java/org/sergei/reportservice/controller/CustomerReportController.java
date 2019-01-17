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

package org.sergei.reportservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.sergei.reportservice.dto.CustomerReportDTO;
import org.sergei.reportservice.service.CustomerReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.sergei.reportservice.controller.hateoas.LinkUtil.setLinksForCustomerReport;

/**
 * @author Sergei Visotsky
 */
@Api(
        value = "/report-api/customers",
        produces = "application/json"
)
@RestController
@RequestMapping("/customers")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class CustomerReportController {

    private final CustomerReportService customerReportService;

    @Autowired
    public CustomerReportController(CustomerReportService customerReportService) {
        this.customerReportService = customerReportService;
    }

    @ApiOperation(
            value = "Get report for a specific customer",
            notes = "Operation allowed for the ROLE_ADMIN only"
    )
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerReportDTO> findReportByCustomerId(@PathVariable Long customerId) {
        CustomerReportDTO customerReportDTO = customerReportService.findById(customerId);
        return new ResponseEntity<>(setLinksForCustomerReport(customerReportDTO), HttpStatus.OK);
    }
}