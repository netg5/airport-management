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

package org.sergei.reservation.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Sergei Visotsky
 */
@ApiModel(value = "Route", description = "Route meta data model")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RouteResponseDTO implements Serializable {

    private static final long serialVersionUID = -6688458044709501452L;

    @ApiModelProperty("Route ID")
    private Long routeId;

    @ApiModelProperty("Route distance")
    private Double distance;

    @ApiModelProperty("Flight departure time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime departureTime;

    @ApiModelProperty("Flight arrival time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime arrivalTime;

    @ApiModelProperty("Flight price")
    private BigDecimal price;

    @ApiModelProperty("Destination place")
    private String place;

    @ApiModelProperty("Aircraft ID to fly with")
    @JsonProperty("aircraft")
    private AircraftResponseDTO aircraftDTO;
}
