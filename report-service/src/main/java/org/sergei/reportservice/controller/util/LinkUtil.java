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

package org.sergei.reportservice.controller.util;

import org.sergei.reportservice.controller.AircraftReportController;
import org.sergei.reportservice.dto.AircraftReportDTO;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Class to set links for the REST responses
 *
 * @author Sergei Visotsky
 */
public final class LinkUtil {

    private LinkUtil() {
    }

    public static AircraftReportDTO setLinksForAircraftReport(AircraftReportDTO aircraftReportDTO) {
        Link selfLink = linkTo(
                methodOn(AircraftReportController.class)
                        .findByAircraftId(aircraftReportDTO.getAircraftId())).withSelfRel();
        Link link = linkTo(AircraftReportController.class).withRel("allReports");
        aircraftReportDTO.add(selfLink);
        aircraftReportDTO.add(link);
        return aircraftReportDTO;
    }
}
