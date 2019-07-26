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

package org.sergei.reports.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Sergei Visotsky
 */
@ApiModel(value = "AircraftReport", description = "Aircraft report model")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AircraftReportDTO implements Serializable {

    private static final long serialVersionUID = 3339446631856128623L;

    @ApiModelProperty("Aircraft ID whoose report is shown")
    private Long aircraftId;

    @ApiModelProperty("Aircraft name")
    private String aircraftName;

    @ApiModelProperty("Aircraft model")
    private String model;

    @ApiModelProperty("Route ID")
    private Long routeId;

    @ApiModelProperty("Distance to fly")
    private Double distance;

    @ApiModelProperty("Destination place")
    private String place;

    @ApiModelProperty("Flight price")
    private BigDecimal price;

    @ApiModelProperty("Collection of reservation made for specific aircraft")
    private List<ReservationDTO> reservations;
}
